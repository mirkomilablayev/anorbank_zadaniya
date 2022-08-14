package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.AbstractController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.odometerHistory.OdometerHistoryCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.odometerHistory.OdometerHistoryUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.CrudService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services.OdometerHistoryService;


@RestController
@RequestMapping("/api/odometerHistory")
public class OdometerHistoryController extends AbstractController<OdometerHistoryService> implements CrudService<OdometerHistoryCreateDto, OdometerHistoryUpdateDto> {


    public OdometerHistoryController(OdometerHistoryService service) {
        super(service);
    }

    @Override
    public HttpEntity<?> create(OdometerHistoryCreateDto cd) {
        return null;
    }

    @Override
    public HttpEntity<?> update(OdometerHistoryUpdateDto cd) {
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
