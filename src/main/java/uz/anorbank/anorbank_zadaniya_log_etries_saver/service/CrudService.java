package uz.anorbank.anorbank_zadaniya_log_etries_saver.service;

import org.springframework.http.HttpEntity;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;

public interface CrudService<
        DTO extends Dto,
        UpDto extends Dto> {

    HttpEntity<?> create(DTO cd);

    HttpEntity<?> update(UpDto cd);

    HttpEntity<?> get(Long id);

    HttpEntity<?> deleteById(Long id);
}
