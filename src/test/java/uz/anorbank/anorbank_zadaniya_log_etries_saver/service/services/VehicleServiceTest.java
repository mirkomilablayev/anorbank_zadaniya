package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleShowDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Vehicle;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ConflictException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.VehicleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {


    @InjectMocks
    private VehicleService underService;
    @Mock
    private Util util;
    @Mock
    private VehicleRepo vehicleRepo;


    @BeforeEach
    void setUp() {

    }

    @Test
    void canCreateNewVehicle() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
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
        lenient().when(util.getCurrentUser()).thenReturn(user);

        lenient().when(vehicleRepo.existsByCarNumberAndIsDeleted("123", false)).thenReturn(false);
        VehicleCreateDto dto = new VehicleCreateDto("1", "blue", "1", 1212, "nexia");
        assertEquals(underService.create(dto), ResponseEntity.ok("Success"));
    }


    @Test
    void itShouldThrowExceptionIfCarNumberAlreadyAdded() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
        User user = new User(
                "Mirkomil Ablayev",
                LocalDate.now().minusYears(19),
                "Samarqand Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(List.of(userRole))
        );
        user.setId(10L);
        lenient().when(util.getCurrentUser()).thenReturn(user);
        lenient().when(vehicleRepo.existsByCarNumberAndIsDeleted("123", false)).thenReturn(true);
        VehicleCreateDto dto = new VehicleCreateDto("1", "blue", "1", 1212, "nexia");
        assertThrows(ConflictException.class, () -> underService.create(dto));
    }


    @Test
    void canCreateNewVehcile() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
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

        lenient().when(util.getCurrentUser()).thenReturn(user);

        lenient().when(vehicleRepo.existsByCarNumberAndIsDeleted("123", false)).thenReturn(false);
        VehicleCreateDto dto = new VehicleCreateDto("1", "blue", "1", 1212, "nexia");
        assertEquals(underService.create(dto), ResponseEntity.ok("Success"));
    }

    @Test
    void canUpdateVehicle() {
        Vehicle vehicle = new Vehicle(
                "Nexia",
                null,
                "12",
                "Blue",
                "1212",
                12000,
                15000,
                false
        );
        vehicle.setId(12L);
        lenient().when(vehicleRepo.findByIdAndIsDeleted(12L, false)).thenReturn(Optional.of(vehicle));
        VehicleUpdateDto dto = new VehicleUpdateDto(
                12L, "Black", "9890", "Malibu"
        );
        assertEquals(underService.update(dto), ResponseEntity.ok("Success"));

    }

    @Test
    void canGetVehicleById() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
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

        Vehicle vehicle = new Vehicle(
                "Nexia",
                user,
                "12",
                "Blue",
                "1212",
                12000,
                15000,
                false
        );
        vehicle.setId(12L);
        lenient().when(vehicleRepo.findByIdAndIsDeleted(12L, false)).thenReturn(Optional.of(vehicle));

        VehicleShowDto vehicleShowDto = extracted(vehicle);

        assertEquals(underService.get(12L), ResponseEntity.ok(vehicleShowDto));

    }


    @Test
    void itShouldThrowIfVehicleNotBeFound() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
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

        Vehicle vehicle = new Vehicle(
                "Nexia",
                user,
                "12",
                "Blue",
                "1212",
                12000,
                15000,
                false
        );
        vehicle.setId(12L);
        lenient().when(vehicleRepo.findByIdAndIsDeleted(12L, false)).thenReturn(Optional.of(vehicle));

        extracted(vehicle);

        assertThrows(ResourceNotFoundException.class, () -> underService.get(13L));

    }

    private VehicleShowDto extracted(Vehicle vehicle) {
        VehicleShowDto vehicleShowDto = new VehicleShowDto();
        vehicleShowDto.setType(vehicle.getType());
        vehicleShowDto.setOwnerName(vehicle.getUser().getFullName());
        vehicleShowDto.setRegistrationNumber(vehicle.getRegistrationNumber());
        vehicleShowDto.setCarColor(vehicle.getCarColor());
        vehicleShowDto.setCarNumber(vehicle.getCarNumber());
        vehicleShowDto.setCurrentTotalOdometerNumber(vehicle.getCurrentTotalOdometerNumber());
        return vehicleShowDto;
    }


    @Test
    void canDeleteById() {
        Vehicle vehicle = new Vehicle(
                "Nexia",
                null,
                "12",
                "Blue",
                "1212",
                12000,
                15000,
                false
        );
        vehicle.setId(12L);
        lenient().when(vehicleRepo.findByIdAndIsDeleted(12L, false)).thenReturn(Optional.of(vehicle));
        assertEquals(underService.deleteById(12L), ResponseEntity.ok("Successfully deleted"));
    }



    @Test
    void itShouldThrowExceptionIfNothingFound() {
        Vehicle vehicle = new Vehicle(
                "Nexia",
                null,
                "12",
                "Blue",
                "1212",
                12000,
                15000,
                false
        );
        vehicle.setId(12L);
        lenient().when(vehicleRepo.findByIdAndIsDeleted(12L, false)).thenReturn(Optional.of(vehicle));
        assertThrows(ResourceNotFoundException.class,() -> underService.deleteById(11L));
    }

}