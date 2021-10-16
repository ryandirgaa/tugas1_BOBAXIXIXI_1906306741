package apap.tugas.bobaxixixi.repository;

import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.model.ToppingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BobaTeaDb extends JpaRepository<BobaTeaModel, Long> {
    Optional<BobaTeaModel> findById(Long id);
    List<BobaTeaModel> findAllByName(BobaTeaModel bobaTea);
    List<BobaTeaModel> findAllByTopping(ToppingModel topping);
    List<BobaTeaModel> findAllByNameAndTopping(BobaTeaModel bobaTea, ToppingModel toping);
}
