package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogEntryResponse{
    private List<GroupedAndFilteredLogEntry> showDtoList;
    private Integer amountDistance;
}
