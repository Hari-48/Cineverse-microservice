package com.hari.notification_service.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.Base64;

@Service
@Slf4j
public class BookingTicket {

    @Autowired
    EmailService emailService;

    public static final String TICKET_BOOKED = "ticket-booked";

    @KafkaListener(topics = TICKET_BOOKED, groupId = "my-group")
    public void getLocation(String location) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Enables LocalDateTime parsing
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            TicketBookedEvent event = objectMapper.readValue(location, TicketBookedEvent.class);

            log.info("✅ Booking ID: {}", event.getBookingId());
            log.info("🎟️ Seats: {}", String.join(", ", event.getSeatNumbers()));
            log.info("🕒 Time: {}", event.getBookingTime());

            emailService.sendBookingEmailWithQr(event, "maagi0225@gmail.com");

        } catch (JsonProcessingException e) {
            log.error("❌ Failed to parse booking message: {}", location, e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
