package software.sigma.internship.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.sigma.internship.user.Student;

import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Student> readAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from student", Student.class).getResultList();
    }
//TODO
    @Override
    public Student read(Long id) {
        return null;
    }

    @Override
    public Student createStudent(Student student) {
        return null;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        return null;
    }

    @Override
    public void deleteStudent(long id) {

    }
}
