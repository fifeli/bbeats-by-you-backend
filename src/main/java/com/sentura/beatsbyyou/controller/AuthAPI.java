package com.sentura.beatsbyyou.controller;

import com.sentura.beatsbyyou.configuration.JwtAuthenticationController;
import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.configuration.JwtRequest;
import com.sentura.beatsbyyou.configuration.JwtResponse;
import com.sentura.beatsbyyou.dto.AuthDto;
import com.sentura.beatsbyyou.dto.UserDto;
import com.sentura.beatsbyyou.entity.User;
import com.sentura.beatsbyyou.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthAPI {

    @Autowired
    private JwtAuthenticationController jwtAuthenticationController;

    @Autowired
    private AuthService authService;

    @PostMapping("/authenticate")
    public ResponseDto authenticate(@RequestBody AuthDto authDto) {

        try {
            JwtRequest jwtRequest = new JwtRequest();
            jwtRequest.setUsername(authDto.getEmail());
            jwtRequest.setPassword(authDto.getPassword());

            JwtResponse jwtResponse = jwtAuthenticationController.generateAuthenticationToken(jwtRequest);

            return new ResponseDto(null,jwtResponse.getToken());

        }catch (Exception e) {
            return new ResponseDto(e.getMessage(),"500",null);
        }
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody UserDto userDto) {

        return authService.save(userDto);
    }

    @PostMapping("/update")
    public ResponseDto update(@RequestBody UserDto userDto) {

        return authService.update(userDto);
    }

    @GetMapping("/getAll")
    public ResponseDto getAll() {

        return authService.getAll();
    }

}
