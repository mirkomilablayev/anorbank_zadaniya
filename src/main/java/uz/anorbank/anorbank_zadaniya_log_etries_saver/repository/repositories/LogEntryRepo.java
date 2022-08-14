package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.LogEntry;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface LogEntryRepo extends JpaRepository<LogEntry, Long> , BaseRepository {
    Boolean existsByRoute_IdAndIsDeleted(Long route_id, Boolean isDeleted);
    Optional<LogEntry> findByIdAndIsDeleted(Long id, Boolean isDeleted);


    @Query(nativeQuery = true ,value = "select *\n" +
            "from log_entry le\n" +
            "         join vehicle v on le.vehicle_id = v.id\n" +
            "         join users u on v.user_id = u.id\n" +
            "         join users u1 on le.user_id = u1.id\n" +
            "where le.is_deleted = false and u1.id = ?1\n" +
            "  and v.registration_number like concat('%', concat(?2, '%'))\n" +
            "  and u.full_name like concat('%', concat(?3, '%'))")
    List<LogEntry> getAllLogEntries(Long userId,String register_number, String fullName);
}
