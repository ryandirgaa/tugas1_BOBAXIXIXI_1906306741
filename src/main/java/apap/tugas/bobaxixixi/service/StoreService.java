package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreModel;

import java.util.List;

public interface StoreService {
    void addStore(StoreModel store);
    StoreModel getStoreByIdStore(Long id);
    List<StoreModel> getStoreList();
    void updateStore(StoreModel store);
    void removeStore(StoreModel store);
    boolean timeHandler(StoreModel storeModel);
}
