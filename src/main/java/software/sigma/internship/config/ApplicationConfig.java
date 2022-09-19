package software.sigma.internship.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Response;
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
    @Qualifier("testForStudentMapper")
    public ModelMapper testForStudentMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Test, TestDto>() {
            @Override
            protected void configure() {
                skip(destination.getTeacher().getRole());
                skip(destination.getTeacher().getPassword());
                skip(destination.getTeacher().getStatus());
            }
        });

        modelMapper.addMappings(new PropertyMap<Answer, AnswerDto>() {
            @Override
            protected void configure() {
                skip(destination.getCorrect());
            }
        });

        return modelMapper;
    }

    @Bean
    @Qualifier("testForTeacherMapper")
    public ModelMapper testForTeacherMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Test, TestDto>() {
            @Override
            protected void configure() {
                skip(destination.getTeacher().getRole());
                skip(destination.getTeacher().getPassword());
                skip(destination.getTeacher().getStatus());
            }
        });

        return modelMapper;
    }


    @Bean
    @Qualifier("allTestsMapper")
    public ModelMapper allTestsMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Test, TestDto>() {
            @Override
            protected void configure() {
                skip(destination.getTeacher().getRole());
                skip(destination.getTeacher().getPassword());
                skip(destination.getTeacher().getStatus());
                skip(destination.getQuestions());
            }
        });

        return modelMapper;
    }

    @Bean
    @Qualifier("studentMapper")
    public ModelMapper studentMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Student, StudentDto>() {
            @Override
            protected void configure() {
                skip(destination.getPassword());
                skip(destination.getStatus());
                skip(destination.getRole());
                skip(destination.getVerificationCode());
            }
        });

        return modelMapper;
    }

    @Bean
    @Qualifier("teacherMapper")
    public ModelMapper teacherMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Teacher, TeacherDto>() {
            @Override
            protected void configure() {
                skip(destination.getPassword());
                skip(destination.getStatus());
                skip(destination.getRole());
                skip(destination.getVerificationCode());
            }
        });

        return modelMapper;
    }

    @Bean
    @Qualifier("personMapper")
    public ModelMapper personMapper() {
        ModelMapper modelMapper = new ModelMapper();
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

        return modelMapper;
    }

    @Bean
    @Qualifier("responseToStatisticsMapper")
    public ModelMapper responseToStatisticsMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ResponseDto, TestStatisticsDto>() {
            @Override
            protected void configure() {
                map().setTestName(source.getTest().getName());
                map().setTeacherEmail(source.getTest().getTeacher().getEmail());
            }
        });

        modelMapper.addMappings(new PropertyMap<Response, ResponseDto>() {
            @Override
            protected void configure() {
                skip(destination.getTest());
                skip(destination.getAnswers());
                skip(destination.getStudent().getPassword());
                skip(destination.getStudent().getStatus());
                skip(destination.getStudent().getRole());
                skip(destination.getStudent().getVerificationCode());
            }
        });
        return modelMapper;
    }

    @Bean
    @Qualifier("allResponsesMapper")
    public ModelMapper allResponsesMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ResponseDto, TestStatisticsDto>() {
            @Override
            protected void configure() {
                map().setTestName(source.getTest().getName());
            }
        });

        modelMapper.addMappings(new PropertyMap<Response, ResponseDto>() {
            @Override
            protected void configure() {
                skip(destination.getTest().getQuestions());
                skip(destination.getStudent());
                skip(destination.getTest().getTeacher().getStatus());
                skip(destination.getTest().getTeacher().getRole());
                skip(destination.getTest().getTeacher().getPassword());
            }
        });

        modelMapper.addMappings(new PropertyMap<Answer, AnswerDto>() {
            @Override
            protected void configure() {
                skip(destination.getCorrect());
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
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
