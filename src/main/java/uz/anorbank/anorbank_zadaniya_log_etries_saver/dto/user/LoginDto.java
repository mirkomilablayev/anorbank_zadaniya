package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto  implements Dto {
    private String username;
    private String password;
}
