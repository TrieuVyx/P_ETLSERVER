package com.wilmar.p_server.controller;

import com.wilmar.p_server.exception.CustomNotFoundException;
import org.springframework.web.context.request.WebRequest;
import com.wilmar.p_server.service.RefreshTokenService;
import com.wilmar.p_server.entity.user.UserEntity;
import com.wilmar.p_server.exception.NotFoundId;
import org.springframework.web.bind.annotation.*;
import com.wilmar.p_server.service.UserService;
import org.springframework.http.ResponseEntity;
import java.io.FileNotFoundException;
import lombok.AllArgsConstructor;
import com.wilmar.p_server.dto.*;
import jakarta.validation.Valid;

@RequestMapping("/api/auth")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;
    // đăng kí
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO , WebRequest request){
        return userService.registerUser(userDTO,request);
    }
    // đăng nhập
    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO , WebRequest request) throws FileNotFoundException {
        return userService.loginUser(loginDTO,request);
    }

    // findUser by FullName And Email
    @GetMapping("getList")
    public ResponseEntity<?> getListFromFullNameAndEmailByBody(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            @RequestBody UserEntity userEntity,
            WebRequest request
    ){
        ResponUserDTO responUserDTO = userService.findFullNameAndEmail(page,size,userEntity,request);
        return ResponseEntity.ok(responUserDTO);
    }

    // lấy danh sách bằng params
    @GetMapping("getListByParams")
    public ResponseEntity<?> getListFromFullNameAndEmailByParams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            @RequestParam( required = false) String fullName,
            @RequestParam( required = false) String email,
            WebRequest request
    ){
        ResponUserDTO responUserDTO = userService.findFullNameAndEmailByParam(page,size,fullName,email,request);
        return ResponseEntity.ok(responUserDTO);
    }
    // lấy id
    @GetMapping("getUser/{id}")
    public ResponseEntity<ResultId> getUserById(
            @PathVariable Integer id
    ) throws NotFoundId {
        ResultId resultId = userService.findUserId(id);
        return ResponseEntity.ok(resultId);
    }

    // cạp nhật người dùng
    @PutMapping("update/{id}")
    public ResponseEntity<ResultId> UpdateUserById (
            @PathVariable Integer id
    )throws NotFoundId {
        ResultId resultId = userService.updateUserById(id);
        return ResponseEntity.ok(resultId);
    }
    @PostMapping("refreshToken")
    public ResponseEntity<TokenDTO> findByToken(
            @Valid
            @RequestBody TokenDTO tokenDTO1
    ){
        String refreshToken = tokenDTO1.getRefreshToken();
        if(refreshTokenService.verifyExpiration(refreshToken))
        {
            TokenDTO tokenDTO = userService.findByToken(refreshToken).getBody();
            return ResponseEntity.ok(tokenDTO);
        }
        throw new CustomNotFoundException();

    }


}
