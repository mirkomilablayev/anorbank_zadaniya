package uz.anorbank.anorbank_zadaniya_log_etries_saver.entity;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.baseEntities.BaseEntity;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.baseEntities.BaseEntityId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LogEntry extends BaseEntityId implements BaseEntity {
    private LocalDateTime journeyDate;
    private String journeyShortDescription;
    @ManyToOne
    private User user;
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne
    private Route route;
    private Boolean isDeleted = false;

}
