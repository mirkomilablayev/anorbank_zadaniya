package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>, BaseRepository {
    Optional<User> findByUsernameAndIsDeleted(String username, Boolean isDeleted);

    Boolean existsByUsernameAndIsDeleted(String username, Boolean isDeleted);

}