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
public class File extends BaseEntityId implements BaseEntity {
    private String contentType;
    private String originalName;
    private String generatedName;
    private String filePath;
    private Boolean isDeleted = false;
}
