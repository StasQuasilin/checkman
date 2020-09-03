package utils.db.dao.workers;

import entity.Worker;

import java.util.List;

public interface WorkerDAO {
    List<Worker> findWorker(String key);
    Worker getWorkerById(Object id);
}
