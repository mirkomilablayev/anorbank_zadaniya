package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogEntryFilterMethod {
    private LocalDateTime journeyDateFrom;
    private LocalDateTime journeyDateTo;
    private String transportRegistrationNumber;
    private String transportOwnerName;
}
