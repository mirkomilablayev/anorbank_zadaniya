package uz.anorbank.anorbank_zadaniya_log_etries_saver.config.anotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;

import javax.ws.rs.ForbiddenException;

@Component
@Aspect
public class CheckRoleExecutor {
    @Before(value = "@annotation(checkRole)")
    public void checkRoleMethod(CheckRole checkRole) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean flag = false;
        for (UserRole userRole : user.getUserRoleSet()) {
            if (userRole.getAuthority().equalsIgnoreCase(checkRole.value())) {
                flag = true;
                break;
            }
        }
        if (!flag){
            throw new ForbiddenException("You don't have a permission");
        }
    }
}
