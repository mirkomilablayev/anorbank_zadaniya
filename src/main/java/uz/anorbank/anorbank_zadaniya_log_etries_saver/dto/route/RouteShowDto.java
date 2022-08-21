package uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route;

import lombok.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;

import javax.persistence.ManyToOne;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RouteShowDto {
    private String fromDestination;
    private String endDestination;
    private Integer distance = 0;

}
