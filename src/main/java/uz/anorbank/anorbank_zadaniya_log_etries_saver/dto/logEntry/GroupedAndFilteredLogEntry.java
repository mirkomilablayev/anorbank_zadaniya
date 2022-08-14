package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GroupedAndFilteredLogEntry {
    private LocalDateTime logEntryDate;
    private List<LogEntryShowDto> showDtoList;
}
