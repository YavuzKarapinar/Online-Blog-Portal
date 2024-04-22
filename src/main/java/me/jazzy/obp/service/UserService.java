package me.jazzy.obp.service;

import lombok.AllArgsConstructor;
import me.jazzy.obp.config.exception.notfound.UserNotFoundException;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.dto.UserDto;
import me.jazzy.obp.model.User;
import me.jazzy.obp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("There is no such username."));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("There is no such username or user"));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is no such user"));
    }

    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

    }

    public ResponseBody updateUser(UserDto userDto) {

        User user = getById(userDto.getId());

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        saveUser(user);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "User updated.",
                LocalDateTime.now()
        );
    }
}