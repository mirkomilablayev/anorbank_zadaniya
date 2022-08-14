package uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions;


import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConflictException extends RuntimeException{
    private String msg;
}
