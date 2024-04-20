package com.wilmar.p_server.service;

import com.wilmar.p_server.entity.token.RefreshToken;
import com.wilmar.p_server.exception.CustomNotFoundException;
import com.wilmar.p_server.exception.NotFoundId;
import com.wilmar.p_server.exception.TokenRefreshException;
import com.wilmar.p_server.repository.RefreshTokenRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import com.wilmar.p_server.repository.UserRepository;
import com.wilmar.p_server.entity.user.UserEntity;
import com.wilmar.p_server.security.JwtUtilities;
import org.springframework.stereotype.Service;
import com.wilmar.p_server.dto.HttpMessage;
import com.wilmar.p_server.entity.role.*;

import java.util.stream.Collectors;
import org.springframework.http.*;
import com.wilmar.p_server.dto.*;
import java.util.*;
import lombok.*;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtilities jwtUtilities;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO, WebRequest request) {
        try{

            if(userRepository.existsByEmail(userDTO.getEmail())){
                return  new ResponseEntity<>("email is already exist !", HttpStatus.SEE_OTHER);
            }
            else {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashedPassword = encoder.encode(userDTO.getPassWord());
                UserEntity user = new UserEntity();
                RoleEntity role = new RoleEntity();
                user.setUserName(userDTO.getUserName());
                user.setEmail(userDTO.getEmail());
                user.setFullName(userDTO.getFullName());
                user.setPassWord(hashedPassword);
                role.setRoleNameEntity(RoleNameEntity.USER);
                user.setRoles(Collections.singletonList(role));
                userRepository.save(user);
                return new ResponseEntity<>(new ResultDTO(userDTO.getUserName(), userDTO.getEmail(),userDTO.getFullName()), HttpStatus.OK);
            }
        }
        catch(Exception e){
            HttpMessage httpMessage = new HttpMessage(
                    HttpStatus.NOT_FOUND,
                    new Date(),
                    e.getMessage(),
                    request.getDescription(false)
            );
            return new ResponseEntity<>(httpMessage, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO, WebRequest request) {
        try {
            UserEntity userEntity = userRepository.findByEmail(loginDTO.getEmail());

            if(userEntity!= null && BCrypt.checkpw(loginDTO.getPassWord(), userEntity.getPassWord())){
                List<String> rolesNames = userEntity.getRoles().stream()
                        .map(r -> r.getRoleNameEntity().toString())
                        .collect(Collectors.toList());
                String token = jwtUtilities.generateToken(userEntity.getEmail(),rolesNames);
                RefreshToken refreshToken1 = refreshTokenService.creaetRefreshToken(userEntity.getId());
                return new ResponseEntity<>(new TokenDTO(token , "Bearer ",refreshToken1.getToken()),HttpStatus.OK);
            }
            throw new CustomNotFoundException();
        } catch(Exception e){
            throw new CustomNotFoundException();
        }
    }



    public ResponUserDTO findFullNameAndEmail(int page, int size, @RequestBody UserEntity userEntity, WebRequest request) {
        try{
            Pageable paging = PageRequest.of(page,size);
            Page<UserEntity> pagedResult = userRepository.getUserBy(userEntity.getFullName() == null ? "" : userEntity.getFullName(), userEntity.getEmail() == null ? "" : userEntity.getEmail() ,paging);
            return new  ResponUserDTO(pagedResult.getTotalPages(),pagedResult.getTotalElements(), pagedResult.getContent());
        }
        catch(Exception e){
            throw new CustomNotFoundException();
        }

    }

    public ResponUserDTO findFullNameAndEmailByParam(int page, int size, String fullName,String email,WebRequest request) {
        try{
            Pageable paging = PageRequest.of(page,size);
            Page<UserEntity> pagedResult = userRepository.getUserBy(fullName == null ? "" : fullName, email == null ? "" : email ,paging);
            return new  ResponUserDTO(pagedResult.getTotalPages(),pagedResult.getTotalElements(), pagedResult.getContent());
        }
        catch(Exception e){
            throw new CustomNotFoundException();
        }
    }
    public ResultId findUserId(@PathVariable Integer id) throws NotFoundId {
        try{
            Optional<UserEntity> resultId = userRepository.findById(id);
            if(resultId.isEmpty()){
                throw new NotFoundId(id);
            }
            return new ResultId(resultId.get().getFullName(), resultId.get().getUserName(), resultId.get().getEmail(), resultId.get().getId());
        }
        catch(Exception e){
            throw new NotFoundId(id);
        }
    }
    // cap nhat user
    public ResultId updateUserById(@PathVariable Integer id) throws NotFoundId {
        try{
            Optional<UserEntity> resultId = userRepository.findById(id);
            if(resultId.isEmpty()){
                throw new NotFoundId(id);
            }

            return new ResultId( resultId.get().getUserName(), resultId.get().getEmail(),resultId.get().getFullName(), resultId.get().getId());
        }
        catch(Exception e){
            throw new NotFoundId(id);
        }
    }

    public ResponUserDTO getUserList(int page, int size) {
        try{
            Pageable paging = PageRequest.of(page,size);
            Page<UserEntity> pagedResult = userRepository.findAll(paging);
            return new  ResponUserDTO(pagedResult.getTotalPages(),pagedResult.getTotalElements(), pagedResult.getContent());
        }
        catch(Exception e){
            throw new CustomNotFoundException();
        }

    }


    public ResponseEntity<TokenDTO> findByToken(String refreshToken) throws TokenRefreshException {
        try {
            Optional<RefreshToken> refreshTokens = refreshTokenRepository.findByToken(refreshToken);
            if (refreshTokens.isPresent()) {
                RefreshToken refreshTokenEntity = refreshTokens.get();

                List<String> rolesNames = refreshTokenEntity.getUserEntity().getRoles().stream()
                        .map(r -> r.getRoleNameEntity().toString())
                        .toList();
                String token = jwtUtilities.generateToken(refreshTokenEntity.getUserEntity().getEmail(), rolesNames);
                return new ResponseEntity<>(new TokenDTO(token , "Bearer ",refreshToken),HttpStatus.OK);
            }
                throw new TokenRefreshException("Refresh token not found");
        } catch (Exception e) {
            throw new CustomNotFoundException();
        }
    }
}
