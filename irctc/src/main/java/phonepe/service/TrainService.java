package phonepe.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import enteties.Train;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {
    private static final String TRAINS_PATH = "irctc/src/main/java/localDB/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(SerializationFeature.INDENT_OUTPUT);
    private Train train;
    private List<Train> trains;

    public TrainService() throws IOException {
        loadTrainListFromFile();
    }

    public TrainService(Train train) throws IOException {
        this.train = train;
        loadTrainListFromFile();
    }


    private void loadTrainListFromFile() throws IOException {
        trains = objectMapper.readValue(new File(TRAINS_PATH), new TypeReference<List<Train>>() {});
    }


    //SearchTrain source, destination
    public List<Train> searchTrains(String source, String destination) {
        return trains.stream().filter(train1 -> validateTrain(train1, source, destination)).collect(Collectors.toList());
    }

    private boolean validateTrain(Train train1, String source, String destination) {
        List<String> stationOrder = train1.getStations().stream()
                .map(String::toLowerCase)
                .toList();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

    //Seats available Train
    public List<List<Integer>> getSeats(String  trainId){
        return trains.stream().filter(train1 -> train1.getTrainId().equals(trainId)).map(Train::getSeats).findFirst().orElse(null);
    }

    //Fetch train using Id
    public Train getTrain(String trainId){
        return trains.stream().filter(train1 -> train1.getTrainId().equals(trainId)).findFirst().orElse(null);
    }

    //Adding Train
    public void saveTrain(Train newTrain) throws IOException {
        Optional<Train> existingTrain = trains.stream().filter(t -> t.getTrainId().equalsIgnoreCase(newTrain.getTrainId())).findFirst();
        if (existingTrain.isPresent()) {
            updateTrain(newTrain);
        }else{
            trains.add(newTrain);
            saveTrainListToFile();
        }
    }


    //Update train
    public void updateTrain(Train updateTrain) throws IOException {
        OptionalInt index = IntStream.range(0, trains.size()).filter(i -> trains.get(i).getTrainId().equalsIgnoreCase(updateTrain.getTrainId())).findFirst();

        if (index.isPresent()) {
            trains.set(index.getAsInt(), updateTrain);
            saveTrainListToFile();
        }else{
            saveTrain(updateTrain);
        }
    }

    private void saveTrainListToFile() throws IOException {
        objectMapper.writeValue(new File(TRAINS_PATH), trains); // Write the updated list back to the file
    }
}
