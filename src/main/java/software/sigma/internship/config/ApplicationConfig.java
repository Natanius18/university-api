package software.sigma.internship.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.sigma.internship.enums.CountStrategy;
import software.sigma.internship.test.passing.CounterStrategy;
import software.sigma.internship.test.passing.OneCorrectAnswerStrategy;
import software.sigma.internship.test.passing.SeveralCorrectAnswersStrategy;

import java.util.Map;

import static software.sigma.internship.enums.CountStrategy.*;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Map<CountStrategy, CounterStrategy> counterStrategyHashMap() {
        CounterStrategy oneCorrect = new OneCorrectAnswerStrategy();
        CounterStrategy severalCorrect = new SeveralCorrectAnswersStrategy();
        return Map.of(
                ONE_CORRECT_ANSWER, oneCorrect,
                SEVERAL_CORRECT_ANSWERS, severalCorrect
        );
    }
}
