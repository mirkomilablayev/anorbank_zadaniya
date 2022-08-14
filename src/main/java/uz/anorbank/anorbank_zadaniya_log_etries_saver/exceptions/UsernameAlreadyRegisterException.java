package uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UsernameAlreadyRegisterException extends RuntimeException{
    private String msg;
}
