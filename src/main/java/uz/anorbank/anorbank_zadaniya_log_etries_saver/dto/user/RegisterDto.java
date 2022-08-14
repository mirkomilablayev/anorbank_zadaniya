package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto  implements Dto {
    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String birthPlace;

    @Column(nullable = false)
    private String phoneNumber;


    private Boolean isDeleted = false;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean asUser = true;

}

