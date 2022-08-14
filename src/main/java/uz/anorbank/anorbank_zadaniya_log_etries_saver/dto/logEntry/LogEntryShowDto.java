package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogEntryShowDto {
    private LocalDateTime logEntryDate;
    private String vehicleType;
    private String vehicleRegistrationNumber;
    private String vehicleOwnersName;
    private Integer odometerStart;
    private Integer odometerEnd;
    private String route;
    private String journeyShortDescription;
}
