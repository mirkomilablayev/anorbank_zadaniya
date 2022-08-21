package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.RepositoryLayer;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class LogEntryRepoTest {

    @Autowired
    private LogEntryRepo underTest;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private VehicleRepo vehicleRepo;
    @Autowired
    private RouteRepo routeRepo;


    @Test
    void checkIfLogEntryIfExistByRouteIdAndIsDeleted() {
        Route savedRoue = getRoute();
        Boolean expected = underTest.existsByRoute_IdAndIsDeleted(savedRoue.getId(), false);
        assertThat(expected).isTrue();
    }

    @Test
    void checkIfLogEntryIfDoesNotExistByRouteIdAndIsDeleted() {
        Route savedRoue = getRoute();
        Boolean expected = underTest.existsByRoute_IdAndIsDeleted(savedRoue.getId() + 1, false);
        assertThat(expected).isFalse();
    }

    private Route getRoute() {
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole driverRole = roleRepo.save(new UserRole(Constant.DRIVER));
        //given
        String username = "mirkomil_ablayev1";
        User savedUser = userRepo.save(new User(
                "Mirkomil Ablayev",
                LocalDate.now().minusYears(19),
                "Samarqand Ishtixon",
                "+998945331738",
                null,
                false,
                username,
                "1212",
                new HashSet<>(Arrays.asList(userRole, driverRole))
        ));

        Vehicle savedVehicle = vehicleRepo.save(new Vehicle(
                "Nexia",
                savedUser,
                "Blue",
                "AS191991",
                "30Y287KA",
                198000,
                260000,
                false
        ));

        Route savedRoue = routeRepo.save(new Route(
                "Samarkand",
                "Tashkent",
                240,
                false,
                savedUser
        ));


        LogEntry savedLogEntry = underTest.save(new LogEntry(
                LocalDateTime.now().minusDays(3),
                "Journay Short Desktription",
                savedUser,
                savedVehicle,
                savedRoue,
                false
        ));
        return savedRoue;
    }

    private LogEntry getLogEntryId() {
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole driverRole = roleRepo.save(new UserRole(Constant.DRIVER));
        //given
        String username = "mirkomil_ablayev1";
        User savedUser = userRepo.save(new User(
                "Mirkomil Ablayev",
                LocalDate.now().minusYears(19),
                "Samarqand Ishtixon",
                "+998945331738",
                null,
                false,
                username,
                "1212",
                new HashSet<>(Arrays.asList(userRole, driverRole))
        ));

        Vehicle savedVehicle = vehicleRepo.save(new Vehicle(
                "Nexia",
                savedUser,
                "Blue",
                "AS191991",
                "30Y287KA",
                198000,
                260000,
                false
        ));

        Route savedRoue = routeRepo.save(new Route(
                "Samarkand",
                "Tashkent",
                240,
                false,
                savedUser
        ));


        return underTest.save(new LogEntry(
                LocalDateTime.now().minusDays(3),
                "Journay Short Desktription",
                savedUser,
                savedVehicle,
                savedRoue,
                false
        ));
    }


    @Test
    void checkFindingLogEntryByIdAndIsDeleted(){
        LogEntry logEntry = getLogEntryId();
        LogEntry expected = underTest.findByIdAndIsDeleted(logEntry.getId(), false).orElseThrow(ResourceNotFoundException::new);
        assertThat(expected).isEqualTo(logEntry);
    }

    @Test
    void checkThrowingExceptionIfCannotBeFoundLogEntryByIdAndIsDeleted(){
        LogEntry logEntry = getLogEntryId();
        underTest.findByIdAndIsDeleted(logEntry.getId(), false).orElseThrow(ResourceNotFoundException::new);

    }

}