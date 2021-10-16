package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.model.ToppingModel;
import apap.tugas.bobaxixixi.repository.ManagerDb;

import apap.tugas.bobaxixixi.repository.ToppingDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDb managerDb;

    @Override
    public void addManager(ManagerModel manager){
        managerDb.save(manager);
    }

    @Override
    public ManagerModel getManagerByIdManager(Long id){
        Optional<ManagerModel> x = managerDb.findById(id);
        if (x.isPresent()) {
            return x.get();
        }
        return null;
    }

    @Override
    public List<ManagerModel> getManagerList(){
        return managerDb.findAll();
    }

    @Override
    public void updateManager(ManagerModel manager){
        managerDb.save(manager);
    }

    @Override
    public void removeManager(ManagerModel manager){
        managerDb.delete(manager);
    }
}
