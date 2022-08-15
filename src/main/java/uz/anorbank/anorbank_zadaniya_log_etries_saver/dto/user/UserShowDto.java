package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.user;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.File;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserShowDto {
    private String fullName;
    private LocalDate birthDate;
    private String birthPlace;
    private String phoneNumber;
    private Long logoFileId;
    private String status;
    private String username;
    private List<String> roles;
}
