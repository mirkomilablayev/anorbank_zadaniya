package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.stereotype.Service;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.UserRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.AbstractService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
@Service
public class UserService extends AbstractService<UserRepo> implements BaseService {
    public UserService(UserRepo repository) {
        super(repository);
    }
}
