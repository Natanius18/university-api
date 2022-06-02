package software.sigma.internship.service;

import software.sigma.internship.entity.Test;

import java.util.List;

public interface TestService {
    List<Test> findAll();

    Test findById(Long id);

    Test save(Test test);

    void deleteById(Long id);
}
