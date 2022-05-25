package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dao.repo.TeacherRepository;
import software.sigma.internship.user.Teacher;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> fetchList() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher update(Teacher teacher, Long id) {
        Teacher teacherDB = teacherRepository.findById(id).get();
        if (Objects.nonNull(teacher.getFirstName())
                && !"".equalsIgnoreCase((
                teacher.getFirstName()))) {
            teacherDB.setFirstName(teacher.getFirstName());
        }

        if (Objects.nonNull(teacher.getLastName())
                && !"".equalsIgnoreCase((
                teacher.getLastName()))) {
            teacherDB.setLastName(teacher.getLastName());
        }
//??
        if (Objects.nonNull(teacher.getPosition())) {
            teacherDB.setPosition(teacher.getPosition());
        }

        return teacherRepository.save(teacherDB);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher fetch(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        return optionalTeacher.orElse(null);
    }
}
