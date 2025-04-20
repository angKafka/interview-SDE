package phonepe.audit;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PaymentHistoryAuditing {

    // Log after successful payment processing
    @AfterReturning(
            pointcut = "execution(public Boolean phonepe.service.PaymentService.invokePayment(..))",
            returning = "result"
    )
    public void logPaymentResult(Boolean result) {
        if (Boolean.FALSE.equals(result)) {
            System.out.println("Logged[AOP]Payment already exists. Skipping addition.");
        } else {
            System.out.println("Logged[AOP] Payment added successfully.");
        }
    }

    // Log if the payment fails due to a technical error
    @AfterThrowing(
            pointcut = "execution(public Boolean phonepe.service.PaymentService.invokePayment(..))",
            throwing = "ex"
    )
    public void logPaymentFailure(Exception ex) {
        System.err.println("Logged[AOP] Payment failed due to: " + ex.getMessage());
    }
}