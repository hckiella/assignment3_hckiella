package no.uio.inf5750.assignment2.service;

import static org.junit.Assert.*;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/assignment2/beans.xml" })
@Transactional
public class StudentSystemTest {

	@Autowired
	private StudentSystem studentSystem;

	@Test
	public void testAddCourse() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		studentSystem.addCourse("INF1010", "Objektorientert programmering");

		assertTrue(studentSystem.getAllCourses().contains(course));

	}

	@Test
	public void testUpdateCourse() {
		Course course = new Course("INF1010", "Objektorientert programmering");

		int courseId = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");
		assertTrue(studentSystem.getAllCourses().contains(course));

		studentSystem.updateCourse(courseId, "INF1020",
				"Objektorientert programmering 2");
		assertFalse(studentSystem.getAllCourses().contains(course));

		course.setCourseCode("INF1020");
		course.setName("Objektorientert programmering 2");

		assertTrue(studentSystem.getAllCourses().contains(course));
	}

	@Test
	public void testGetCourse() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		int courseId = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");

		assertTrue(course.equals(studentSystem.getCourse(courseId)));
		assertNull(studentSystem.getCourse(0));
	}

	@Test
	public void testGetCourseByCourseCode() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		studentSystem.addCourse("INF1010", "Objektorientert programmering");

		assertTrue(course.equals(studentSystem.getCourseByCourseCode(course
				.getCourseCode())));
		assertNull(studentSystem.getCourseByCourseCode("INF1000"));
	}

	@Test
	public void testGetCourseByName() {
		Course course = new Course("INF1010", "Objektorientert programmering");
		studentSystem.addCourse("INF1010", "Objektorientert programmering");

		assertTrue(course
				.equals(studentSystem.getCourseByName(course.getName())));
		assertNull(studentSystem.getCourseByName("Funksjonell programmering"));
	}

	@Test
	public void testGetAllCourses() {
		Course course1 = new Course("INF1010", "Objektorientert programmering");
		Course course2 = new Course("INF2810", "Funksjonell programmering");

		studentSystem.addCourse("INF1010", "Objektorientert programmering");
		studentSystem.addCourse("INF2810", "Funksjonell programmering");

		assertTrue(studentSystem.getAllCourses().contains(course1));
		assertTrue(studentSystem.getAllCourses().contains(course2));
	}

	@Test
	public void testDelCourse() {
		int degreeId1 = studentSystem.addDegree("Bachelor");
		int degreeId2 = studentSystem.addDegree("Master");
		
		int studentId1 = studentSystem.addStudent("Freddy");
		int studentId2 = studentSystem.addStudent("Hans");
		
		Course course = new Course("INF1010", "Objektorientert programmering");
		int courseId = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");

		studentSystem.addAttendantToCourse(courseId, studentId1);
		studentSystem.addAttendantToCourse(courseId, studentId2);
		
		studentSystem.addRequiredCourseToDegree(degreeId1, courseId);
		studentSystem.addRequiredCourseToDegree(degreeId2, courseId);
		
		studentSystem.delCourse(courseId);
		
		assertFalse(studentSystem.getStudent(studentId1).getCourses().contains(course));
		assertFalse(studentSystem.getStudent(studentId2).getCourses().contains(course));
		assertFalse(studentSystem.getDegree(degreeId1).getRequiredCourses().contains(course));
		assertFalse(studentSystem.getDegree(degreeId2).getRequiredCourses().contains(course));
		
		assertNull(studentSystem.getCourse(courseId));	
	}

	@Test
	public void testaddAttendantToCourse() {
		Course course1 = new Course("INF1010", "Objektorientert programmering");
		int courseId1 = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");

		Student student = new Student("Freddy");
		int studentId = studentSystem.addStudent("Freddy");

		Course course2 = new Course("INF2810", "Funksjonell programmering");
		int courseId2 = studentSystem.addCourse("INF2810",
				"Funksjonell programmering");

		studentSystem.addAttendantToCourse(courseId1, studentId);

		assertTrue(studentSystem.getCourse(courseId1).getAttendants()
				.contains(student));
		assertTrue(studentSystem.getStudent(studentId).getCourses()
				.contains(course1));

		assertFalse(studentSystem.getCourse(courseId2).getAttendants()
				.contains(student));
		assertFalse(studentSystem.getStudent(studentId).getCourses()
				.contains(course2));
	}

	@Test
	public void testRemoveAttendantFromCourse() {
		Course course1 = new Course("INF1010", "Objektorientert programmering");
		int courseId1 = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");

		Course course2 = new Course("INF2810", "Funksjonell programmering");
		int courseId2 = studentSystem.addCourse("INF2810",
				"Funksjonell programmering");

		Student student = new Student("Freddy");
		int studentId = studentSystem.addStudent("Freddy");

		studentSystem.addAttendantToCourse(courseId1, studentId);
		studentSystem.addAttendantToCourse(courseId2, studentId);

		assertTrue(studentSystem.getCourse(courseId1).getAttendants()
				.contains(student));
		assertTrue(studentSystem.getStudent(studentId).getCourses()
				.contains(course1));

		studentSystem.removeAttendantFromCourse(courseId1, studentId);

		assertFalse(studentSystem.getCourse(courseId1).getAttendants()
				.contains(student));
		assertFalse(studentSystem.getStudent(studentId).getCourses()
				.contains(course1));

		assertTrue(studentSystem.getCourse(courseId2).getAttendants()
				.contains(student));
		assertTrue(studentSystem.getStudent(studentId).getCourses()
				.contains(course2));
	}

	@Test
	public void testAddDegree() {
		Degree degree = new Degree("Bachelor");

		studentSystem.addDegree("Bachelor");

		assertTrue(studentSystem.getAllDegrees().contains(degree));
	}

	@Test
	public void testUpdateDegree() {
		Degree degree = new Degree("Bachelor");

		int degreeId = studentSystem.addDegree("Bachelor");
		assertTrue(studentSystem.getAllDegrees().contains(degree));

		studentSystem.updateDegree(degreeId, "Master");
		assertFalse(studentSystem.getAllDegrees().contains(degree));

		degree.setType("Master");
		assertTrue(studentSystem.getAllDegrees().contains(degree));
	}

	@Test
	public void testGetDegree() {
		Degree degree = new Degree("Bachelor");
		int degreeId = studentSystem.addDegree("Bachelor");

		assertTrue(degree.equals(studentSystem.getDegree(degreeId)));
		assertNull(studentSystem.getDegree(0));
	}

	@Test
	public void testGetDegreeByType() {
		Degree degree = new Degree("Bachelor");
		studentSystem.addDegree("Bachelor");

		assertTrue(degree
				.equals(studentSystem.getDegreeByType(degree.getType())));
		assertNull(studentSystem.getDegreeByType("Master"));
	}

	@Test
	public void testGetAllDegrees() {
		Degree degree1 = new Degree("Bachelor");
		Degree degree2 = new Degree("Master");

		studentSystem.addDegree("Bachelor");
		studentSystem.addDegree("Master");

		assertTrue(studentSystem.getAllDegrees().contains(degree1));
		assertTrue(studentSystem.getAllDegrees().contains(degree2));
	}

	@Test
	public void testdelDegree() {
		Degree degree = new Degree("Bachelor");
		int degreeId = studentSystem.addDegree("Bachelor");

		int studentId1 = studentSystem.addStudent("Freddy");
		int studentId2 = studentSystem.addStudent("Hans");
		
		studentSystem.addDegreeToStudent(studentId1, degreeId);
		studentSystem.addDegreeToStudent(studentId2, degreeId);
		studentSystem.delDegree(degreeId);
		
		assertFalse(studentSystem.getStudent(studentId1).getDegrees().contains(degree));
		assertFalse(studentSystem.getStudent(studentId2).getDegrees().contains(degree));
		assertNull(studentSystem.getDegree(degreeId));	
	}

	@Test
	public void testAddRequiredCourseToDegree() {
		Course course1 = new Course("INF1010", "Objektorientert programmering");
		int courseId1 = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");

		int degreeId = studentSystem.addDegree("Bachelor");

		Course course2 = new Course("INF2810", "Funksjonell programmering");
		studentSystem.addCourse("INF2810", "Funksjonell programmering");

		studentSystem.addRequiredCourseToDegree(degreeId, courseId1);

		assertTrue(studentSystem.getDegree(degreeId).getRequiredCourses()
				.contains(course1));
		assertFalse(studentSystem.getDegree(degreeId).getRequiredCourses()
				.contains(course2));
	}

	@Test
	public void testRemoveRequiredCourseFromDegree() {
		Course course1 = new Course("INF1010", "Objektorientert programmering");
		int courseId1 = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");

		int degreeId = studentSystem.addDegree("Bachelor");

		Course course2 = new Course("INF2810", "Funksjonell programmering");
		int courseId2 = studentSystem.addCourse("INF2810",
				"Funksjonell programmering");

		studentSystem.addRequiredCourseToDegree(degreeId, courseId1);
		studentSystem.addRequiredCourseToDegree(degreeId, courseId2);

		assertTrue(studentSystem.getDegree(degreeId).getRequiredCourses()
				.contains(course1));
		assertTrue(studentSystem.getDegree(degreeId).getRequiredCourses()
				.contains(course2));

		studentSystem.removeRequiredCourseFromDegree(degreeId, courseId1);

		assertFalse(studentSystem.getDegree(degreeId).getRequiredCourses()
				.contains(course1));
		assertTrue(studentSystem.getDegree(degreeId).getRequiredCourses()
				.contains(course2));
	}

	@Test
	public void testAddStudent() {
		Student student = new Student("Freddy");
		studentSystem.addStudent("Freddy");

		assertTrue(studentSystem.getAllStudents().contains(student));
	}

	@Test
	public void testUpdateStudent() {
		Student student = new Student("Freddy");

		int studentId = studentSystem.addStudent("Freddy");
		assertTrue(studentSystem.getAllStudents().contains(student));

		studentSystem.updateStudent(studentId, "Hans");
		assertFalse(studentSystem.getAllStudents().contains(student));

		student.setName("Hans");
		assertTrue(studentSystem.getAllStudents().contains(student));
	}

	@Test
	public void testGetStudent() {
		Student student = new Student("Freddy");
		int studentId = studentSystem.addStudent("Freddy");

		assertTrue(student.equals(studentSystem.getStudent(studentId)));
		assertNull(studentSystem.getStudent(0));
	}

	@Test
	public void testGetStudentByName() {
		Student student = new Student("Freddy");
		studentSystem.addStudent("Freddy");

		assertTrue(student.equals(studentSystem.getStudentByName(student
				.getName())));
		assertNull(studentSystem.getStudentByName("Hans"));
	}

	@Test
	public void testGetAllStudents() {
		Student student1 = new Student("Freddy");
		Student student2 = new Student("Hans");

		studentSystem.addStudent("Freddy");
		studentSystem.addStudent("Hans");

		assertTrue(studentSystem.getAllStudents().contains(student1));
		assertTrue(studentSystem.getAllStudents().contains(student2));
	}

	@Test
	public void testDelStudent() {
		Student student = new Student("Freddy");
		int studentId = studentSystem.addStudent("Freddy");
				
		int courseId1 = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");		
		int courseId2 = studentSystem.addCourse("INF2810",
				"Funksjonell programmering");
		
		studentSystem.addAttendantToCourse(courseId1, studentId);
		studentSystem.addAttendantToCourse(courseId2, studentId);
		
		studentSystem.delStudent(studentId);
		
		assertFalse(studentSystem.getCourse(courseId1).getAttendants().contains(student));
		assertFalse(studentSystem.getCourse(courseId2).getAttendants().contains(student));
		assertNull(studentSystem.getStudent(studentId));
		
		

	}

	@Test
	public void testAddDegreeToStudent() {
		Degree degree1 = new Degree("Bachelor");
		int degreeId1 = studentSystem.addDegree("Bachelor");

		Degree degree2 = new Degree("Master");
		studentSystem.addDegree("Master");

		int studentId = studentSystem.addStudent("Freddy");

		studentSystem.addDegreeToStudent(studentId, degreeId1);

		assertTrue(studentSystem.getStudent(studentId).getDegrees()
				.contains(degree1));
		assertFalse(studentSystem.getStudent(studentId).getDegrees()
				.contains(degree2));
	}

	@Test
	public void testRemoveDegreeFromStudent() {
		Degree degree1 = new Degree("Bachelor");
		int degreeId1 = studentSystem.addDegree("Bachelor");

		Degree degree2 = new Degree("Master");
		int degreeId2 = studentSystem.addDegree("Master");

		int studentId = studentSystem.addStudent("Freddy");

		studentSystem.addDegreeToStudent(studentId, degreeId1);
		studentSystem.addDegreeToStudent(studentId, degreeId2);

		assertTrue(studentSystem.getStudent(studentId).getDegrees()
				.contains(degree1));
		assertTrue(studentSystem.getStudent(studentId).getDegrees()
				.contains(degree2));

		studentSystem.removeDegreeFromStudent(studentId, degreeId1);

		assertFalse(studentSystem.getStudent(studentId).getDegrees()
				.contains(degree1));
		assertTrue(studentSystem.getStudent(studentId).getDegrees()
				.contains(degree2));
	}

	@Test
	public void testStudentFulfillsDegreeRequirements() {
		int courseId1 = studentSystem.addCourse("INF1010",
				"Objektorientert programmering");
		int courseId2 = studentSystem.addCourse("INF4151", "Operativsystemer");

		int degreeId1 = studentSystem.addDegree("Bachelor");
		int degreeId2 = studentSystem.addDegree("Master");

		studentSystem.addRequiredCourseToDegree(degreeId1, courseId1);
		studentSystem.addRequiredCourseToDegree(degreeId2, courseId2);

		int studentId = studentSystem.addStudent("Freddy");
		studentSystem.addAttendantToCourse(courseId1, studentId);

		assertTrue(studentSystem.studentFulfillsDegreeRequirements(studentId,
				degreeId1));
		assertFalse(studentSystem.studentFulfillsDegreeRequirements(studentId,
				degreeId2));

		studentSystem.addAttendantToCourse(courseId2, studentId);

		assertTrue(studentSystem.studentFulfillsDegreeRequirements(studentId,
				degreeId2));
	}
}
