package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouetUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouteCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouteShowDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Route;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ConflictException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.LogEntryRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RouteRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.AbstractService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.CrudService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService extends AbstractService<RouteRepo> implements BaseService, CrudService<RouteCreateDto, RouetUpdateDto> {
    private final Util util;
    private final LogEntryRepo logEntryRepo;
    public RouteService(RouteRepo repository, Util util, LogEntryRepo logEntryRepo) {
        super(repository);
        this.util = util;
        this.logEntryRepo = logEntryRepo;
    }

    @Override
    public HttpEntity<?> create(RouteCreateDto cd) {
        Route route = mapDtotoRoute(cd);
        repository.save(route);
        return ResponseEntity.ok("Success");
    }

    private Route mapDtotoRoute(RouteCreateDto cd) {
        Route route = new Route();
        if (cd.getDistance() > 0) {
            route.setDistance(cd.getDistance());
        }else {
            throw new ConflictException("Distance doesn't have to be negative number");
        }
        route.setEndDestination(cd.getEnd_destination());
        route.setFromDestination(cd.getFrom_destination());
        route.setUser(util.getCurrentUser());
        route.setIsDeleted(false);
        return route;
    }

    @Override
    public HttpEntity<?> update(RouetUpdateDto cd) {
        Route route = repository.findByIdAndIsDeleted(cd.getRouteId(), false).orElseThrow(ResourceNotFoundException::new);
        if (!route.getFromDestination().equals(cd.getStartDestination())){
            route.setFromDestination(cd.getStartDestination());
        }

        if (!route.getEndDestination().equals(cd.getEndDestination())){
            route.setEndDestination(cd.getEndDestination());
        }

        if (!route.getDistance().equals(cd.getDistance())){
            route.setDistance(cd.getDistance());
        }

        repository.save(route);
        return ResponseEntity.ok("Success");
    }

    @Override
    public HttpEntity<?> get(Long id) {
        Route route = repository.findByIdAndIsDeleted(id, false).orElseThrow(ResourceNotFoundException::new);
        RouteShowDto routeShowDto = makeRouteShowDto(route);
        return ResponseEntity.ok(routeShowDto);
    }

    private RouteShowDto makeRouteShowDto(Route route) {
        RouteShowDto routeShowDto = new RouteShowDto();
        routeShowDto.setDistance(routeShowDto.getDistance());
        routeShowDto.setEndDestination(route.getEndDestination());
        routeShowDto.setFromDestination(route.getFromDestination());
        return routeShowDto;
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        Boolean aBoolean = logEntryRepo.existsByRoute_IdAndIsDeleted(id, false);
        if (aBoolean){
            throw new ConflictException("Ushbu yunalish avval ishlatilgan siz ushbu yunalishni o'chirolmaysiz");
        }else {
            Route route = repository.findByIdAndIsDeleted(id, false).orElseThrow(ResourceNotFoundException::new);
            route.setIsDeleted(true);
            repository.save(route);
            return ResponseEntity.ok("Success");
        }
    }

    public HttpEntity<?> getMyRoutes() {
        Long currentUserId = util.getCurrentUserId();
        List<Route> routeList = repository.findAllByUser_IdAndIsDeleted(currentUserId, false);
        List<RouteShowDto> routeShowDtos = new ArrayList<>();
        for (Route route : routeList) {
            RouteShowDto routeShowDto = makeRouteShowDto(route);
            routeShowDtos.add(routeShowDto);
        }
        return ResponseEntity.ok(routeShowDtos);
    }
}
