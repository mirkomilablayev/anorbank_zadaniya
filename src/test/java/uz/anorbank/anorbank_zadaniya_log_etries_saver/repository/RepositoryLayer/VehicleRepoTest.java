package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.RepositoryLayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Vehicle;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RoleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.UserRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.VehicleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class VehicleRepoTest {

    @Autowired
    private VehicleRepo underTest;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;




    /**
     * this test has to check vehicle by its carNumber and isDeleted fields
     * real jpa query return true if it exists by those fields
     */
    @Test
    void itShouldCheckVehicleIfVehicleExistsByCarNumberAndIsDeleted() {
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole adminRole = roleRepo.save(new UserRole(Constant.DRIVER));

        String carNumber = "30Y287KA";
        User user = new User(
                "Mirkomil",
                LocalDate.now().minusYears(19),
                "Samarkand, Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(Arrays.asList(
                        userRole,adminRole
                ))
                );
        User savedUser = userRepo.save(user);
        Vehicle vehicle = new Vehicle(
                "Nexia",
                savedUser,
                "Blue",
                "AS191991",
                carNumber,
                198000,
                260000,
                false
                );
        underTest.save(vehicle);

        //when
        Boolean expected = underTest.existsByCarNumberAndIsDeleted(carNumber, false);

        //then
        assertThat(expected).isTrue();
    }


    /**
     * this test has to check vehicle by its carNumber and isDeleted fields
     * real jpa query return true if it exists by those fields
     */
    @Test
    void itShouldCheckVehicleIfVehicleExistsByCarNumberAndIsDeleted2() {
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole adminRole = roleRepo.save(new UserRole(Constant.DRIVER));

        String carNumber = "30Y287KA";
        User user = new User(
                "Mirkomil",
                LocalDate.now().minusYears(19),
                "Samarkand, Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(Arrays.asList(
                        userRole,adminRole
                ))
        );
        User savedUser = userRepo.save(user);
        Vehicle vehicle = new Vehicle(
                "Nexia",
                savedUser,
                "Blue",
                "AS191991",
                carNumber,
                198000,
                260000,
                false
        );
        underTest.save(vehicle);

        //when
        Boolean expected = underTest.existsByCarNumberAndIsDeleted("let's imagine here we put Another Car Number", false);

        //then
        assertThat(expected).isFalse();
    }


    /**
     * this test check finding vehicle by its id and isDeleted fields
     */
    @Test
    void itShouldCheckVehicleIfVehicleFindByIdAndIsDeleted() {
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole adminRole = roleRepo.save(new UserRole(Constant.DRIVER));

        String carNumber = "30Y287KA";
        User user = new User(
                "Mirkomil",
                LocalDate.now().minusYears(19),
                "Samarkand, Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(Arrays.asList(
                        userRole,adminRole
                ))
        );
        User savedUser = userRepo.save(user);
        Vehicle vehicle = new Vehicle(
                "Nexia",
                savedUser,
                "Blue",
                "AS191991",
                carNumber,
                198000,
                260000,
                false
        );
        Vehicle savedVehicle = underTest.save(vehicle);

        //when
        Vehicle vehicle1 = underTest.findByIdAndIsDeleted(savedVehicle.getId(), false).orElseThrow(ResourceNotFoundException::new);

        //then
        assertThat(vehicle1).isEqualTo(savedVehicle);
    }


    /**
     * this test check throwing exception if vehicle cannot be found by vehicle id and its isDeleted fields
     */
    @Test
    void itShouldIfVehicleCannotBeFoundByIdAndIsDeleted2() {
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole adminRole = roleRepo.save(new UserRole(Constant.DRIVER));

        String carNumber = "30Y287KA";
        User user = new User(
                "Mirkomil",
                LocalDate.now().minusYears(19),
                "Samarkand, Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(Arrays.asList(
                        userRole,adminRole
                ))
        );
        User savedUser = userRepo.save(user);
        Vehicle vehicle = new Vehicle(
                "Nexia",
                savedUser,
                "Blue",
                "AS191991",
                carNumber,
                198000,
                260000,
                false
        );
        Vehicle savedVehicle = underTest.save(vehicle);

        //when

        //then
        assertThrows(ResourceNotFoundException.class, () -> underTest.findByIdAndIsDeleted((savedVehicle.getId() + 1), false).orElseThrow(ResourceNotFoundException::new));
    }



}