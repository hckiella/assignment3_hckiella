package no.uio.inf5750.assignment2.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

public class DefaultStudentSystem implements StudentSystem {

	@Autowired
	private CourseDAO courseDao;

	@Autowired
	private DegreeDAO degreeDao;

	@Autowired
	private StudentDAO studentDao;

	@Override
	public int addCourse(String courseCode, String name) {
		Course course = new Course(courseCode, name);
		
		return courseDao.saveCourse(course);
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
		Course course = courseDao.getCourse(courseId);

		if (course != null) {
			course.setCourseCode(courseCode);
			course.setName(name);
		}
	}

	@Override
	public Course getCourse(int courseId) {
		return courseDao.getCourse(courseId);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		return courseDao.getCourseByCourseCode(courseCode);
	}

	@Override
	public Course getCourseByName(String name) {
		return courseDao.getCourseByName(name);
	}

	@Override
	public Collection<Course> getAllCourses() {
		return courseDao.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		Course course = courseDao.getCourse(courseId);

		if (course != null) {
			Collection<Degree> degrees = degreeDao.getAllDegrees();
			Collection<Student> students = studentDao.getAllStudents();

			for (Degree d : degrees)
				d.getRequiredCourses().remove(course);

			for (Student s : students)
				s.getCourses().remove(course);

			courseDao.delCourse(course);
		}
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		Course course = courseDao.getCourse(courseId);
		Student student = studentDao.getStudent(studentId);

		if (course != null && student != null) {
			course.getAttendants().add(student);
			student.getCourses().add(course);
		}
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Course course = courseDao.getCourse(courseId);
		Student student = studentDao.getStudent(studentId);

		if (course != null && student != null) {
			course.getAttendants().remove(student);
			student.getCourses().remove(course);
		}
	}

	@Override
	public int addDegree(String type) {
		Degree degree = new Degree(type);
		return degreeDao.saveDegree(degree);
	}

	@Override
	public void updateDegree(int degreeId, String type) {
		Degree degree = degreeDao.getDegree(degreeId);

		if (degree != null)
			degree.setType(type);
	}

	@Override
	public Degree getDegree(int degreeId) {
		return degreeDao.getDegree(degreeId);
	}

	@Override
	public Degree getDegreeByType(String type) {
		return degreeDao.getDegreeByType(type);
	}

	@Override
	public Collection<Degree> getAllDegrees() {
		return degreeDao.getAllDegrees();
	}

	@Override
	public void delDegree(int degreeId) {
		Degree degree = degreeDao.getDegree(degreeId);
		if (degree != null) {

			Collection<Student> students = studentDao.getAllStudents();
			for (Student s : students)
				s.getDegrees().remove(degree);

			degreeDao.delDegree(degree);
		}
	}

	@Override
	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		Degree degree = degreeDao.getDegree(degreeId);
		Course course = courseDao.getCourse(courseId);

		if (degree != null && course != null) {
			degree.getRequiredCourses().add(course);
		}
	}

	@Override
	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		Course course = courseDao.getCourse(courseId);
		Degree degree = degreeDao.getDegree(degreeId);

		if (degree != null && course != null) {
			degree.getRequiredCourses().remove(course);
		}
	}

	@Override
	public int addStudent(String name) {
		Student student = new Student(name);
		
		return studentDao.saveStudent(student);
	}

	@Override
	public void updateStudent(int studentId, String name) {
		Student student = studentDao.getStudent(studentId);

		if (student != null)
			student.setName(name);
	}

	@Override
	public Student getStudent(int studentId) {
		return studentDao.getStudent(studentId);
	}

	@Override
	public Student getStudentByName(String name) {
		return studentDao.getStudentByName(name);
	}

	@Override
	public Collection<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {
		Student student = studentDao.getStudent(studentId);

		if (student != null) {
			Collection<Course> courses = courseDao.getAllCourses();

			for (Course c : courses)
				c.getAttendants().remove(student);
		}

		studentDao.delStudent(student);

	}

	@Override
	public void addDegreeToStudent(int studentId, int degreeId) {
		Student student = studentDao.getStudent(studentId);
		Degree degree = degreeDao.getDegree(degreeId);

		if (student != null && degree != null)
			student.getDegrees().add(degree);
	}

	@Override
	public void removeDegreeFromStudent(int studentId, int degreeId) {
		Student student = studentDao.getStudent(studentId);
		Degree degree = degreeDao.getDegree(degreeId);

		if (student != null && degree != null)
			student.getDegrees().remove(degree);

	}

	@Override
	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
		Degree degree = degreeDao.getDegree(degreeId);
		Student student = studentDao.getStudent(studentId);

		if (student != null && degree != null) {
			if (student.getCourses().containsAll(degree.getRequiredCourses()))
				return true;

			else
				return false;
		} else
			return false;
	}
	
	@Override
	public void setStudentLocation(int studentId, String latitude, String longitude) {
		Student student = studentDao.getStudent(studentId);
		
		if(student != null) {
			student.setLatitude(latitude);
			student.setLongitude(longitude);
		}
	}
}