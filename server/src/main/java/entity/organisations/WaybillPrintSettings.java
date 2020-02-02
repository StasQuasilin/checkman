package entity.organisations;

import entity.transport.Transportation;

/**
 * Created by Kvasik on 01.02.2020.
 */
public class WaybillPrintSettings {
    private int id;
    private String number;
    private Transportation transportation;
    private LoadAddress loadAddress;
    private float brutto;
    private float netto;
}
