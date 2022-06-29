package software.sigma.internship.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.service.ResponseService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/responses", produces = "application/json")
public class ResponseController {
    private ResponseService service;

    @ApiOperation(value = "Get a response by id", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the response by id"),
            @ApiResponse(code = 404, message = "The response doesn't exist")
    })
    @GetMapping("/{id}")
    public ResponseDto fetch(@ApiParam(value = "id of the response we want to get")
                             @PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Save a response", response = ResponseDto.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Response was successfully saved")})
    @PostMapping
    public ResponseDto save(@ApiParam(value = "Object of the response to be saved")
                            @RequestBody ResponseDto response) {
        return service.save(response);
    }

}
