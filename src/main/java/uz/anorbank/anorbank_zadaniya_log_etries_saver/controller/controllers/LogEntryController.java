package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.AbstractController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.CrudController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services.LogEntryService;

@RestController
@RequestMapping("/api/trip")
public class LogEntryController extends AbstractController<LogEntryService> implements CrudController<LogEntryCreateDto, LogEntryUpdateDto> {
    public LogEntryController(LogEntryService service) {
        super(service);
    }

    @PostMapping("/createLogEntry")
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
