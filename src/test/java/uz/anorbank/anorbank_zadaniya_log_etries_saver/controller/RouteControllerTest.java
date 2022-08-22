package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouetUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.route.RouteCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Route;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RouteRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.UserRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.auth.AuthService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
public class RouteControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RouteRepo routeRepo;

    private RouteCreateDto routeCreateDto = null;
    private RouetUpdateDto rouetUpdateDto = null;
    private Route route = null;

    @BeforeEach
    void setUp() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
        UserRole userRole2 = new UserRole(Constant.ADMIN);
        userRole2.setId(3L);
        User user = new User(
                "Mirkomil Ablayev",
                LocalDate.now().minusYears(19),
                "Samarqand Ishtixon",
                "+998945331738",
                null,
                false,
                "mirkomil_ablayev",
                "1212",
                new HashSet<>(Arrays.asList(userRole, userRole1, userRole2))
        );
        user = userRepo.save(user);

        UserDetails userDetails = authService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        route = routeRepo.save(new Route("Samarkand", "Toshkent", 121, false, user));
        rouetUpdateDto = new RouetUpdateDto(route.getId(), "Bukhara", "QAS", 121);
        routeCreateDto = new RouteCreateDto("Samarqand", "Toshkent", 100);
    }

    @Test
    void canAddRouteSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/route/createRoute")
                        .content(objectMapper.writeValueAsString(routeCreateDto))
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(String.class)))
                .andExpect(jsonPath("$", is("Success")));
    }

    @Test
    void canGetTheRouteById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/route/getFullInfoForDto/" + route.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8));
    }


    @Test
    void canUpdateTheRoute() throws Exception {
        mockMvc.perform(put("/api/route/updateRoute")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(rouetUpdateDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/api/route/deleteById/{id}", route.getId()))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void canGetMyRoutes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/route/getMyRoutes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}
