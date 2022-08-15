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
public class Vehicle extends BaseEntityId implements BaseEntity {
    private String type;
    @ManyToOne
    private User user;
    private String registrationNumber;
    private String carColor;
    private String carNumber;
    private Integer totalOdometerNumberAtRegistration = 0;
    private Integer currentTotalOdometerNumber = 0;
    private Boolean isDeleted = false;

}
