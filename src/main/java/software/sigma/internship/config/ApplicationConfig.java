package software.sigma.internship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;
import software.sigma.internship.enums.CountStrategy;
import software.sigma.internship.service.CounterStrategy;
import software.sigma.internship.test.passing.OneCorrectAnswerStrategy;
import software.sigma.internship.test.passing.SeveralCorrectAnswersStrategy;

import java.util.Map;

import static software.sigma.internship.enums.CountStrategy.ONE_CORRECT_ANSWER;
import static software.sigma.internship.enums.CountStrategy.SEVERAL_CORRECT_ANSWERS;

@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfig {

    @Bean
    public Map<CountStrategy, CounterStrategy> counterStrategyMap() {
        CounterStrategy oneCorrect = new OneCorrectAnswerStrategy();
        CounterStrategy severalCorrect = new SeveralCorrectAnswersStrategy();
        return Map.of(
                ONE_CORRECT_ANSWER, oneCorrect,
                SEVERAL_CORRECT_ANSWERS, severalCorrect
        );
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
