package springaoptut.service;

import org.springframework.stereotype.Service;
import springaoptut.model.User;

@Service
public class UserService {
    private User user;

    public UserService() {
        this.user = new User("Raj", 27);
    }

    public void login(){
        System.out.println("Logged In");
    }
    public void logout() throws Exception {
        System.out.println("Logged Out");
        throw new Exception("Unable to logout user.");
    }
}
