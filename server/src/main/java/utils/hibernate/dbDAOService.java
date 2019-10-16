package utils.hibernate;

/**
 * Created by szpt_user045 on 24.06.2019.
 */
public class dbDAOService {
    public static dbDAO getDAO(){
        return new HibernateDAO();
    }
}
