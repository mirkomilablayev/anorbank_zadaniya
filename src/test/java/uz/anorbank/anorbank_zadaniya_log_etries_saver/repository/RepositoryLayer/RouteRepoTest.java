package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.RepositoryLayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Route;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RoleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RouteRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.UserRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class RouteRepoTest {

    @Autowired
    private RouteRepo underTest;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Test
    void itShouldCheckFindingRouteByIdAndIsDeletedFields() {
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

        Route savedRoute = underTest.save(new Route(
                "Samarkand",
                "Tashkent",
                240,
                false,
                savedUser
        ));

        //when
        Route expected = underTest.findByIdAndIsDeleted(savedRoute.getId(), false).orElseThrow(ResourceNotFoundException::new);

        //then
        assertThat(expected).isEqualTo(savedRoute);
    }


    @Test
    void itShouldCheckDoesNotFindingRouteByIdAndIsDeletedFields() {
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

        Route savedRoute = underTest.save(new Route(
                "Samarkand",
                "Tashkent",
                240,
                false,
                savedUser
        ));

        //when
        //then
        assertThrows(ResourceNotFoundException.class, () -> {
            underTest.findByIdAndIsDeleted((savedRoute.getId() + 1), false).orElseThrow(ResourceNotFoundException::new);
        });
    }


    @Test
    void itShouldCheckGettingAllRoutesByUserIdAndIsDeleted() {
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

         underTest.save(new Route(
                "Samarkand",
                "Tashkent",
                240,
                false,
                savedUser
        ));

         underTest.save(new Route(
                "Tashkent",
                "Samarkand",
                240,
                false,
                savedUser
        ));

        List<Route> routeList = underTest.findAllByUser_IdAndIsDeleted(savedUser.getId(), false);
        assertThat(routeList.size()).isGreaterThan(0);
    }

    @Test
    void itShouldCheckIfCannotGetAllRoutesByUserIdAndIsDeleted() {
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

        underTest.save(new Route(
                "Samarkand",
                "Tashkent",
                240,
                false,
                savedUser
        ));

        underTest.save(new Route(
                "Tashkent",
                "Samarkand",
                240,
                false,
                savedUser
        ));

        List<Route> routeList = underTest.findAllByUser_IdAndIsDeleted((savedUser.getId() + 1), false);
        assertThat(routeList.size()).isEqualTo(0);
    }
}