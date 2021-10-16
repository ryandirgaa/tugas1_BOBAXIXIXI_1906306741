package apap.tugas.bobaxixixi.repository;

import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerDb extends JpaRepository<ManagerModel, Long> {
    Optional<ManagerModel> findById(Long id);
}
