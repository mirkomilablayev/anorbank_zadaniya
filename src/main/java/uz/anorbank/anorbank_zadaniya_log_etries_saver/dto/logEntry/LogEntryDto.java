package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogEntryDto {
    private Boolean enableToGroupedByJourneyDate = true;
    private Boolean isSortedInIncOrder = true;

}
