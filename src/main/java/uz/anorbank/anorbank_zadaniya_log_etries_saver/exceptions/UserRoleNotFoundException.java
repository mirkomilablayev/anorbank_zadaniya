package uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleNotFoundException extends RuntimeException{
    private String msg;
}
