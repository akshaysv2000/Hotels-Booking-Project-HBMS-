package com.example.hbmSystem.dto;

public class HotelBookingListDTO {
    private String bookingDate;
    private String roomType;
    private String numberOfRooms;
    private String check_in_date;
    private String check_out_date;
    private String amount;
    private String paymentStatus;
    private String customerName;
    private String customerPhone;

    public HotelBookingListDTO() {
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Override
    public String toString() {
        return "HotelBookingListDTO{" +
                "bookingDate='" + bookingDate + '\'' +
                ", roomType='" + roomType + '\'' +
                ", numberOfRooms='" + numberOfRooms + '\'' +
                ", check_in_date='" + check_in_date + '\'' +
                ", check_out_date='" + check_out_date + '\'' +
                ", amount='" + amount + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", customerName='" + customerName + '\'' +
                ", CustomerPhone='" + customerPhone + '\'' +
                '}';
    }
}
