package com.sentura.beatsbyyou.service;

import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.dto.UserDto;
import com.sentura.beatsbyyou.repository.AuthRepository;
import com.sentura.beatsbyyou.entity.User;
import com.sentura.beatsbyyou.util.ObjectEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    public User findUserByEmail(String email) {
        return authRepository.findFirstByEmail(email);
    }

    public ResponseDto save(UserDto userDto) {

        try{
            User user;
            if (userDto.getId() != 0 || userDto.getEmail() != null) {
                user = findUserByEmail(userDto.getEmail());
                if (user != null) {
                    throw new Exception("user already exists!");
                } else {
                    userDto.setId(0);
                    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    userDto.setActive(true);
                    user = authRepository.saveAndFlush(new ObjectEntityConverter().userDtoToEntity(userDto));
                }
            } else {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userDto.setActive(true);
                user = authRepository.saveAndFlush(new ObjectEntityConverter().userDtoToEntity(userDto));
            }
            userDto.setPassword(null);
            userDto.setId(user.getId());
            return new ResponseDto("success",userDto);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto update(UserDto userDto) {

        try{
            User user;
            if (userDto.getId() != 0 && userDto.getEmail() != null) {
                user = findUserByEmail(userDto.getEmail());
                if (user != null && user.getId() == userDto.getId()) {
                    if (userDto.getPassword() != null) userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    authRepository.saveAndFlush(new ObjectEntityConverter().userDtoToEntity(userDto));
                }else{
                    throw new Exception("user not found!");
                }
            } else {
                throw new Exception("user not found!");
            }
            userDto.setPassword(null);
            return new ResponseDto("success",userDto);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    public ResponseDto getAll() {

        try{
            List<UserDto> users = new ArrayList<>();
            List<User> userList = authRepository.findAll();
            if (!userList.isEmpty()) {
                for(User user: userList) {
                    users.add(new ObjectEntityConverter().userEntityToDto(user));
                }
            }
            return new ResponseDto("success",users);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }
}
