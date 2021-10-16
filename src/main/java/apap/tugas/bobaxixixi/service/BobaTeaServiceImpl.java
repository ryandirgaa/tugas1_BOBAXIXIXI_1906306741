package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.ToppingModel;
import apap.tugas.bobaxixixi.repository.BobaTeaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class BobaTeaServiceImpl implements BobaTeaService {
    @Autowired
    BobaTeaDb bobaTeaDb;

    @Override
    public void addBobaTea(BobaTeaModel bobaTea) {
        bobaTeaDb.save(bobaTea);
    }

    @Override
    public BobaTeaModel getBobaTeaByIdBobaTea(Long id){
        Optional<BobaTeaModel> x = bobaTeaDb.findById(id);
        if (x.isPresent()) {
            return x.get();
        }
        return null;
    }

    @Override
    public List<BobaTeaModel> getBobaTeaList(){
        return bobaTeaDb.findAll();
    }

    @Override
    public List<BobaTeaModel> getBobaByName(BobaTeaModel bobaTea){
        return bobaTeaDb.findAllByName(bobaTea);
    }

    @Override
    public List<BobaTeaModel> getBobaByTopping(ToppingModel topping){
        return bobaTeaDb.findAllByTopping(topping);
    }

    @Override
    public List<BobaTeaModel> getBobaByNameAndTopping(BobaTeaModel bobaTea, ToppingModel topping){
        return bobaTeaDb.findAllByNameAndTopping(bobaTea, topping);
    }

    @Override
    public void updateBobaTea(BobaTeaModel bobaTea) {
        bobaTeaDb.save(bobaTea);
    }

    @Override
    public void removeBobaTea(BobaTeaModel bobaTea) {
        bobaTeaDb.delete(bobaTea);
    }
}
