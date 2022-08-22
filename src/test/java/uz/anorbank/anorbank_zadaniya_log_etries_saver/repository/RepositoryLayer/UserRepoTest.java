package uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.RepositoryLayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RoleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.UserRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo underTest;
    @Autowired
    private RoleRepo roleRepo;


    /**
     * this test has to check existing user by its Username and isDeleted fields
     */
    @Test
    void itShouldCheckUserIfExistByUsernameAndIsDeleted(){
        //given
        String username = "mirkomil_ablayev1";
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole driverRole = roleRepo.save(new UserRole(Constant.DRIVER));
        User savedUser = underTest.save(new User(
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

        //when
        Boolean expected = underTest.existsByUsernameAndIsDeleted(savedUser.getUsername(), false);

        //then
        assertThat(expected).isTrue();
    }

    /**
     * this test has to check if user does not exist by Username and isDeleted fields
     */
    @Test
    void itShouldCheckUserIfDoesNotExistByUsernameAndIsDeleted(){
        Boolean expected = underTest.existsByUsernameAndIsDeleted("any Username", false);
        assertThat(expected).isFalse();
    }


    /**
     * this test has to check user by its username and isDeleted fielsd
     */

    @Test
    void itShouldCheckFindUserByUsernameAndIsDeleted(){
        //given
        String username = "mirkomil_ablayev1";
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole driverRole = roleRepo.save(new UserRole(Constant.DRIVER));
        User savedUser = underTest.save(new User(
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
        //when
        User expected = underTest.findByUsernameAndIsDeleted(username, false).orElseThrow(ResourceNotFoundException::new);
        //then
        assertThat(expected).isEqualTo(savedUser);
    }

    /**
     * this test has to chech throwing an exception when User can't be found by its username and isDeleted
     */
    @Test
    void itShouldCheckThrowingExceptionIfDoesNotFindUserByUsernameAndIsDeleted(){
        //given
        String username = "mirkomil_ablayev1";
        assertThrows(ResourceNotFoundException.class, () -> underTest.findByUsernameAndIsDeleted(username+"1", false).orElseThrow(ResourceNotFoundException::new));
    }

}