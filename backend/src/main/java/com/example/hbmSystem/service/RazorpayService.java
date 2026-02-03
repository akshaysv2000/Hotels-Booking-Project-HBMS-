package com.example.hbmSystem.service;

import com.example.hbmSystem.exception.ResourceNotFoundException;
import com.example.hbmSystem.models.Booking;
import com.example.hbmSystem.models.Payment;
import com.example.hbmSystem.repository.BookingRepository;
import com.example.hbmSystem.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    private static final String RAZORPAY_KEY_ID = "rzp_test_R85gcttS8hfK9M";
    private static final String RAZORPAY_KEY_SECRET = "e7nVEtx3iVWN8H73Gej2kJm9";

    public String createRazorpayOrder(BigDecimal amount) throws Exception {
        RazorpayClient client = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue()); // amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);
        Order order = client.Orders.create(orderRequest);
        return order.get("id");
    }

    public void createPaymentForBooking(Booking booking, BigDecimal amount, String razorpayOrderId) {
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setPaymentMethod("Razorpay");
        payment.setPaymentStatus("ORDER_CREATED");
        payment.setRazorpayOrderId(razorpayOrderId);

        booking.setPayment(payment);
        bookingRepository.save(booking);
    }

    public boolean verifyPaymentSignature(Map<String, String> paymentDetails) throws Exception {
        String orderId = paymentDetails.get("razorpayOrderId");
        String paymentId = paymentDetails.get("razorpayPaymentId");
        String signature = paymentDetails.get("razorpaySignature");

        System.out.println("Received signature: " + signature);
        System.out.println("Order ID: " + orderId);
        System.out.println("Payment ID: " + paymentId);

        String payload = orderId + "|" + paymentId;
        String generatedSignature = hmacSha256(payload, RAZORPAY_KEY_SECRET);

        System.out.println("Generated signature: " + generatedSignature);

        return generatedSignature.equals(signature);
    }

    private String hmacSha256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(data.getBytes());


        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public void updatePaymentStatus(Map<String, String> paymentDetails) {
        String razorpayPaymentId = paymentDetails.get("razorpayPaymentId");
        String razorpayOrderId = paymentDetails.get("razorpayOrderId");

        Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment record not found"));

        payment.setRazorpayPaymentId(razorpayPaymentId);
        payment.setPaymentStatus("PAID");
        payment.setPaymentMethod("Razorpay");
        paymentRepository.save(payment);

        Booking booking = payment.getBooking();
        booking.setStatus(Booking.Status.Booked);
        bookingRepository.save(booking);
    }



}
