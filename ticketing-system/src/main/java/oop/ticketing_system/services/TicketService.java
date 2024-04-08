package oop.ticketing_system.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> updateTicketStatusByEventId(int eventId, String newStatus) {
        List<Ticket> tickets = ticketRepository.findByEventIdAndStatus(eventId, "Active");
        for (Ticket ticket : tickets) {
            ticket.setStatus(newStatus);
        }
        return ticketRepository.saveAll(tickets);
    }

    public int getTicketsSoldByEventId(int eventId) {
        return ticketRepository.countByEventIdAndStatus(eventId, "Active");
    }

    public int getTicketsRefundedByEventId(int eventId) {
        return ticketRepository.countByEventIdAndStatus(eventId, "Refunded");
    }

    public int getTicketsAttendedByEventId(int eventId) {
        return ticketRepository.countByEventIdAndStatus(eventId, "Used");
    }

    public int getTicketsCancelledByEventId(int eventId) {
        return ticketRepository.countByEventIdAndStatus(eventId, "Cancelled");
    }

    public BufferedImage generateTicketBarcode(int ticketId) {
        String serial = encryptTicketID(ticketId);
        return generateQRCodeImage(serial);
    }

    public Ticket verifyTicketSerial(String serial) {
        int ticketId = decryptTicketId(serial);
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        Ticket ticket = optionalTicket.orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid Ticket");
        }
        return ticket;
    }

    public BufferedImage generateQRCodeImage(String barcodeText) {
        try {
            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            System.out.println("Error generating QR: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // Encrypting Ticket Serial
    private final String AES_KEY = "labtest2tootough"; // 16-character secret key

    public String encryptTicketID(int id) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(String.valueOf(id).getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.out.println("Error encrypting ticketId: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public int decryptTicketId(String serial) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(serial));
            return Integer.parseInt(new String(decryptedBytes));
        } catch (Exception e) {
            System.out.println("Error decrypting ticketId: " + e.getMessage());
            throw new IllegalArgumentException("Invalid Ticket");
        }
    }
}
