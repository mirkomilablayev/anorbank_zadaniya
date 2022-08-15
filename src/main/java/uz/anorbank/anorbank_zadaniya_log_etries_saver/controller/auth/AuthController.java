package uz.anorbank.anorbank_zadaniya_log_etries_saver.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.user.LoginDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.dto.user.RegisterDto;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.entity.User;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.auth.AuthService;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.service.jwt.JwtProvider;
import uz.anorbank.anorbank_zadaniya_log_etries_saver.tools.Util;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final Util util;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }


    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto) {
        User user = null;
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            user = (User) authenticate.getPrincipal();
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("", HttpStatus.BAD_GATEWAY);
        }
        String token = jwtProvider.generateToken(user.getUsername(), user);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
