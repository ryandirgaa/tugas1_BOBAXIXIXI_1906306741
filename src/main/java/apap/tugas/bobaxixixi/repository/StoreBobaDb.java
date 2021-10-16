package apap.tugas.bobaxixixi.repository;

import apap.tugas.bobaxixixi.model.StoreBobaTeaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface StoreBobaDb extends JpaRepository<StoreBobaTeaModel, Long> {
    Optional<StoreBobaTeaModel> findById(Long id);
}
