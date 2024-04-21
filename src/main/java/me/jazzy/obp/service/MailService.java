package me.jazzy.obp.service;

import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.ContactDto;
import me.jazzy.obp.dto.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MailService {

    private final EmailSenderService emailSenderService;

    public ResponseBody contact(ContactDto contactDto) {
        String message = "Dear " + contactDto.getName() + ",\n" + contactDto.getMessage();
        emailSenderService.sendEmailFrom(contactDto.getEmail(), contactDto.getSubject(), message);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Contact message sent successfully",
                LocalDateTime.now()
        );
    }
}