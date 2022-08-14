package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.AbstractController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouetUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouteCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.CrudService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services.RouteService;

@RestController
@RequestMapping("/api/route")
public class RouteController extends AbstractController<RouteService> implements CrudService<RouteCreateDto, RouetUpdateDto> {
    public RouteController(RouteService service) {
        super(service);
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
