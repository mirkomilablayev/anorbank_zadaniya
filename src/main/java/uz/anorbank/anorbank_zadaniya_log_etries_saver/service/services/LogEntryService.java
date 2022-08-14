package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.LogEntryRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.AbstractService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.CrudService;

@Service
public class LogEntryService extends AbstractService<LogEntryRepo> implements BaseService, CrudService<LogEntryCreateDto, LogEntryUpdateDto> {

    public LogEntryService(LogEntryRepo repository) {
        super(repository);
    }

    @Override
    public HttpEntity<?> create(LogEntryCreateDto cd) {
        return null;
    }

    @Override
    public HttpEntity<?> update(LogEntryUpdateDto cd) {
        return null;
    }

    @Override
    public HttpEntity<?> get(Long id) {
        return null;
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        return null;
    }
}
