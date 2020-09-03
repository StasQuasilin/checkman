package utils.db.dao.workers;

import entity.Worker;
import utils.db.hibernate.Hibernator;

import java.util.LinkedList;
import java.util.List;

import static constants.Keys.ID;
import static constants.Keys.SURNAME;

public class WorkerDAOHibernate implements WorkerDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Worker> findWorker(String key) {
        LinkedList<Worker> workers = new LinkedList<>();
        workers.addAll(hibernator.find(Worker.class, SURNAME, key));
        return workers;
    }

    @Override
    public Worker getWorkerById(Object id) {
        return hibernator.get(Worker.class, ID, id);
    }
}
