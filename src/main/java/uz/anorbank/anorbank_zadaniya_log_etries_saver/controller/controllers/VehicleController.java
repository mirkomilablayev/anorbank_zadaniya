package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.config.anotation.CheckRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.AbstractController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.CrudController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services.VehicleService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController extends AbstractController<VehicleService> implements CrudController<VehicleCreateDto, VehicleUpdateDto> {
    public VehicleController(VehicleService service) {
        super(service);
    }

    @CheckRole(Constant.DRIVER)
    @PostMapping("/createVehicle")
    @Override
    public HttpEntity<?> create(VehicleCreateDto cd) {
        return service.create(cd);
    }

    @CheckRole(Constant.DRIVER)
    @PutMapping("/updateVehicle")
    @Override
    public HttpEntity<?> update(VehicleUpdateDto cd) {
        return service.update(cd);
    }

    @CheckRole(Constant.DRIVER)
    @GetMapping("/getMyVehiclesFullInfo/{id}")
    @Override
    public HttpEntity<?> get(@PathVariable Long id) {
        return service.get(id);
    }

    @CheckRole(Constant.DRIVER)
    @DeleteMapping("/deleteMyVehicle/{id}")
    @Override
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }


}
