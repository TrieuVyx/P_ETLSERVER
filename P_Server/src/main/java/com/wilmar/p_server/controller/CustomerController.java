package com.wilmar.p_server.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.wilmar.p_server.service.UserService;
import com.wilmar.p_server.dto.ResponUserDTO;
import lombok.AllArgsConstructor;


@RequestMapping("/api/user")
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    private final UserService userService;
    // lấy danh sách user
    @GetMapping("getUserList")
    public ResponseEntity<?> getUserList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        ResponUserDTO responUserDTO = userService.getUserList(page,size);
        return ResponseEntity.ok(responUserDTO);
    }
}
