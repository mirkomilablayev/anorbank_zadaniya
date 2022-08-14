package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.*;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return ResponseEntity.ok("Log entry is successfully saved with id - " + save.getId() + " !!!");
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
        if (!logEntry.getJourneyShortDescription().equals(cd.getJourneyShortDescription())) {
            logEntry.setJourneyShortDescription(cd.getJourneyShortDescription());
        }

        if (!logEntry.getJourneyDate().equals(cd.getJourneyDate())) {
            logEntry.setJourneyDate(cd.getJourneyDate());
        }

        if (!logEntry.getRoute().getId().equals(cd.getRouteId())) {
            logEntry.setRoute(routeRepo.findByIdAndIsDeleted(cd.getRouteId(), false).orElseThrow(ResourceNotFoundException::new));
        }

        if (!logEntry.getVehicle().getId().equals(cd.getVehicleId())) {
            logEntry.setVehicle(vehicleRepo.findByIdAndIsDeleted(cd.getVehicleId(), false).orElseThrow(ResourceNotFoundException::new));
        }

        LogEntry save = repository.save(logEntry);
        return ResponseEntity.ok("id - " + save.getId() + " Log entry is successfully edited");
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
        logEntryShowDto.setVehicleOwnersName(logEntry.getVehicle().getUser().getFullName());
        logEntryShowDto.setVehicleType(logEntry.getVehicle().getType());
        logEntryShowDto.setRouteDistance(logEntry.getRoute().getDistance());
        logEntryShowDto.setRoute(logEntry.getRoute().getFromDestination() + " - " + logEntry.getRoute().getEndDestination());
        return logEntryShowDto;
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        LogEntry logEntry = repository.findByIdAndIsDeleted(id, false).orElseThrow(ResourceNotFoundException::new);
        logEntry.setIsDeleted(true);
        repository.save(logEntry);
        return ResponseEntity.status(HttpStatus.OK).body("Log entry is successfully deleted");
    }

    public HttpEntity<?> getReport(LogEntryDto logEntryDto) {
        Long currentUserId = util.getCurrentUserId();
        LogEntryFilterMethod filterMethod = logEntryDto.getLogEntryFilterMethod();
        String transportOwnerName = "";
        String registrationNumber = "";
        try {
            transportOwnerName = filterMethod.getTransportOwnerName();
            registrationNumber = filterMethod.getTransportRegistrationNumber();
        } catch (Exception e) {
            transportOwnerName = "";
            registrationNumber = "";
        }
        boolean isDateIntervalValid = checkDateInterval(filterMethod);
        List<LogEntry> logEntries = repository.getAllLogEntries(currentUserId, registrationNumber, transportOwnerName);
        return ResponseEntity.ok(getFilteredGroupedAndSortedResult(logEntries, isDateIntervalValid, filterMethod, logEntryDto.getEnableToGroupedByJourneyDate(), logEntryDto.getIsSortedInIncOrder()));
    }

    private LogEntryResponse getFilteredGroupedAndSortedResult(List<LogEntry> logEntries,
                                                               boolean isDateIntervalValid,
                                                               LogEntryFilterMethod filterMethod,
                                                               Boolean enableToGroupedByJourneyDate,
                                                               Boolean isSortedInIncOrder) {
        List<LogEntryShowDto> res = new ArrayList<>();
        Integer amountDistance = getAmountDistance(logEntries, isDateIntervalValid, filterMethod, res);
        if (enableToGroupedByJourneyDate) {
            return getGroupedAndFilteredLogEntryLogEntryResponse(isSortedInIncOrder, res, amountDistance);
        } else {
            return getLogEntryShowDtoLogEntryResponse(res, amountDistance);
        }
    }

    private LogEntryResponse<LogEntryShowDto> getLogEntryShowDtoLogEntryResponse(List<LogEntryShowDto> res, Integer amountDistance) {
        LogEntryResponse<LogEntryShowDto> response = new LogEntryResponse<>();
        response.setShowDtoList(res);
        response.setAmountDistance(amountDistance);
        return response;
    }

    private LogEntryResponse<GroupedAndFilteredLogEntry> getGroupedAndFilteredLogEntryLogEntryResponse(Boolean isSortedInIncOrder, List<LogEntryShowDto> res, Integer amountDistance) {
        List<GroupedAndFilteredLogEntry> logEntryList = getGroupedAndFilteredLogEntries(res);
        sortedByDistance(isSortedInIncOrder, logEntryList);
        LogEntryResponse<GroupedAndFilteredLogEntry> logEntryResponse = new LogEntryResponse<>();
        logEntryResponse.setAmountDistance(amountDistance);
        logEntryResponse.setShowDtoList(logEntryList);
        return logEntryResponse;
    }

    private void sortedByDistance(Boolean isSortedInIncOrder, List<GroupedAndFilteredLogEntry> logEntryList) {
        for (GroupedAndFilteredLogEntry filteredLogEntry : logEntryList) {
            if (isSortedInIncOrder) {
                List<LogEntryShowDto> collect = filteredLogEntry.getShowDtoList().stream().sorted().collect(Collectors.toList());
                filteredLogEntry.setShowDtoList(collect);
            } else {
                filteredLogEntry.getShowDtoList().sort((item1, item2) ->
                        item2.getRouteDistance().compareTo(item1.getRouteDistance()));
            }
        }
    }

    private List<GroupedAndFilteredLogEntry> getGroupedAndFilteredLogEntries(List<LogEntryShowDto> res) {
        List<GroupedAndFilteredLogEntry> logEntryList = new ArrayList<>();
        for (LogEntryShowDto re : res) {
            boolean isExist = isExistTime(re, logEntryList);
            GroupedByDate(logEntryList, re, isExist);
        }
        return logEntryList;
    }

    private Integer getAmountDistance(List<LogEntry> logEntries, boolean isDateIntervalValid, LogEntryFilterMethod filterMethod, List<LogEntryShowDto> res) {
        Integer amountDistance = 0;
        for (LogEntry logEntry : logEntries) {
            if (isDateIntervalValid) {
                if (isaBoolean(filterMethod, logEntry)) {
                    LogEntryShowDto logEntryShowDto = makeLogEntryShowDto(logEntry);
                    res.add(logEntryShowDto);
                    amountDistance += logEntry.getRoute().getDistance();
                }
            } else {
                LogEntryShowDto logEntryShowDto = makeLogEntryShowDto(logEntry);
                amountDistance += logEntry.getRoute().getDistance();
                res.add(logEntryShowDto);
            }
        }
        return amountDistance;
    }

    private void GroupedByDate(List<GroupedAndFilteredLogEntry> logEntryList, LogEntryShowDto re, boolean isExist) {
        if (isExist) {
            for (GroupedAndFilteredLogEntry filteredLogEntry : logEntryList) {
                if (filteredLogEntry.getLogEntryDate().equals(re.getLogEntryDate())) {
                    List<LogEntryShowDto> showDtoList = filteredLogEntry.getShowDtoList();
                    showDtoList.add(re);
                    break;
                }
            }
        } else {
            logEntryList.add(
                    new GroupedAndFilteredLogEntry(re.getLogEntryDate(), new ArrayList<>(List.of(re)))
            );
        }
    }

    private boolean isExistTime(LogEntryShowDto re, List<GroupedAndFilteredLogEntry> logEntryList) {
        boolean isExist = false;
        for (GroupedAndFilteredLogEntry filteredLogEntry : logEntryList) {
            if (filteredLogEntry.getLogEntryDate().equals(re.getLogEntryDate())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    private boolean isaBoolean(LogEntryFilterMethod filterMethod, LogEntry logEntry) {
        return (filterMethod.getJourneyDateFrom().equals(logEntry.getJourneyDate()) ||
                filterMethod.getJourneyDateFrom().isAfter(logEntry.getJourneyDate()))
                && (filterMethod.getJourneyDateTo().equals(logEntry.getJourneyDate()) ||
                filterMethod.getJourneyDateTo().isBefore(logEntry.getJourneyDate()));
    }


    private boolean checkDateInterval(LogEntryFilterMethod filterMethod) {
        try {
            LocalDateTime journeyDateFrom = filterMethod.getJourneyDateFrom();
            LocalDateTime journeyDateTo = filterMethod.getJourneyDateTo();
            int hour = journeyDateFrom.getHour();
            int hour1 = journeyDateTo.getHour();
            System.out.println(hour1);
            System.out.println(hour);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
