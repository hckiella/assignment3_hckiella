package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

	@Autowired
	StudentSystem studentSystem;

	@RequestMapping(value = "/api/student", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> getAllStudents() { //HttpServletRequest request,	HttpServletResponse response) {

		Collection<Student> students = studentSystem.getAllStudents();
		return students;
	}
	
	
	
	@RequestMapping(value = "/api/student/{student}/location", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> setStudentLocation(@PathVariable String studentId, HttpServletRequest request) {
		Student student = studentSystem.getStudent(Integer.parseInt(studentId));
		student.setLatitude(request.getParameter("latitude"));
		student.setLongitude(request.getParameter("longitude"));
		
		Collection<Student> students = studentSystem.getAllStudents();
		return students;
	}

	@RequestMapping(value = "/api/course", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Course> getAllCourses() { //HttpServletRequest request, HttpServletResponse response) {

		Collection<Course> courses = studentSystem.getAllCourses();
		return courses;

	}

	@RequestMapping(value = "/api/degreet", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Degree> getAllDegrees() { //HttpServletRequest request, HttpServletResponse response) {

		Collection<Degree> degrees = studentSystem.getAllDegrees();
		return degrees;

	}
}
