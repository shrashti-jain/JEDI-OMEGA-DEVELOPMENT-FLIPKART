package com.flipfit.dao;

import com.flipfit.bean.Payment;

public interface PaymentDAO {

    boolean makePayment(Payment payment);

    Payment getPaymentByBookingId(String bookingId);
}
