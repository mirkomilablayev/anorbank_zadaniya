package uz.anorbank.anorbank_zadaniya_log_etries_saver.service;


import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.BaseRepository;

public abstract class AbstractService<R extends BaseRepository> implements BaseService{
    public final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }
}




