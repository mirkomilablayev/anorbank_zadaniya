package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VehicleCreateDto  implements Dto {
    private String registrationNumber;
    private String vehicleColor;
    private String vehicleNumber;
    private int odometerValueAtRegistration;
    private String type;

}
