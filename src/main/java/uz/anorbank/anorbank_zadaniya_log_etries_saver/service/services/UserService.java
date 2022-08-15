package uz.anorbank.anorbank_zadaniya_log_etries_saver.service.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.user.UserShowDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.File;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.UserRole;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.exceptions.ResourceNotFoundException;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.FileRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.RoleRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.repository.repositories.UserRepo;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.AbstractService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.BaseService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Constant;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService extends AbstractService<UserRepo> implements BaseService {
    private final FileRepo fileRepo;
    private final Util util;
    private final RoleRepo roleRepo;

    public UserService(UserRepo repository, Util util, FileRepo fileRepo, RoleRepo roleRepo) {
        super(repository);
        this.util = util;
        this.roleRepo = roleRepo;
        this.fileRepo = fileRepo;
    }

    public HttpEntity<?> saveProfilePhoto(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file != null) {


            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String[] split = originalFilename.split("\\.");
            String generatedName = UUID.randomUUID() + "." + split[split.length - 1];
            String filePath = Constant.filePaths + "/" + generatedName;
            // file generate qilindi
            File newFile = makeFile(file, filePath, generatedName);
            File save = fileRepo.save(newFile);
            Path path = Paths.get(filePath);
            try {
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            User currentUser = util.getCurrentUser();
            currentUser.setLogoFile(save);
            return ResponseEntity.status(HttpStatus.OK).body(save.getId());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Something went wrong");
    }

    private File makeFile(MultipartFile multipartFile, String filePath, String generatedName) {
        File file = new File();
        file.setFilePath(filePath);
        file.setGeneratedName(generatedName);
        file.setOriginalName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        return file;
    }


    public void showPictures(Long id, HttpServletResponse response) {
        Optional<File> fileOptional = fileRepo.findByIdAndIsDeleted(id, false);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            try {
                response.setHeader("Content-Disposition", file.getContentType());
                response.setContentType(file.getContentType());
                FileInputStream inputStream = new FileInputStream(file.getFilePath());
                FileCopyUtils.copy(inputStream, response.getOutputStream());
            } catch (IOException ignored) {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HttpEntity<?> addDriverRoleToUser() {
        User currentUser = util.getCurrentUser();
        Set<UserRole> userRoleSet = currentUser.getUserRoleSet();
        boolean isExist = false;
        for (UserRole userRole : userRoleSet) {
            if (userRole.getAuthority().equals(Constant.DRIVER)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            UserRole userRole = roleRepo.findByNameAndIsActive(Constant.DRIVER, true).orElseThrow(ResourceNotFoundException::new);
            userRoleSet.add(userRole);
            currentUser.setUserRoleSet(userRoleSet);
            repository.save(currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(Constant.DRIVER + " role is successfully added");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Constant.DRIVER + " role is already added");
        }
    }

    public HttpEntity<?> addUserRoleToDriver() {
        User currentUser = util.getCurrentUser();
        Set<UserRole> userRoleSet = currentUser.getUserRoleSet();
        boolean isExist = false;
        for (UserRole userRole : userRoleSet) {
            if (userRole.getAuthority().equals(Constant.USER)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            UserRole userRole = roleRepo.findByNameAndIsActive(Constant.USER, true).orElseThrow(ResourceNotFoundException::new);
            userRoleSet.add(userRole);
            currentUser.setUserRoleSet(userRoleSet);
            repository.save(currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(Constant.DRIVER + " role is successfully added");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Constant.DRIVER + " role is already added");
        }
    }

    public HttpEntity<?> getUserPersonalData() {
        User currentUser = util.getCurrentUser();
        UserShowDto userShowDto = new UserShowDto();
        userShowDto.setBirthPlace(currentUser.getBirthPlace());
        userShowDto.setFullName(currentUser.getFullName());
        userShowDto.setUsername(currentUser.getUsername());
        userShowDto.setPhoneNumber(currentUser.getPhoneNumber());
        userShowDto.setBirthDate(currentUser.getBirthDate());
        userShowDto.setStatus("ACTIVE");
        if (currentUser.getLogoFile() != null) {
            userShowDto.setLogoFileId(currentUser.getLogoFile().getId());
        }
        List<String> roles = new ArrayList<>();
        for (UserRole userRole : currentUser.getUserRoleSet()) {
            roles.add(userRole.getAuthority());
        }
        userShowDto.setRoles(roles);
        return ResponseEntity.status(HttpStatus.OK).body(userShowDto);
    }
}













