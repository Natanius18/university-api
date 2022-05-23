package software.sigma.internship.service.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.repo.TeacherRepository;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;

@Repository
@AllArgsConstructor
public class TeacherDaoImpl implements TeacherDao {
    private final TeacherRepository teacherRepository;

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
