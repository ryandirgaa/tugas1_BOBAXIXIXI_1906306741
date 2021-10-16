package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.repository.StoreDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import java.time.LocalTime;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreDb storeDb;

    @Override
    public void addStore(StoreModel store){
        storeDb.save(store);
    }

    @Override
    public StoreModel getStoreByIdStore(Long id){
        Optional<StoreModel> x = storeDb.findById(id);
        if (x.isPresent()) {
            return x.get();
        }
        return null;
    }

    public List<StoreModel> getStoreList(){
        return storeDb.findAll();
    }

    @Override
    public void updateStore(StoreModel store){
        storeDb.save(store);
    }

    @Override
    public void removeStore(StoreModel store){
        storeDb.delete(store);
    }

    @Override
    public boolean timeHandler(StoreModel store){
        LocalTime currentTime = LocalTime.now(), openHour = store.getOpenHour(), closeHour = store.getCloseHour();
        boolean can = false;

        if(openHour.compareTo(closeHour) == -1){
            if(currentTime.compareTo(closeHour) >= 0 || currentTime.compareTo(openHour) < 0) can = true;
            else can = false;

        }
        else if(openHour.compareTo(closeHour) == 1){
            if(currentTime.compareTo(closeHour) >= 0 && currentTime.compareTo(openHour) < 0) can = true;
            else can = false;
        }

        return can;
    }

}
