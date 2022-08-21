package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Route;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepo extends JpaRepository<Route, Long>, BaseRepository {
    Optional<Route> findByIdAndIsDeleted(Long id, Boolean isDeleted);

    List<Route> findAllByUser_IdAndIsDeleted(Long user_id, Boolean isDeleted);
}
