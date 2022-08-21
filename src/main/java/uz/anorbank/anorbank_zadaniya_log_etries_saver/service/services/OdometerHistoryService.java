package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.odometerHistory.OdometerHistoryCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.odometerHistory.OdometerHistoryUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.OdometerHistoryRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.AbstractService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.CrudService;
@Transactional
@Service
public class OdometerHistoryService extends AbstractService<OdometerHistoryRepo> implements BaseService, CrudService<OdometerHistoryCreateDto, OdometerHistoryUpdateDto> {

    public OdometerHistoryService(OdometerHistoryRepo repository) {
        super(repository);
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
