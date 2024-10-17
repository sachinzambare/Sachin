package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private AppUserRepository appUserRepository;
    private UserService userService;

    public UserController(AppUserRepository appUserRepository, UserService userService) {
        this.appUserRepository = appUserRepository;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(

            @RequestBody AppUser user
    ){
        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());
        if (opUsername.isPresent()){
            return new ResponseEntity<>("Username Already taken", HttpStatus.INTERNAL_SERVER_ERROR);


        }  Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if (opEmail.isPresent()){
            return new ResponseEntity<>("Email Already taken", HttpStatus.INTERNAL_SERVER_ERROR);


        }
        String encryptedpassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(encryptedpassword);
        AppUser savedUser = appUserRepository.save(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }
    @GetMapping("/message")
    public String getMessage(){
        return "hello";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
         @RequestBody   LoginDto dto
    ){

        boolean status = userService.verifyLogin(dto);
        if(status) {
            return new ResponseEntity<>("user logged in",HttpStatus.OK);
        }else {
            return  new ResponseEntity<>("invalid username/password",HttpStatus.FORBIDDEN);
        }
    }
}
