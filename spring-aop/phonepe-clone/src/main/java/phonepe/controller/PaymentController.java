package phonepe.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phonepe.model.Payment;
import phonepe.service.PaymentService;

@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/invoke")
    public ResponseEntity<String> paymentProceed(@RequestBody Payment payment) {
        try {
            if (paymentService.invokePayment(payment)) {
                return ResponseEntity.ok("✅ Payment successful: " + payment.getPaymentId());
            } else {
                return ResponseEntity.badRequest().body("⚠️ Payment already exists: " + payment.getPaymentId());
            }
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("❌ Error processing payment: " + ex.getMessage());
        }
    }
}
