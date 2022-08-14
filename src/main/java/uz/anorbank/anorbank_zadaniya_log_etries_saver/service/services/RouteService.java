package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouetUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouteCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RouteRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.AbstractService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.CrudService;
@Service
public class RouteService extends AbstractService<RouteRepo> implements BaseService, CrudService<RouteCreateDto, RouetUpdateDto> {
    public RouteService(RouteRepo repository) {
        super(repository);
    }

    @Override
    public HttpEntity<?> create(RouteCreateDto cd) {
        return null;
    }

    @Override
    public HttpEntity<?> update(RouetUpdateDto cd) {
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
