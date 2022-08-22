package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryShowDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry.LogEntryUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.LogEntryRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.OdometerHistoryRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;


@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@ExtendWith(MockitoExtension.class)
class LogEntryServiceTest {

    @InjectMocks
    private LogEntryService underTest;
    @Mock
    private LogEntryRepo logEntryRepo;
    @Mock
    private OdometerHistoryRepo odometerHistoryRepo;

    private LogEntry createMockLogEntry(Route route, Vehicle vehicle, User user) {
        LogEntryCreateDto dto = new LogEntryCreateDto(
                LocalDateTime.now().minusDays(3),
                "Shunaqa gaplar",
                vehicle.getId(),
                route.getId()
        );
        LogEntry logEntry = new LogEntry(
                dto.getLogEntryTime(),
                dto.getJourneyShortDescription(),
                user,
                vehicle,
                route,
                false
        );
        logEntry.setId(1L);
        return logEntry;
    }

    private Vehicle extracted1(User user) {
        Vehicle vehicle = new Vehicle(
                "Nexia", user, "12", "Blue", "12", 12000, 13000, false
        );
        vehicle.setId(12L);
        return vehicle;
    }

    private User getUser(UserRole userRole, UserRole userRole1) {
        User user = new User(
                "Mirkomil Ablayev",
                LocalDate.now().minusYears(19),
                "Samarqand Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(Arrays.asList(userRole, userRole1))
        );
        user.setId(10L);
        return user;
    }

    private Route extracted(User user) {
        Route route = new Route(
                "A", "B", 120, false, user
        );
        route.setId(10L);
        return route;
    }

    @Test
    void canUpdateLogEntry() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
        User user = getUser(userRole, userRole1);
        Route extracted = extracted(user);
        Vehicle vehicle = extracted1(user);
        LogEntry mockLogEntry = createMockLogEntry(extracted, vehicle, user);
        lenient().when(logEntryRepo.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(mockLogEntry));
        LogEntryUpdateDto logEntryUpdateDto = new LogEntryUpdateDto(
                1L,LocalDateTime.now(),"Updated Log Entry", 12L, 10L
        );
        assertEquals(underTest.update(logEntryUpdateDto), ResponseEntity.ok("Success"));
    }

    @Test
    void canGetLogEntryShowDto() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
        User user = getUser(userRole, userRole1);
        Route extracted = extracted(user);
        Vehicle vehicle = extracted1(user);
        LogEntry mockLogEntry = createMockLogEntry(extracted, vehicle, user);
        OdometerHistory odometerHistory = getOdometerHistory(mockLogEntry);
        lenient().when(odometerHistoryRepo.findByLogEntry_IdAndIsDeleted(mockLogEntry.getId(), false)).thenReturn(Optional.of(odometerHistory));
        lenient().when(logEntryRepo.findByIdAndIsDeleted(1L, false)).thenReturn(Optional.of(mockLogEntry));

        LogEntryShowDto extracted1 = extracted(odometerHistory, mockLogEntry);
        assertEquals(underTest.get(mockLogEntry.getId()),ResponseEntity.ok(extracted1));
    }

    private LogEntryShowDto extracted(OdometerHistory odometerHistory, LogEntry logEntry) {
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

    private OdometerHistory getOdometerHistory(LogEntry logEntryRepo) {
        OdometerHistory odometerHistory = new OdometerHistory(
                12,12,logEntryRepo.getVehicle(),logEntryRepo,false
        );
        odometerHistory.setId(1L);
        return odometerHistory;
    }

    @Test
    void deleteById() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
        User user = getUser(userRole, userRole1);
        Route extracted = extracted(user);
        Vehicle vehicle = extracted1(user);
        LogEntry mockLogEntry = createMockLogEntry(extracted, vehicle, user);
        lenient().when(logEntryRepo.findByIdAndIsDeleted(mockLogEntry.getId(), false)).thenReturn(Optional.of(mockLogEntry));
        assertEquals(underTest.deleteById(mockLogEntry.getId()), ResponseEntity.ok("Success"));
    }

}