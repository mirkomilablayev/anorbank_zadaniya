package uz.anorbank.anorbank_zadaniya_log_etries_saver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.baseEntities.BaseEntity;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.baseEntities.BaseEntityId;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "user_roles")
public class UserRole extends BaseEntityId implements GrantedAuthority, BaseEntity {
    private String name;
    private Boolean isActive = true;
    @Override
    public String getAuthority() {
        return name;
    }

    public UserRole(String name){
        this.name = name;
    }
}
