package phonepe.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.stereotype.Component;
import phonepe.model.Payment;
import util.StorageLocation;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class LocalStorage {
    private Payment payment;
    public List<Payment> paymentHistory;
    private final ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    private final File userFile;

    public LocalStorage() {
        this.userFile = new File(StorageLocation.STORAGE_LOCATION);
        loadToStorage();
    }

    public LocalStorage(Payment payment) {
        this.payment = payment;
        this.userFile = new File(StorageLocation.STORAGE_LOCATION);
        loadToStorage();
    }

    private void loadToStorage(){
      try{
          if(userFile.exists()){
              paymentHistory = mapper.readValue(userFile, new TypeReference<List<Payment>>(){});
          }
      }catch (IOException e){
          e.printStackTrace();
      }
    }

    public void saveToStorage(){
        try{
            mapper.writeValue(userFile, paymentHistory);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
