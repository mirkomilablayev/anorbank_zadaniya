package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;

import javax.persistence.ManyToOne;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VehicleShowDto {
    private String type;
    private String ownerName;
    private String registrationNumber;
    private String carColor;
    private String carNumber;
    private Integer currentTotalOdometerNumber;
}
