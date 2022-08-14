package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.OdometerHistory;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

public interface  OdometerHistoryRepo extends JpaRepository<OdometerHistory, Long>, BaseRepository {

}
