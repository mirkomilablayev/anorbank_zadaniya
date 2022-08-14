package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Route;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Vehicle;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogEntryUpdateDto implements Dto {
    private Long logEntryId;
    private LocalDateTime journeyDate;
    private String journeyShortDescription;
    private Long vehicleId;
    private Long routeId;
}
