package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Degree;


public class HibernateDegreeDAO implements DegreeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public int saveDegree(Degree degree) {
		Degree savedDegree = getDegreeByType(degree.getType());

		if (savedDegree == null) {
			degree.setId(degree.hashCode());
			
			Session session = sessionFactory.getCurrentSession();
			session.save(degree);
			
			return degree.getId();
		}

		else {
			System.out.println("Returning cached degree");
			return savedDegree.getId();
		}
	}

	@Override
	@Transactional
	public Degree getDegree(int id) {
		return (Degree) sessionFactory.getCurrentSession()
				.get(Degree.class, id);

	}

	@Override
	@Transactional
	public Degree getDegreeByType(String type) {
		Session session = sessionFactory.getCurrentSession();
		
		return (Degree) session.createQuery(
				"FROM Degree WHERE type='" + type + "'").uniqueResult();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Collection<Degree> getAllDegrees() {
		Session session = sessionFactory.getCurrentSession();

		return (List<Degree>) session.createQuery(
				"FROM Degree ORDER by degreeId ASC").list();
	}

	@Override
	@Transactional
	public void delDegree(Degree degree) {
		Session session = sessionFactory.getCurrentSession();
		
		session.delete(degree);
	}

}
