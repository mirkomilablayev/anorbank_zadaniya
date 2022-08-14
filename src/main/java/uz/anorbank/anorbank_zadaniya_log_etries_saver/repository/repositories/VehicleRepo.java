package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Vehicle;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

import java.util.Optional;

public interface VehicleRepo extends JpaRepository<Vehicle, Long>, BaseRepository {
    Boolean existsByCarNumberAndIsDeleted(String carNumber, Boolean isDeleted);
    Optional<Vehicle> findByIdAndIsDeleted(Long id, Boolean isDeleted);
}
