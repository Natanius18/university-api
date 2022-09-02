package cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import software.sigma.internship.UniversityApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = UniversityApplication.class, properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/university",
        "spring.datasource.username=natanius",
        "spring.datasource.password=12345",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class CucumberSpringContextConfig {

}