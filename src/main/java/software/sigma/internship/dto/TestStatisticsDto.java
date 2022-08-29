package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TestStatisticsDto {
    private String id;
    private String testName;
    private String teacherEmail;
    private int numberOfTry;
    private float result;
    private float averageResult;
    private float averageNumberOfTry;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
}