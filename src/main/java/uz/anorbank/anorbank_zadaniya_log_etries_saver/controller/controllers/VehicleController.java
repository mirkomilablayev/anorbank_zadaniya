package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.AbstractController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.CrudController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services.VehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController extends AbstractController<VehicleService> implements CrudController<VehicleCreateDto, VehicleUpdateDto> {
    public VehicleController(VehicleService service) {
        super(service);
    }

    @PostMapping("/createVehicle")
    @Override
    public HttpEntity<?> create(VehicleCreateDto cd) {
        return service.create(cd);
    }

    @PutMapping("/updateVehicle")
    @Override
    public HttpEntity<?> update(VehicleUpdateDto cd) {
        return service.update(cd);
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
