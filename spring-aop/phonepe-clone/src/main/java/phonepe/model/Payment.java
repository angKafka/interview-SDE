package phonepe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Payment {
    private String paymentId;
    private String customerId;
    private String customerName;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
    private String paymentDate;

    public Payment() {
    }
}
