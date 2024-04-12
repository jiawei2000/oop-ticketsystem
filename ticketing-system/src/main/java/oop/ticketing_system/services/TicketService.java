package oop.ticketing_system.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import oop.ticketing_system.models.Ticket;
import oop.ticketing_system.models.Customer;
import oop.ticketing_system.models.Event;
import oop.ticketing_system.repository.TicketRepository;
import oop.ticketing_system.repository.CustomerRepository;
import oop.ticketing_system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CustomerRepository customerRepository;


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
        System.out.println("Serial: " + serial);
        return generateQRCodeImage(serial);
    }

    public Map<String, Object> verifyTicketSerial(String serial, int eventId) {
        int ticketId = decryptTicketId(serial);
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        
        Ticket ticket = optionalTicket.orElse(null);

        if (ticket == null) {
            throw new IllegalArgumentException("Invalid Ticket: Serial code does not exist");
        }
        if (ticket.getEventId() != eventId){
            System.out.println("ticketEventId: " + ticket.getEventId());
            throw new IllegalArgumentException("Invalid Ticket: Ticket does not match eventId");
        }

        Map<String, Object> retMap = new HashMap<>();
        Event event = eventRepository.getReferenceById(ticket.getEventId());
        Optional<Customer> optionalCustomer = customerRepository.findById(ticket.getUserId());
        Customer customer = optionalCustomer.orElse(null);
        String username = "";
        if(customer == null){
            username = "Offline Sale";
        }else{
            username = customer.getUserName();
        }
        //ticketId, eventName, Username, type, & status 
        retMap.put("ticketId", ticket.getTicketId());
        retMap.put("eventname", event.getEventName());
        retMap.put("username", username);
        retMap.put("type", ticket.getType());
        retMap.put("status", ticket.getStatus());
        return retMap;
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

    public Ticket updateTicketStatusToUsed(int ticketId){
        Ticket ticket = ticketRepository.getReferenceById(ticketId);
        ticket.setStatus("Used");
        return ticketRepository.save(ticket); 
    }
}
