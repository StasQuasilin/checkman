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
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Archivator;
import utils.DocumentUIDGenerator;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;
import utils.hibernate.dbDAOService;
import utils.transport.TransportReplaceUtil;
import utils.transport.TransportationEditor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static constants.Constants.DEAL;
import static constants.Constants.PRODUCTS;
import static utils.hibernate.State.notNull;

/**
 * Created by quasilin on 13.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j" ,Branches.UI.FORGOT})
public class ContextFilter implements Filter {

    public static BotSettings settings;
    final Logger log = Logger.getLogger(ContextFilter.class);
    TransportReplaceUtil tru;

    @Override
    public void init(FilterConfig filterConfig) {

        checkTransport();
        gcTimer = new Timer(10 * 60 * 1000, e -> System.gc());
//        gcTimer.start();

        TelegramBotFactory.init(filterConfig.getServletContext().getContextPath());
        Archivator.init();
        tru = new TransportReplaceUtil();
    }

    public static void checkTransport() {

        final TransportationEditor transportationEditor = new TransportationEditor();
        final HashMap<String, Object> args = new HashMap<>();
        args.put(DEAL, notNull);

        final Hibernator hibernator = Hibernator.getInstance();

        System.out.println();
        final LinkedList<Transportation> transportations = new LinkedList<>();
        initList(transportations, hibernator, args);
        if (transportations.size() > 0){
            System.out.println("Start transportation checking");
            while (transportations.size() > 0){
                for (Transportation transportation : transportations){
                    final Deal deal = transportation.getDeal();
                    DealProduct dealProduct = createDealProduct(deal, hibernator);
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
                initList(transportations, hibernator, args);
            }
            System.out.println("done");
        } else {
            System.out.println("Nothing to do!");
        }

    }

    private static DealProduct createDealProduct(Deal deal, Hibernator hibernator) {
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

            hibernator.save(dealProduct);
            deal.getProducts().add(dealProduct);

            deal.setProduct(null);
            hibernator.save(deal);
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
        gcTimer.stop();
        Archivator.stop();
        ActiveSubscriptions.getInstance().close();
        TelegramBotFactory.shutdown();
        HibernateSessionFactory.shutdown();
        tru.shutdown();
        SessionTimer.getInstance().stop();
    }
    private static void initList(LinkedList<Transportation> transportations, Hibernator hibernator, HashMap<String, Object> args) {
        transportations.clear();
        transportations.addAll(hibernator.limitQuery(Transportation.class, args, 20));
    }
}


