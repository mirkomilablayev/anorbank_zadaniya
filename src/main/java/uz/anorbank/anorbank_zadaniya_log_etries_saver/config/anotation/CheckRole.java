package uz.anorbank.anorbank_zadaniya_log_etries_saver.config.anotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRole {
 String value();
}
