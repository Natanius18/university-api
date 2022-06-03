package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.entity.Test;
import software.sigma.internship.repo.TestRepository;
import software.sigma.internship.validator.exception.TestNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private TestRepository testRepository;

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public Test findById(Long id) {
        return testRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));

    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }
}
