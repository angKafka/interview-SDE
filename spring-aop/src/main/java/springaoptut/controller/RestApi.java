package springaoptut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springaoptut.service.UserService;

@RestController
@RequestMapping("api/v1/aop")
public class RestApi {

    private UserService userService;

    @Autowired
    public RestApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String userLogIn(){
        userService.login();
        return "User logged in successfully";
    }
}
