package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;


public class HibernateCourseDAO implements CourseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public int saveCourse(Course course) {
		Course savedCourse = getCourseByCourseCode(course.getCourseCode());

		if (savedCourse == null) {
			course.setId(course.hashCode());
			Session session = sessionFactory.getCurrentSession();
			session.save(course);
			return course.getId();
		}

		else {
			System.out.println("Returning cached course");
			return savedCourse.getId();
		}
	}

	@Override
	@Transactional
	public Course getCourse(int id) {
		return (Course) sessionFactory.getCurrentSession()
				.get(Course.class, id);
	}

	@Override
	@Transactional
	public Course getCourseByCourseCode(String courseCode) {
		Session session = sessionFactory.getCurrentSession();
		return (Course) session.createQuery(
				"FROM Course WHERE courseCode='" + courseCode + "'")
				.uniqueResult();
	}

	@Override
	@Transactional
	public Course getCourseByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		return (Course) session.createQuery(
				"FROM Course WHERE name='" + name + "'").uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Course> getAllCourses() {
		Session session = sessionFactory.getCurrentSession();
		return (List<Course>) session.createQuery(
				"FROM Course ORDER by courseId ASC").list();
	}

	@Override
	@Transactional
	public void delCourse(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(course);
	}

}
