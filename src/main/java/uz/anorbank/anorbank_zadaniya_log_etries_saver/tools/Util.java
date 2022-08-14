package uz.anorbank.anorbank_zadaniya_log_etries_saver.tools;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;


@Component
public class Util {

    public boolean checkBlank(String str) {
        boolean empty = str.trim().isEmpty();
        return empty;
    }

    public Long getCurrentUserId() {
        return ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
        ).getId();
    }


    public User getCurrentUser() {
        return ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
        );
    }

}
