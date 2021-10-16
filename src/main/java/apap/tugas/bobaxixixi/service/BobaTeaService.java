package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.model.ToppingModel;

import java.util.List;

public interface BobaTeaService {
    void addBobaTea(BobaTeaModel bobaTea);
    BobaTeaModel getBobaTeaByIdBobaTea(Long id);
    List<BobaTeaModel> getBobaTeaList();
    List<BobaTeaModel> getBobaByName(BobaTeaModel bobaTea);
    List<BobaTeaModel> getBobaByTopping(ToppingModel topping);
    List<BobaTeaModel> getBobaByNameAndTopping(BobaTeaModel bobaTea, ToppingModel topping);
    void updateBobaTea(BobaTeaModel bobaTea);
    void removeBobaTea(BobaTeaModel bobaTea);
}
