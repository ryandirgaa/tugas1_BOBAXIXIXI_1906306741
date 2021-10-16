package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.StoreModel;

import java.util.List;

public interface ManagerService {
    void addManager(ManagerModel manager);
    ManagerModel getManagerByIdManager(Long id);
    List<ManagerModel> getManagerList();
    void updateManager(ManagerModel manager);
    void removeManager(ManagerModel manager);
}
