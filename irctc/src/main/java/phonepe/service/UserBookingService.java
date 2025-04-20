package phonepe.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import enteties.Train;
import enteties.User;
import phonepe.util.UserServiceUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(SerializationFeature.INDENT_OUTPUT);
    private static final String USER_PATH = "irctc/src/main/java/localDB/users.json";


    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        userList = mapper.readValue(new File(USER_PATH), new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getUsername().equals(user.getUsername()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();

        return foundUser.isPresent();
    }

    public Boolean singUp(User newUser){
       try{
           userList.add(newUser);
           saveUserListToFile();
           return Boolean.TRUE;
       }catch (IOException e){
           return Boolean.FALSE;
       }
    }

    private void saveUserListToFile() throws IOException {
        File userFile = new File(USER_PATH);
        //Serialization
        mapper.writeValue(userFile, userList);
    }

    //FetchBooking
    public void fetchBooking(){
       try{
           if(loginUser()){
               System.out.println("Fetch your bookings");
               user.printTickets();
           }
       }catch (Exception e){
           if(e instanceof NullPointerException){
               System.out.println("Please login with your username and password");
           }
       }
    }

    //CancelBooking
    public Boolean cancelBooking(String ticketId) throws IOException {
        Optional<User> userBooked = userList.stream()
                .filter(user1 -> user1.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(ticketId)))
                .findFirst();

        if (userBooked.isPresent()) {
            user = userBooked.get();
            System.out.printf("Booking canceled for user: %s ", user.getUsername());
            saveUserListToFile();
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    //Get Trains
    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    //Search Seats
    public List<List<Integer>> trainSeats(String trainId) throws IOException {
        TrainService trainService = new TrainService();
        return trainService.getSeats(trainId);
    }

    //Booking seats
    public Boolean bookTickets(String trainId, int row, int seat) {
        try{
            TrainService trainService = new TrainService();
            Train train = trainService.getTrain(trainId);
            List<List<Integer>> seats = trainService.getSeats(trainId);

            if(row >= 0  && row < seats.size() && seat >= 0 && seat < seats.get(row).size()){
                if(seats.get(row).get(seat) == 0){
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.saveTrain(train);
                    return true; // booking successfully.
                }else{
                    return false; //seat already booked.
                }
            }else {
                return false; //invalid row or seat index.
            }
        }catch (IOException e){
            return false;
        }
    }
}
