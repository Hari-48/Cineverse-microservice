package com.hari.booking_service.service.Imple;

import com.google.zxing.WriterException;
import com.hari.booking_service.QRCodeGenerator.QRCodeGen;
import com.hari.booking_service.entity.Booking;
import com.hari.booking_service.entity.Seat;
import com.hari.booking_service.kafka.BookingProducerService;
import com.hari.booking_service.kafka.TicketBookedEvent;
import com.hari.booking_service.model.BookingRequest;
import com.hari.booking_service.model.Status;
import com.hari.booking_service.repo.BookingRepo;
import com.hari.booking_service.repo.SeatRepo;
import com.hari.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final SeatRepo seatRepo;
    private final BookingProducerService bookingEventProducer;


    @Override
    public ResponseEntity<?> bookTicket(BookingRequest request) {

        Long availability = seatRepo.validate(request.getShowTimeId(), request.getSeatNumbers());
        if (availability != 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            Booking booking = new Booking();
            booking.setBookingTime(LocalTime.now());
            booking.setStatus(Status.valueOf("BOOKED"));
            booking.setSeatNumbers(request.getSeatNumbers());
            booking.setMovieId(request.getMovieId());
            booking.setUserId(request.getUserId());
            booking.setShowTimeId(request.getShowTimeId());
            Booking response = bookingRepo.save(booking);

            Boolean QRCode = generateQRCode(response);
            updateSeat(request);

//            Booking saveQr = bookingRepo.findById(response.getId()).get();
//            saveQr.setQrCodeUrl("http://localhost:8002/bookings/qr/"+saveQr.getId());
//            Booking finalBooking = bookingRepo.saveAndFlush(saveQr);
//            return new ResponseEntity<>(finalBooking,HttpStatus.OK);


            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<?> cancelTicket(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).get();
        bookingRepo.cancelTicket(bookingId);
        seatRepo.updateSeatAvailability(booking.getSeatNumbers(), booking.getShowTimeId());
        return new ResponseEntity<>("BOOKING CANCELLED SUCCESSFULLY", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateTicket(Long bookingId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getBookingOfUser(Long userId) {
        return new ResponseEntity<>(bookingRepo.findByUserId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getBookingById(Long id) {
        return null;
    }

//    public Boolean generateQRCode(Booking response) {
//        String filePath = "src/main/resources/qr/booking-" + response.getId() + ".png";
//        try {
//            QRCodeGen.generateQRCodeImage(response, 250, 250, filePath);
//            sendBookingMessage(response);
//            return true;
//        } catch (WriterException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Boolean generateQRCode(Booking response){
        try {
            byte[] qrBytes = QRCodeGen.getQRCodeAsBytes(response.toString(), 250, 250);
            String base64Qr = Base64.getEncoder().encodeToString(qrBytes);

            // Include base64Qr in a custom response DTO

//            BookingResponse bookingResponse = new BookingResponse(response, base64Qr);
//            return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
//            https://codebeautify.org/base64-to-image-converter

            response.setQrCodeUrl(base64Qr);
            bookingRepo.saveAndFlush(response);

//            sendBookingMessage(response);

            return true;

        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateSeat(BookingRequest request) {
        Seat seat = new Seat();
        seat.setIsBooked(true);
        seat.setSeatNumber(request.getSeatNumbers());
        seat.setShowTimeId(request.getShowTimeId());
        seatRepo.save(seat);
    }

    public void sendBookingMessage(Booking booking) {
        bookingEventProducer.sendBookingEvent(new TicketBookedEvent(
                booking.getId(),
                booking.getUserId(),
                booking.getShowTimeId(),
                Arrays.asList(booking.getSeatNumbers().split(",")),
                booking.getBookingTime(), booking.getQrCodeUrl()
        ));


        //docker exec -it apache-kafka-kafka-1 bash
//        kafka-console-consumer \
//  --bootstrap-server localhost:9092 \
//  --topic ticket-booked \
//  --from-beginning
    }


}
