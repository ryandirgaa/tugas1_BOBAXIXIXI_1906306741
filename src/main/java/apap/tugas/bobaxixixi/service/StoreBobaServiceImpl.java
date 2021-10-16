package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreBobaTeaModel;
import apap.tugas.bobaxixixi.repository.StoreBobaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoreBobaServiceImpl implements StoreBobaService{
    @Autowired
    StoreBobaDb storeBobaDb;

    @Override
    public void addStoreBoba(StoreBobaTeaModel storeBoba) { storeBobaDb.save(storeBoba); }

    @Override
    public StoreBobaTeaModel getStoreBobaByIdStoreBoba(Long id){
        Optional<StoreBobaTeaModel> x = storeBobaDb.findById(id);
        if (x.isPresent()) {
            return x.get();
        }
        return null;
    }

    @Override
    public List<StoreBobaTeaModel> getStoreBobaList(){
        return storeBobaDb.findAll();
    }

    @Override
    public void updateStoreBoba(StoreBobaTeaModel storeBoba){
        storeBobaDb.save(storeBoba);
    }

    @Override
    public void removeStoreBoba(StoreBobaTeaModel storeBoba){
        storeBobaDb.delete(storeBoba);
    }
}
