package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.AbstractController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController<UserService> {
    public UserController(UserService service) {
        super(service);
    }

    @PutMapping("/addDriverRoleToUser")
    public HttpEntity<?> addDriverRoleToUser() {
        return null;
    }

    @PutMapping("/addUserRoleToDriver")
    public HttpEntity<?> addUserRoleToDriver() {
        return null;
    }

    @PostMapping("/saveUserProfilePhoto")
    public HttpEntity<?> saveProfilePhoto(){
        return null;
    }

    @GetMapping("/showProfilePhoto/{photoId}")
    public void showProfilePicture(){

    }

    @GetMapping("/getUserPersonalData")
    public HttpEntity<?> getUserData(){
        return null;
    }

}