package phonepe.service;

import org.springframework.stereotype.Service;
import phonepe.exception.PaymentExceptions;
import phonepe.model.Payment;
import phonepe.repository.LocalStorage;

import java.util.Optional;

@Service
public class PaymentService {
    private final LocalStorage localStorage;

    public PaymentService(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }

    public Boolean invokePayment(Payment payment) {
        Optional<Payment> userCheck = localStorage.paymentHistory.stream()
                .filter(pay -> pay.getPaymentId().equals(payment.getPaymentId()))
                .findFirst();

        if (!userCheck.isPresent()) {
            localStorage.paymentHistory.add(payment);
            localStorage.saveToStorage();
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE; // already exists
        }
    }
}
