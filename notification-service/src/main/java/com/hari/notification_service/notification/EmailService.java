package com.hari.notification_service.notification;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.Base64;

@Component
@Slf4j
@RequiredArgsConstructor

public class EmailService {

    private final  JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    public File saveQrCodeAsImage(String base64Qr, Long bookingId) throws IOException {
        if (base64Qr == null || base64Qr.isEmpty()) {
            throw new IllegalArgumentException("QR base64 string is empty or null.");
        }
        byte[] imageBytes = Base64.getDecoder().decode(base64Qr);
        File qrFile = new File("qr-booking-" + bookingId + ".png");
        try (OutputStream os = new FileOutputStream(qrFile)) {
            os.write(imageBytes);
        }
        log.info("✅ QR Code image saved at: {}", qrFile.getAbsolutePath());
        return qrFile;
    }


    public void sendBookingEmailWithQr(TicketBookedEvent event, String toEmail) {
        File qrFile = null;
        try {

            qrFile = saveQrCodeAsImage(event.getQr(), event.getBookingId());
            if (!qrFile.exists()) {
                throw new FileNotFoundException("QR file not found: " + qrFile.getAbsolutePath());
            }
            Context context = new Context();
            context.setVariable("bookingId", event.getBookingId());
            context.setVariable("showTimeId", event.getShowTimeId());
            context.setVariable("seats", String.join(", ", event.getSeatNumbers()));
            context.setVariable("bookingTime", event.getBookingTime());

            String html = templateEngine.process("booking-confirmation", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("🎟️ Booking Confirmation with QR Code");

            String textContent = "Your booking is confirmed!\n"
                    + "Booking ID: " + event.getBookingId() + "\n"
                    + "ShowTime ID: " + event.getShowTimeId() + "\n"
                    + "Seats: " + String.join(", ", event.getSeatNumbers()) + "\n"
                    + "Time: " + event.getBookingTime();
            helper.setText(textContent, false);

//            helper.setText(html, true);

            FileSystemResource file = new FileSystemResource(qrFile);
            helper.addAttachment("qr-code.png", file);
            mailSender.send(message);
            log.info("📧 Email sent successfully to {}", toEmail);

        } catch (Exception e) {
            log.error("❌ Failed to send email: {}", e.getMessage(), e);
        } finally {
            if (qrFile != null && qrFile.exists()) {
                boolean deleted = qrFile.delete();
                if (deleted) {
                    log.info("🧹 QR file deleted: {}", qrFile.getAbsolutePath());
                } else {
                    log.warn("⚠️ Failed to delete QR file: {}", qrFile.getAbsolutePath());
                }
            }
        }
    }

}
