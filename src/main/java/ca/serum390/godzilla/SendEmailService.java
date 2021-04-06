package ca.serum390.godzilla;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class SendEmailService {

    private java.util.logging.Logger log;

    @Autowired
    private JavaMailSender javaMailSender;

    public SendEmailService() {
        log = Logger.getLogger(SendEmailService.class.getName());
        try {
            FileHandler file = new FileHandler("email_service.log");
            SimpleFormatter formatter = new SimpleFormatter();
            file.setFormatter(formatter);
            log.addHandler(file);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public Mono<Void> sendEmail(String to, String topic, String body) {
        return Mono.fromCallable(() -> {
            log.info("Sending Email to: " + to);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("ERP.Godzilla@gmail.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(topic);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
            return null;
        });
    }
}
