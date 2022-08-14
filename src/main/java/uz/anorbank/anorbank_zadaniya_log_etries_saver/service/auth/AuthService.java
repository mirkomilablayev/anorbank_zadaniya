package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.user.RegisterDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ConflictException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.UserRoleNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.UsernameAlreadyRegisterException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RoleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.UserRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService, BaseService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final Util util;



    public HttpEntity<?> register(RegisterDto cd) {
        if (!userRepo.existsByUsernameAndIsDeleted(cd.getUsername(), false)) {
          User user = mapToUser(cd);
          User save = userRepo.save(user);
          return ResponseEntity.ok(save.getFirstName()+" "+save.getLastName() +" IS SUCCESSFULLY SAVED");
        }
        throw new UsernameAlreadyRegisterException(cd.getUsername() + " is already registered");
    }

    private User mapToUser(RegisterDto cd) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(cd.getPassword()));
        user.setUsername(cd.getUsername());
        user.setBirthDate(cd.getBirthDate());
        user.setBirthPlace(cd.getBirthPlace());
        user.setFirstName(cd.getFirstName());
        user.setLastName(cd.getLastName());
        if (cd.getAsUser())
        user.setUserRoleSet(new HashSet<>(List.of(roleRepo.findByNameAndIsActive(Constant.USER, true).orElseThrow(ResourceNotFoundException::new))));
        else
            user.setUserRoleSet(new HashSet<>(List.of(roleRepo.findByNameAndIsActive(Constant.DRIVER, true).orElseThrow(ResourceNotFoundException::new))));
        user.setIsDeleted(false);
        user.setPhoneNumber(cd.getPhoneNumber());
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsernameAndIsDeleted(username, false).orElseThrow(() -> new ResourceNotFoundException(""));
    }
}
