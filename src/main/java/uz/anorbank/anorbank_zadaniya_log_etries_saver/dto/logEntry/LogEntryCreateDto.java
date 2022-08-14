package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.logEntry;

import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;

import java.time.LocalDateTime;

public class LogEntryCreateDto implements Dto {
    private LocalDateTime logEntryTime;
    private String journeyShortDescription;

}
