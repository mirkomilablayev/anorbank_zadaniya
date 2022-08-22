package uz.anorbank.anorbank_zadaniya_log_etries_saver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouetUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouteCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouteShowDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Route;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ConflictException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.LogEntryRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RoleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RouteRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services.RouteService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {

    @Mock
    private RoleRepo roleRepo;

    @Mock
    private RouteRepo routeRepo;

    @Mock
    private LogEntryRepo logEntryRepo;


    @Mock
    private Util util;

    @InjectMocks
    private RouteService routeService;

    private final List<Route> routeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Route route = new Route("Samarkand", "Toshkent", 100, false, null);
        route.setId(7L);
        routeList.add(route);
    }

    @Test
    public void canGetMyRoutes() {
        lenient().when(routeRepo.findAllByUser_IdAndIsDeleted(1L, false)).thenReturn(routeList);
        lenient().when(util.getCurrentUserId()).thenReturn(1L);

        routeService.getMyRoutes();

        verify(routeRepo, times(1)).findAllByUser_IdAndIsDeleted(1L, false);
    }


    @Test
    void canBeDeleted() {
        Route route = new Route("Samarkand", "Toshkent", 100, false, null);
        route.setId(7L);

        lenient().when(logEntryRepo.existsByRoute_IdAndIsDeleted(7L, false)).thenReturn(false);
        lenient().when(routeRepo.findByIdAndIsDeleted(7L, false)).thenReturn(Optional.of(route));

        assertEquals(routeService.deleteById(7L), ResponseEntity.ok("Success"));
    }

    @Test
    void itShouldThrowIfRouteUsedForLogEntry(){
        Route route = new Route("Samarkand", "Toshkent", 0, false, null);
        route.setId(7L);
        lenient().when(logEntryRepo.existsByRoute_IdAndIsDeleted(7L, false)).thenReturn(true);
        assertThrows(ConflictException.class, () -> routeService.deleteById(7L));
    }

    @Test
    void canGetRouteById(){
        Route route = new Route("Samarkand", "Toshkent", 100, false, null);
        route.setId(7L);
        lenient().when(routeRepo.findByIdAndIsDeleted(7L, false)).thenReturn(Optional.of(route));

        RouteShowDto routeShowDto = new RouteShowDto();
        routeShowDto.setDistance(route.getDistance());
        routeShowDto.setEndDestination(route.getEndDestination());
        routeShowDto.setFromDestination(route.getFromDestination());

        assertEquals(routeService.get(7L), ResponseEntity.ok(routeShowDto));
    }

    @Test
    void itShouldThrowExceptionIfRouteCannotBeFound(){
        Route route = new Route("Samarkand", "Toshkent", 100, false, null);
        route.setId(7L);
        lenient().when(routeRepo.findByIdAndIsDeleted(7L, false)).thenReturn(Optional.of(route));

        RouteShowDto routeShowDto = new RouteShowDto();
        routeShowDto.setDistance(route.getDistance());
        routeShowDto.setEndDestination(route.getEndDestination());
        routeShowDto.setFromDestination(route.getFromDestination());

        assertThrows(ResourceNotFoundException.class,() -> routeService.get(8L));
    }

    @Test
    void canUpdateRoute(){
        Route route = new Route("Samarkand", "Tashkent",100,false,null);
        route.setId(10L);
        lenient().when(routeRepo.findByIdAndIsDeleted(10L, false)).thenReturn(Optional.of(route));
        RouetUpdateDto rouetUpdateDto = RouetUpdateDto.builder()
                .routeId(10L)
                .startDestination("Buxara")
                .endDestination("Namangan")
                .distance(102).build();     
        assertEquals(routeService.update(rouetUpdateDto), ResponseEntity.ok("Success"));
    }



    @Test
    void canCreateNewRoute(){
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole driverRole = roleRepo.save(new UserRole(Constant.DRIVER));

        User user = new User(
                "Mirkomil Ablayev",
                LocalDate.now().minusYears(19),
                "Samarqand Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(Arrays.asList(userRole, driverRole))
        );

        lenient().when(util.getCurrentUser()).thenReturn(user);
        RouteCreateDto routeCreateDto = new RouteCreateDto("A","B",100);
        assertEquals(routeService.create(routeCreateDto),ResponseEntity.ok("Success"));
    }

    @Test
    void itShouldThrowConflictExceptionWhenDistanceIsInvalid(){
        UserRole userRole = roleRepo.save(new UserRole(Constant.USER));
        UserRole driverRole = roleRepo.save(new UserRole(Constant.DRIVER));

        User user = new User(
                "Mirkomil Ablayev",
                LocalDate.now().minusYears(19),
                "Samarqand Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(Arrays.asList(userRole, driverRole))
        );

        lenient().when(util.getCurrentUser()).thenReturn(user);
        RouteCreateDto routeCreateDto = new RouteCreateDto("A","B",-12);
        assertThrows(ConflictException.class, () -> routeService.create(routeCreateDto));
    }




}
