package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryShowDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.LogEntryRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.OdometerHistoryRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RouteRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.VehicleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.AbstractService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.CrudService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

@Service
public class LogEntryService extends AbstractService<LogEntryRepo> implements BaseService, CrudService<LogEntryCreateDto, LogEntryUpdateDto> {


    private final VehicleRepo vehicleRepo;
    private final RouteRepo routeRepo;
    private final OdometerHistoryRepo odometerHistoryRepo;
    private final Util util;
    public LogEntryService(LogEntryRepo repository, VehicleRepo vehicleRepo, RouteRepo routeRepo, Util util, OdometerHistoryRepo odometerHistoryRepo) {
        super(repository);
        this.vehicleRepo = vehicleRepo;
        this.routeRepo = routeRepo;
        this.util = util;
        this.odometerHistoryRepo = odometerHistoryRepo;
    }

    @Override
    public HttpEntity<?> create(LogEntryCreateDto cd) {
        LogEntry logEntry = makeLogEntry(cd);
        LogEntry save = repository.save(logEntry);

        // bu yerda odometer yangi hitory yaratamiz
        OdometerHistory odometerHistory = new OdometerHistory();
        odometerHistory.setLogEntry(save);
        Integer currentTotalOdometerNumber = save.getVehicle().getCurrentTotalOdometerNumber();
        Integer distance = save.getRoute().getDistance();
        odometerHistory.setOdometerStart(currentTotalOdometerNumber);
        odometerHistory.setOdometerEnd(currentTotalOdometerNumber + distance);
        odometerHistory.setIsDeleted(false);
        Vehicle vehicle = logEntry.getVehicle();
        vehicle.setCurrentTotalOdometerNumber(vehicle.getCurrentTotalOdometerNumber() + distance);
        Vehicle save1 = vehicleRepo.save(vehicle);
        odometerHistory.setVehicle(save1);
        odometerHistoryRepo.save(odometerHistory);
        return ResponseEntity.ok("Log entry is successfully saved with id - " + save.getId()+" !!!");
    }

    private LogEntry makeLogEntry(LogEntryCreateDto cd) {
        Vehicle vehicle = vehicleRepo.findByIdAndIsDeleted(cd.getVehicleId(), false).orElseThrow(ResourceNotFoundException::new);
        Route route = routeRepo.findByIdAndIsDeleted(cd.getRouteId(), false).orElseThrow(ResourceNotFoundException::new);
        User currentUser = util.getCurrentUser();
        LogEntry logEntry = new LogEntry();
        logEntry.setIsDeleted(false);
        logEntry.setUser(currentUser);
        logEntry.setJourneyDate(cd.getLogEntryTime());
        logEntry.setRoute(route);
        logEntry.setVehicle(vehicle);
        logEntry.setJourneyShortDescription(cd.getJourneyShortDescription());
        return logEntry;
    }

    @Override
    public HttpEntity<?> update(LogEntryUpdateDto cd) {
        LogEntry logEntry = repository.findByIdAndIsDeleted(cd.getLogEntryId(), false).orElseThrow(ResourceNotFoundException::new);
        if (!logEntry.getJourneyShortDescription().equals(cd.getJourneyShortDescription())){
            logEntry.setJourneyShortDescription(cd.getJourneyShortDescription());
        }

        if (!logEntry.getJourneyDate().equals(cd.getJourneyDate())){
            logEntry.setJourneyDate(cd.getJourneyDate());
        }

        if (!logEntry.getRoute().getId().equals(cd.getRouteId())){
            logEntry.setRoute(routeRepo.findByIdAndIsDeleted(cd.getRouteId(),false).orElseThrow(ResourceNotFoundException::new));
        }

        if (!logEntry.getVehicle().getId().equals(cd.getVehicleId())){
            logEntry.setVehicle(vehicleRepo.findByIdAndIsDeleted(cd.getVehicleId(), false).orElseThrow(ResourceNotFoundException::new));
        }

        LogEntry save = repository.save(logEntry);
        return ResponseEntity.ok("id - "+save.getId() + " Log entry is successfully edited");
    }

    @Override
    public HttpEntity<?> get(Long id) {
        LogEntry logEntry = repository.findByIdAndIsDeleted(id, false).orElseThrow(ResourceNotFoundException::new);
        LogEntryShowDto logEntryShowDto = makeLogEntryShowDto(logEntry);
        return ResponseEntity.status(HttpStatus.OK).body(logEntryShowDto);
    }

    private LogEntryShowDto makeLogEntryShowDto(LogEntry logEntry) {
        OdometerHistory odometerHistory = odometerHistoryRepo.findByLogEntry_IdAndIsDeleted(logEntry.getId(), false).orElseThrow(ResourceNotFoundException::new);
        LogEntryShowDto logEntryShowDto = new LogEntryShowDto();
        logEntryShowDto.setLogEntryDate(logEntry.getJourneyDate());
        logEntryShowDto.setOdometerEnd(odometerHistory.getOdometerEnd());
        logEntryShowDto.setOdometerStart(odometerHistory.getOdometerStart());
        logEntryShowDto.setJourneyShortDescription(logEntryShowDto.getJourneyShortDescription());
        logEntryShowDto.setVehicleOwnersName(logEntry.getVehicle().getUser().getFirstName()+" "+logEntry.getVehicle().getUser().getLastName());
        logEntryShowDto.setVehicleType(logEntry.getVehicle().getType());
        logEntryShowDto.setRoute(logEntry.getRoute().getFromDestination()+" - " + logEntry.getRoute().getEndDestination());
        return logEntryShowDto;
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        LogEntry logEntry = repository.findByIdAndIsDeleted(id, false).orElseThrow(ResourceNotFoundException::new);
        logEntry.setIsDeleted(true);
        repository.save(logEntry);
        return ResponseEntity.status(HttpStatus.OK).body("Log entry is successfully deleted");
    }
}
