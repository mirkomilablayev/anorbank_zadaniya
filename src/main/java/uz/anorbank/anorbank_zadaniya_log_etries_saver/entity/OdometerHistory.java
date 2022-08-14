package uz.anorbank.anorbank_zadaniya_log_etries_saver.entity;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.baseEntities.BaseEntity;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.baseEntities.BaseEntityId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OdometerHistory extends BaseEntityId implements BaseEntity {
    private int odometerStart;
    private int odometerEnd;
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne
    private LogEntry logEntry;
    private Boolean isDeleted = false;
}
