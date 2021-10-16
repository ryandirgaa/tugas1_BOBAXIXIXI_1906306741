package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreBobaTeaModel;
import apap.tugas.bobaxixixi.model.StoreModel;

import java.util.List;

public interface StoreBobaService {
    void addStoreBoba(StoreBobaTeaModel storeBoba);
    StoreBobaTeaModel getStoreBobaByIdStoreBoba(Long id);
    List<StoreBobaTeaModel> getStoreBobaList();
    void updateStoreBoba(StoreBobaTeaModel storeBoba);
    void removeStoreBoba(StoreBobaTeaModel storeBoba);
}
