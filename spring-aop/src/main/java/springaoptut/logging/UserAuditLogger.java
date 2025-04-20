package springaoptut.logging;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAuditLogger {

    @Before("execution(public void springaoptut.service.UserService.login())")
    public void loggingAdvice(){
        System.out.println("Before advice for login is executed");
    }

    @After("execution(public void springaoptut.service.UserService.login())")
    public void loggingAdviceAfter(){
        System.out.println("After advice for login is executed");
    }
}
