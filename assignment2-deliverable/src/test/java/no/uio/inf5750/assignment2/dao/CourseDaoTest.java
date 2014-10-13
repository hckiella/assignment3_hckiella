package no.uio.inf5750.assignment2.dao;

import static org.junit.Assert.*;

import java.util.Collection;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/META-INF/assignment2/beans.xml" })
@Transactional
public class CourseDaoTest {

	@Autowired
	private CourseDAO courseDao;

	@Test
	public void testSaveCourse() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		int id = courseDao.saveCourse(course);

		assertTrue(course.equals(courseDao.getCourse(id)));
		
		assertEquals(id, courseDao.saveCourse(course));
	}

	@Test
	public void testGetCourse() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		int id = courseDao.saveCourse(course);
		
		assertTrue(course.equals(courseDao.getCourse(id)));
		
		assertNull(courseDao.getCourse(0));
	}

	@Test
	public void testGetCourseByCourseCode() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		courseDao.saveCourse(course);
		
		assertTrue(course.equals(courseDao.getCourseByCourseCode(course.getCourseCode())));
		
		assertNull(courseDao.getCourseByCourseCode("INF1000"));
	}

	@Test
	public void testGetCourseByName() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		courseDao.saveCourse(course);
		
		assertTrue(course.equals(courseDao.getCourseByName(course.getName())));
		
		assertNull(courseDao.getCourseByName("Funksjonell programmering"));
	}

	@Test
	public void testgetAllCourses() {
		Course course1 = new Course("INF1010", "Objektorientert programmering");
		Course course2 = new Course("INF1050", "Systemutvikling");
		Course course3 = new Course("INF2810", "Funksjonell programmering");
		
		
		courseDao.saveCourse(course1);
		courseDao.saveCourse(course2);
		
		Collection<Course> courses = courseDao.getAllCourses();
		
		assertTrue(courses.contains(course1));
		assertTrue(courses.contains(course2));
		assertFalse(courses.contains(course3));
	}

	@Test
	public void testDelCourse() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		int id = courseDao.saveCourse(course);

		assertTrue(course.equals(courseDao.getCourse(id)));
				
		courseDao.delCourse(course);
		
		assertNull(courseDao.getCourse(id));
	}
}