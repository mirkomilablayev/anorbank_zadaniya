package uz.anorbank.anorbank_zadaniya_log_etries_saver.entity;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.baseEntities.BaseEntity;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.baseEntities.BaseEntityId;

import javax.persistence.Entity;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Route extends BaseEntityId implements BaseEntity {
    private String fromDestination;
    private String endDestination;
    private Integer distance = 0;
    private Boolean isDeleted = false;
}
