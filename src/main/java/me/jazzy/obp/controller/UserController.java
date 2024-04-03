package me.jazzy.obp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.dto.UserDto;
import me.jazzy.obp.model.User;
import me.jazzy.obp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
@AllArgsConstructor
@RestController
@SecurityRequirement(name = "obp")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseBody> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }
}