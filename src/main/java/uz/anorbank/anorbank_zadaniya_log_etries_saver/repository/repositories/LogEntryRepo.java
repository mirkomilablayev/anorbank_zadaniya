package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.LogEntry;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

import java.util.Optional;

public interface LogEntryRepo extends JpaRepository<LogEntry, Long> , BaseRepository {
    Boolean existsByRoute_IdAndIsDeleted(Long route_id, Boolean isDeleted);
    Optional<LogEntry> findByIdAndIsDeleted(Long id, Boolean isDeleted);
}
