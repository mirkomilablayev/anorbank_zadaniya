package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.AbstractController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.CrudController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryFilterMethod;
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
        return service.create(cd);
    }

    @PutMapping("/updateLogEntry")
    @Override
    public HttpEntity<?> update(LogEntryUpdateDto cd) {
        return service.update(cd);
    }

    @GetMapping("/getLogEntry/{id}")
    @Override
    public HttpEntity<?> get(@PathVariable Long id) {
        return service.get(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @Override
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @PostMapping("/getReport")
    public HttpEntity<?> generateReport(@RequestBody LogEntryFilterMethod logEntryFilterMethod){
        return service.getReport(logEntryFilterMethod);
    }
}
