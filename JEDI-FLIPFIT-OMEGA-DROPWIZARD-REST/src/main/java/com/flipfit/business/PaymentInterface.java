package com.flipfit.business;

import com.flipfit.bean.Payment;

public interface PaymentInterface {

    boolean makePayment(Payment payment);

    Payment viewPaymentByBookingId(String bookingId);
}
