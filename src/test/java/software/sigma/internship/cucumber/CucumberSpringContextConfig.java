package software.sigma.internship.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import software.sigma.internship.UniversityApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = UniversityApplication.class)
public class CucumberSpringContextConfig {

}