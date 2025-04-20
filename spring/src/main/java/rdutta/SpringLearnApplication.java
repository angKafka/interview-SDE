package rdutta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rdutta.bean.UserConfig;

@SpringBootApplication
public class SpringLearnApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringLearnApplication.class, args);
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserConfig config = (UserConfig) context.getBean("userConfigBean");
    }
}
