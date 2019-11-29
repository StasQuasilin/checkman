package entity.organisations;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@Entity
@Table(name = "load_address")
public class LoadAddress {
    private int id;
    private Organisation organisation;

}
