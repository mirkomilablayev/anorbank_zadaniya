package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.AbstractController;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController<UserService> {
    public UserController(UserService service) {
        super(service);
    }

    @PutMapping("/addDriverRoleToUser")
    public HttpEntity<?> addDriverRoleToUser() {
        return service.addDriverRoleToUser();
    }

    @PutMapping("/addUserRoleToDriver")
    public HttpEntity<?> addUserRoleToDriver() {
        return service.addUserRoleToDriver();
    }

    @PostMapping("/saveUserProfilePhoto")
    public HttpEntity<?> saveProfilePhoto(MultipartHttpServletRequest request){
        return service.saveProfilePhoto(request);
    }

    @GetMapping("/showProfilePhoto/{photoId}")
    public void showProfilnullePicture(@PathVariable Long photoId, HttpServletResponse response){
        service.showPictures(photoId, response);
    }

    @GetMapping("/getUserPersonalData")
    public HttpEntity<?> getUserData(){
        return service.getUserPersonalData();
    }

}