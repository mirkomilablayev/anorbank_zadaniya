package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;

public interface CrudController<
        DTO extends Dto,
        UpDto extends Dto> {

     HttpEntity<?> create(@RequestBody DTO cd);

     HttpEntity<?> update(@RequestBody UpDto cd);

     HttpEntity<?> get(@PathVariable Long id);

     HttpEntity<?> deleteById(@PathVariable Long id);
}
