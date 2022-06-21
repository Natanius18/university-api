package software.sigma.internship.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.sigma.internship.test.passing.CounterStrategy;
import software.sigma.internship.test.passing.OneCorrectAnswerStrategy;
import software.sigma.internship.test.passing.SeveralCorrectAnswersStrategy;

import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Map<Integer, CounterStrategy> counterStrategyHashMap() {
        CounterStrategy oneCorrect = new OneCorrectAnswerStrategy();
        CounterStrategy severalCorrect = new SeveralCorrectAnswersStrategy();
        return Map.of(
                1, oneCorrect,
                2, severalCorrect
        );
    }
}
