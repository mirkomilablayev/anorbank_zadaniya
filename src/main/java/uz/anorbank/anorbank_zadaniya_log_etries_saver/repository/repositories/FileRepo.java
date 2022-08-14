package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.File;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

import java.util.Optional;

public interface FileRepo extends JpaRepository<File, Long>, BaseRepository {
    Optional<File> findByIdAndIsDeleted(Long id, Boolean isDeleted);
}
