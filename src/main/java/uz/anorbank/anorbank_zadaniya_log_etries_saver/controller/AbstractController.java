package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller;


import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;

public abstract class AbstractController <S extends BaseService>{
public final S service;
public AbstractController(S service){this.service = service;}
}
