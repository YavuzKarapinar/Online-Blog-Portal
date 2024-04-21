package me.jazzy.obp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

import me.jazzy.obp.dto.ContactDto;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mails")
@SecurityRequirement(name = "obp")
@AllArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/contact")
    public ResponseEntity<ResponseBody> contact(@RequestBody ContactDto contactDto) {
        return new ResponseEntity<>(
                mailService.contact(contactDto),
                HttpStatus.OK
        );
    }

}