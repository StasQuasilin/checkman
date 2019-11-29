package entity.transport;

import entity.organisations.LoadAddress;

import java.util.Set;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
public class TransportationDocument {
    private int id;
    private Transportation2 transportation;
    private LoadAddress address;
    private Set<TransportationProduct> products;
}
