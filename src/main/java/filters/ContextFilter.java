package filters;

import api.sockets.ActiveSubscriptions;
import api.sockets.handlers.SessionTimer;
import bot.TelegramBotFactory;
import constants.Branches;
import constants.Constants;
import entity.bot.BotSettings;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.products.Product;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import org.apache.log4j.Logger;
import utils.Archivator;
import utils.DocumentUIDGenerator;
import utils.hibernate.DateContainers.LE;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;
import utils.transport.TransportReplaceUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;

import static constants.Constants.*;
import static utils.hibernate.State.notNull;

/**
 * Created by quasilin on 13.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j" ,Branches.UI.FORGOT})
public class ContextFilter implements Filter {

    public static BotSettings settings;
    final static Logger log = Logger.getLogger(ContextFilter.class);
    TransportReplaceUtil tru;
    static final Hibernator hibernator = Hibernator.getInstance();
    static boolean interrupt = false;

    @Override
    public void init(FilterConfig filterConfig) {
        new Thread(ContextFilter::checkTransport).start();

        gcTimer = new Timer(10 * 60 * 1000, e -> System.gc());
//        gcTimer.start();
        TelegramBotFactory.init(filterConfig.getServletContext().getContextPath());
        Archivator.init();
        tru = new TransportReplaceUtil();
    }

    public static void checkTransport() {

        final HashMap<String, Object> args = new HashMap<>();
        args.put(DEAL, notNull);
        args.put(DATE, new LE(Date.valueOf(LocalDate.now().plusDays(1))));

        final LinkedList<Transportation> transportations = new LinkedList<>();
        initList(transportations, args);
        if (transportations.size() > 0){
            int count = 0;
            log.info("Start transportation checking at " + LocalDateTime.now());
            while (transportations.size() > 0){
                for (Transportation transportation : transportations){
                    count++;
                    final Deal deal = transportation.getDeal();
                    DealProduct dealProduct = createDealProduct(deal);
                    TransportationProduct product = new TransportationProduct();
                    product.setTransportation(transportation);
                    product.setDealProduct(dealProduct);
                    product.setWeight(transportation.getWeight());
                    product.setSunAnalyses(transportation.getSunAnalyses());
                    product.setOilAnalyses(transportation.getOilAnalyses());
                    product.setAmount(transportation.getAmount());
                    product.setUid(DocumentUIDGenerator.generateUID());
                    hibernator.save(product);

                    transportation.setDeal(null);
                    hibernator.save(transportation);
                }
                if (interrupt){
                    break;
                }
                initList(transportations, args);
            }
            System.out.println("done " + count + " transforms at " + LocalDateTime.now());
        } else {
            System.out.println("Nothing to do!");
        }

    }

    private static DealProduct createDealProduct(Deal deal) {
        final LinkedList<DealProduct> products = new LinkedList<>(deal.getProducts());

        if (products.size() == 0) {
            DealProduct dealProduct = new DealProduct();
            dealProduct.setDeal(deal);
            final Product product = deal.getProduct();
            dealProduct.setProduct(product);
            dealProduct.setPrice(deal.getPrice());
            dealProduct.setQuantity(deal.getQuantity());
            dealProduct.setUnit(deal.getUnit());
            dealProduct.setShipper(deal.getShipper());
            dealProduct.setCreate(deal.getCreate());
            dealProduct.setDone(deal.getComplete());
            dealProduct.setAddress(deal.getAddress());
            dealProduct.setUid(DocumentUIDGenerator.generateUID());

            ContextFilter.hibernator.save(dealProduct);
            deal.getProducts().add(dealProduct);

            deal.setProduct(null);
            ContextFilter.hibernator.save(deal);
            return dealProduct;
        }
        return products.get(0);
    }

    Timer gcTimer;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        req.setAttribute(Constants.CONTEXT, req.getContextPath());
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        interrupt = true;
        gcTimer.stop();
        Archivator.stop();
        ActiveSubscriptions.getInstance().close();
        TelegramBotFactory.shutdown();
        HibernateSessionFactory.shutdown();
        tru.shutdown();
        SessionTimer.getInstance().stop();
    }
    private static void initList(LinkedList<Transportation> transportations, HashMap<String, Object> args) {
        transportations.clear();
        transportations.addAll(ContextFilter.hibernator.limitQuery(Transportation.class, args, 20));
    }
}


