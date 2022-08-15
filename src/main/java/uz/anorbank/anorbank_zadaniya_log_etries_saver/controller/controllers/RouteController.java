package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
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


    @PostMapping("/createRoute")
    @Override
    public HttpEntity<?> create(@RequestBody RouteCreateDto cd) {
        return service.create(cd);
    }

    @PutMapping("/updateRoute")
    @Override
    public HttpEntity<?> update(@RequestBody RouetUpdateDto cd) {
        return service.update(cd);
    }

    @GetMapping("/getFullInfoForDto/{id}")
    @Override
    public HttpEntity<?> get(@PathVariable Long id) {
        return service.get(id);
    }


    @DeleteMapping("/deleteById/{id}")
    @Override
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @GetMapping("/getMyRoutes")
    public HttpEntity<?> getMyRoutes(){
        return service.getMyRoutes();
    }
}
