package entity.documents;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by szpt_user045 on 10.02.2020.
 */
@Entity
@Table(name = "currencyes")
public class Currency {
    private int id;
    private String name;
    private float rate;
}
