package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;

import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogEntryCreateDto implements Dto {
    private LocalDateTime logEntryTime;
    private String journeyShortDescription;
    private Long vehicleId;
    private Long routeId;

}
