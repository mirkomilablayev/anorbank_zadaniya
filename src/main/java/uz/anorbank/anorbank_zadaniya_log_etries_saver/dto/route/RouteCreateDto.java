package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.Dto;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RouteCreateDto  implements Dto {
    private String from_destination;
    private String end_destination;
    private Integer distance;
}
