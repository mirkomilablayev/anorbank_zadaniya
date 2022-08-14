package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RouetUpdateDto  implements Dto {
    private Long routeId;
    private String startDestination;
    private String endDestination;
    private Integer distance;
}
