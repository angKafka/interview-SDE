package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import enteties.User;
import util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private ObjectMapper mapper = new ObjectMapper();
    private static final String USER_PATH = "src/main/java/localDB/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        File users = new File(USER_PATH);
        this.userList = mapper.readValue(users, new TypeReference<List<User>>() {});
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
        user.printTickets();
    }

    //CancelBooking

    //BookTicket

}
