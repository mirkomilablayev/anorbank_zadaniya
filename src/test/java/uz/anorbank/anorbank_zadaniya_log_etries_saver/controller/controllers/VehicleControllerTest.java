package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

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
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleCreateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.vehicle.VehicleUpdateDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.Vehicle;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.UserRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.VehicleRepo;
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
class VehicleControllerTest {

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
    private VehicleRepo vehicleRepo;

    private VehicleCreateDto vehicleCreateDto = null;
    private VehicleUpdateDto vehicleUpdateDto = null;
    private Vehicle vehicle = null;

    @BeforeEach
    void setUp() {
        UserRole userRole = new UserRole(Constant.USER);
        userRole.setId(1L);
        UserRole userRole1 = new UserRole(Constant.DRIVER);
        userRole1.setId(2L);
        UserRole userRole2 = new UserRole(Constant.ADMIN);
        userRole2.setId(2L);
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

        vehicleCreateDto = new VehicleCreateDto("12", "Blue", "A12", 12000,"nexia");
        vehicle = vehicleRepo.save(new Vehicle("U", user,"U","U","U",120000,150000,false));
        vehicleUpdateDto = new VehicleUpdateDto(vehicle.getId(),"UY","12","Malibu");
    }



    @Test
    void canCreateNewVehicle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vehicle/createVehicle")
                        .content(objectMapper.writeValueAsString(vehicleCreateDto))
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(String.class)))
                .andExpect(jsonPath("$", is("Success")));
    }

    @Test
    void canUpdateTheVehicle() throws Exception {
        mockMvc.perform(put("/api/vehicle/updateVehicle")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(vehicleUpdateDto)))
                .andExpect(status().isOk());
    }

    @Test
    void canDeleteVehicleVyId() throws Exception {
        mockMvc.perform(delete("/api/vehicle/deleteMyVehicle/{id}", vehicle.getId()))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void canGetMyVehicle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicle/getMyVehiclesFullInfo/{id}",vehicle.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8));
    }

}