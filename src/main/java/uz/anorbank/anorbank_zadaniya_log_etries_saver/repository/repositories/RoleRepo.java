package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<UserRole,Long>, BaseRepository {
Optional<UserRole> findByNameAndIsActive(String name, Boolean isActive);
}
