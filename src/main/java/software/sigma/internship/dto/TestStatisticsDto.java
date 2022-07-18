package software.sigma.internship.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TestStatisticsDto {
    private String id;
    private String testName;
    private int numberOfTry;
    private float result;
    private float averageResult;
    private Date date;
}