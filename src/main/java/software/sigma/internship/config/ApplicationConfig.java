package software.sigma.internship.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.Student;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.entity.Test;
import software.sigma.internship.enums.CountStrategy;
import software.sigma.internship.service.CounterStrategy;
import software.sigma.internship.test.passing.OneCorrectAnswerStrategy;
import software.sigma.internship.test.passing.SeveralCorrectAnswersStrategy;

import java.util.Map;

import static software.sigma.internship.enums.CountStrategy.ONE_CORRECT_ANSWER;
import static software.sigma.internship.enums.CountStrategy.SEVERAL_CORRECT_ANSWERS;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Test, TestDto>() {
            @Override
            protected void configure() {
                skip(destination.getTeacher().getRole());
                skip(destination.getTeacher().getPassword());
                skip(destination.getTeacher().getStatus());
            }
        });

        modelMapper.addMappings(new PropertyMap<Student, StudentDto>() {
            @Override
            protected void configure() {
                skip(destination.getPassword());
                skip(destination.getStatus());
                skip(destination.getRole());
                skip(destination.getVerificationCode());
            }
        });

        modelMapper.addMappings(new PropertyMap<Teacher, TeacherDto>() {
            @Override
            protected void configure() {
                skip(destination.getPassword());
                skip(destination.getStatus());
                skip(destination.getRole());
                skip(destination.getVerificationCode());
            }
        });

        modelMapper.addMappings(new PropertyMap<Student, PersonDto>() {
            @Override
            protected void configure() {
                skip(destination.getPassword());
            }
        });

        modelMapper.addMappings(new PropertyMap<Teacher, PersonDto>() {
            @Override
            protected void configure() {
                skip(destination.getPassword());
            }
        });

        modelMapper.addMappings(new PropertyMap<ResponseDto, TestStatisticsDto>() {
            @Override
            protected void configure() {
                map().setTestName(source.getTest().getName());
            }
        });

        return modelMapper;
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

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
