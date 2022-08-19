package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleShowDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Vehicle;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ConflictException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.VehicleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.AbstractService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.CrudService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

import java.util.Set;


@Service
public class VehicleService extends AbstractService<VehicleRepo> implements BaseService, CrudService<VehicleCreateDto, VehicleUpdateDto> {

    private final Util util;

    public VehicleService(VehicleRepo repository, Util util) {
        super(repository);
        this.util = util;
    }

    @Override
    public HttpEntity<?> create(VehicleCreateDto cd) {
        if (!existRole(util.getCurrentUser().getUserRoleSet(), Constant.DRIVER)) {
            throw new ConflictException(util.getCurrentUser().getFullName() + " cannot add driver because it has no driver role");
        }
        if (!repository.existsByCarNumberAndIsDeleted(cd.getVehicleNumber(), false)) {
            Vehicle vehicle = mapToVehicle(cd);
            Vehicle save = repository.save(vehicle);
            return ResponseEntity.ok("Success");
        } else {
            throw new ConflictException("Fail");
        }
    }

    private Boolean existRole(Set<UserRole> userRoleSet, String role) {
        boolean exist = false;
        for (UserRole userRole : userRoleSet) {
            if (userRole.getAuthority().equals(role)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    private Vehicle mapToVehicle(VehicleCreateDto cd) {
        Vehicle vehicle = new Vehicle();
        vehicle.setIsDeleted(false);
        vehicle.setCarColor(cd.getVehicleColor());
        vehicle.setCarNumber(cd.getVehicleNumber());
        vehicle.setType(cd.getType());
        vehicle.setRegistrationNumber(cd.getRegistrationNumber());
        if (cd.getOdometerValueAtRegistration() > 0) {
            vehicle.setTotalOdometerNumberAtRegistration(cd.getOdometerValueAtRegistration());
        } else {
            throw new ConflictException("Fail");
        }
        User currentUser = util.getCurrentUser();
        vehicle.setUser(currentUser);
        return vehicle;
    }


    @Override
    public HttpEntity<?> update(VehicleUpdateDto cd) {
        Vehicle vehicle = repository.findByIdAndIsDeleted(cd.getCarId(), false).orElseThrow(ResourceNotFoundException::new);
        Vehicle updatedVehicle = updateVehicle(vehicle, cd);
        repository.save(updatedVehicle);
        return ResponseEntity.ok("Success");
    }

    private Vehicle updateVehicle(Vehicle vehicle, VehicleUpdateDto cd) {

        if (!vehicle.getType().equals(cd.getType())) {
            vehicle.setType(cd.getType());
        }
        if (!vehicle.getCarColor().equals(cd.getVehicleColor())) {
            vehicle.setCarColor(cd.getVehicleColor());
        }
        if (!vehicle.getCarNumber().equals(cd.getVehicleNumber())) {
            vehicle.setCarNumber(cd.getVehicleNumber());
        }
        return vehicle;
    }

    @Override
    public HttpEntity<?> get(Long id) {
        Vehicle vehicle = repository.findByIdAndIsDeleted(id, false).orElseThrow(ResourceNotFoundException::new);
        VehicleShowDto vehicleShowDto = makeVehicleShowDto(vehicle);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleShowDto);
    }

    private VehicleShowDto makeVehicleShowDto(Vehicle vehicle) {
        VehicleShowDto vehicleShowDto = new VehicleShowDto();
        vehicleShowDto.setType(vehicle.getType());
        vehicleShowDto.setOwnerName(vehicle.getUser().getFullName());
        vehicleShowDto.setRegistrationNumber(vehicle.getRegistrationNumber());
        vehicleShowDto.setCarColor(vehicle.getCarColor());
        vehicleShowDto.setCarNumber(vehicle.getCarNumber());
        vehicleShowDto.setCurrentTotalOdometerNumber(vehicle.getCurrentTotalOdometerNumber());
        return vehicleShowDto;
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        Vehicle vehicle = repository.findByIdAndIsDeleted(id, false).orElseThrow(ResourceNotFoundException::new);
        vehicle.setIsDeleted(true);
        repository.save(vehicle);
        return ResponseEntity.ok("Successfully deleted");
    }
}
