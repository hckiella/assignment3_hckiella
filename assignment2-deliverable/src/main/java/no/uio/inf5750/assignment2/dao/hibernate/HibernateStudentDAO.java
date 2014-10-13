package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Student;


public class HibernateStudentDAO implements StudentDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public int saveStudent(Student student) {
		Student savedStudent = getStudentByName(student.getName());

		if (savedStudent == null) {
			student.setId(student.hashCode());

			Session session = sessionFactory.getCurrentSession();
			session.save(student);

			return student.getId();
		}

		else {
			System.out.println("Returning cached student");
			return savedStudent.getId();
		}
	}

	@Override
	@Transactional
	public Student getStudent(int id) {
		return (Student) sessionFactory.getCurrentSession().get(Student.class,
				id);
	}

	@Override
	@Transactional
	public Student getStudentByName(String name) {
		Session session = sessionFactory.getCurrentSession();

		return (Student) session.createQuery(
				"FROM Student WHERE name='" + name + "'").uniqueResult();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.getCurrentSession();
	
		return (List<Student>) session.createQuery(
				"FROM Student ORDER by studentId ASC").list();
	}

	@Override
	@Transactional
	public void delStudent(Student student) {
		Session session = sessionFactory.getCurrentSession();
		
		session.delete(student);
	}
}