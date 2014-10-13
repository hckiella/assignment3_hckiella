package no.uio.inf5750.assignment2.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/META-INF/assignment2/beans.xml" })
@Transactional
public class StudentDaoTest {

	@Autowired
	private StudentDAO studentDao;

	@Test
	public void testSaveStudent() {
		Student student = new Student("Freddy");
		int id = studentDao.saveStudent(student);

		assertTrue(student.equals(studentDao.getStudent(id)));
		
		assertEquals(id, studentDao.saveStudent(student));
	}
	
	@Test
	public void testGetStudent() {
		Student student = new Student("Freddy");
		int id = studentDao.saveStudent(student);
		
		assertTrue(student.equals(studentDao.getStudent(id)));
		
		assertNull(studentDao.getStudent(0));
	}

	@Test
	public void testGetStudentByName() {
		Student student = new Student("Freddy");
		studentDao.saveStudent(student);
		
		assertTrue(student.equals(studentDao.getStudentByName(student.getName())));
		
		assertNull(studentDao.getStudentByName("Hans"));
	}	
	

	@Test
	public void testGetAllStudents() {
		Student student1 = new Student("Freddy");
		Student student2 = new Student("Hans");
		Student student3 = new Student("Peder");
		
		
		studentDao.saveStudent(student1);
		studentDao.saveStudent(student2);
		
		Collection<Student> students = studentDao.getAllStudents();
		
		assertTrue(students.contains(student1));
		assertTrue(students.contains(student2));
		assertFalse(students.contains(student3));

	}

	@Test
	public void testDelStudent() {
		Student student = new Student("Freddy");
		int id = studentDao.saveStudent(student);

		assertTrue(student.equals(studentDao.getStudent(id)));
				
		studentDao.delStudent(student);
		
		assertNull(studentDao.getStudent(id));

	}
}