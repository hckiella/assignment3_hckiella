package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	
	@RequestMapping(value = "/api/student/{student}/location", params = {"latitude", "longitude"}, method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> setStudentLocation(@PathVariable String student, @RequestParam(value="latitude") String latitude, @RequestParam(value="longitude") String longitude) {
		Student s = studentSystem.getStudent(Integer.parseInt(student));
		System.out.println("\n\n " + s.getName() + "\n\n");
		System.out.println("\n\n " + latitude);
		s.setLatitude(latitude);
		s.setLongitude(longitude);
		
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
