package com.hari.booking_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

//    public BookingEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.objectMapper = objectMapper;
//    }

    public void sendBookingEvent(TicketBookedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(KafkaTopics.TICKET_BOOKED, message);
            System.out.println("Produced KafkaTopics message: " + message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize booking event", e);
        }
    }
}
