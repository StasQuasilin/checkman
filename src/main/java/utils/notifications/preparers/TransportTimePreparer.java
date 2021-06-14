package utils.notifications.preparers;

import entity.DealType;
import entity.bot.NotifyStatus;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.products.ProductProperty;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import utils.LanguageBase;
import utils.hibernate.dao.NotificationDAO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static constants.Constants.COMMA;
import static constants.Constants.SPACE;

public abstract class TransportTimePreparer extends utils.notifications.NotificationPreparer{
    private final LanguageBase languageBase;
    private final NotificationDAO dao;
    TransportTimePreparer(LanguageBase languageBase, NotificationDAO dao){
        this.languageBase = languageBase;
        this.dao = dao;
    }

    public String prepareMessage(Transportation transportation, String lang, String messageBase) {

        final Set<TransportationProduct> products = transportation.getProducts();
        final HashMap<String, LinkedList<String>> productsHashMap = new HashMap<>();
        final LinkedList<String> organisations = new LinkedList<>();
        for (TransportationProduct product : products){
            final DealProduct dealProduct = product.getDealProduct();
            final Deal deal = dealProduct.getDeal();
            final DealType type = deal.getType();

            String action = getAction(type, lang);
//            languageBase.get(lang, "_" + type.toString()).toLowerCase();
            String productName = dao.getProductProperty(dealProduct.getProduct(), type.toString());
            if (!productsHashMap.containsKey(action)){
                productsHashMap.put(action, new LinkedList<>());
            }
            final LinkedList<String> strings = productsHashMap.get(action);
            if (!strings.contains(productName)){
                strings.add(productName);
            }
            String organisation = deal.getOrganisation().getValue();
            if (!organisations.contains(organisation)){
                organisations.add(organisation);
            }
        }

        int i = 0;
        StringBuilder productBuilder = new StringBuilder();
        StringBuilder counterpartyBuilder = new StringBuilder();
        for (Map.Entry<String, LinkedList<String>> entry : productsHashMap.entrySet()){
            productBuilder.append(entry.getKey()).append(SPACE);
            int j = 0;
            final LinkedList<String> value = entry.getValue();
            for (String s : value){
                productBuilder.append(s);
                if (j < value.size() - 1){
                    productBuilder.append(COMMA).append(SPACE);
                }
                j++;
            }
            if (i < productsHashMap.size() - 1){
                productBuilder.append(COMMA).append(SPACE);
            }
            i++;
        }
        i = 0;
        for (String s : organisations){
            counterpartyBuilder.append(s);
            if (i < organisations.size() - 1){
                counterpartyBuilder.append(COMMA).append(SPACE);
            }
            i++;
        }

        final Driver driver = transportation.getDriver();

        return String.format(messageBase, productBuilder.toString(), driver != null ? driver.getPerson().getValue() : "--", counterpartyBuilder.toString());
    }

    protected String getAction(DealType type, String lang){
        return languageBase.get(lang, "_" + type.toString()).toLowerCase();
    }
}
