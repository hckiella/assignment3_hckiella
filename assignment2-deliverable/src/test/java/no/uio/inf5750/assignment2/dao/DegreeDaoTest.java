package no.uio.inf5750.assignment2.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Degree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/assignment2/beans.xml" })
@Transactional
public class DegreeDaoTest {

	@Autowired
	private DegreeDAO degreeDao;

	@Test
	public void testSaveDegree() {
		Degree degree = new Degree("Bachelor");
		int id = degreeDao.saveDegree(degree);

		assertTrue(degree.equals(degreeDao.getDegree(id)));
		
		assertEquals(id, degreeDao.saveDegree(degree));
	}
	
	@Test
	public void testGetDegree() {
		Degree degree = new Degree("Bachelor");
		int id = degreeDao.saveDegree(degree);
		
		assertTrue(degree.equals(degreeDao.getDegree(id)));
		
		assertNull(degreeDao.getDegree(0));
	}

	@Test
	public void testGetDegreeByType() {
		Degree degree = new Degree("Bachelor");
		degreeDao.saveDegree(degree);
		
		assertTrue(degree.equals(degreeDao.getDegreeByType(degree.getType())));
		
		assertNull(degreeDao.getDegreeByType("Master"));
	}

	@Test
	public void testGetAllDegrees() {
		Degree degree1 = new Degree("Bachelor");
		Degree degree2 = new Degree("Master");
		Degree degree3 = new Degree("PHD");		
		
		degreeDao.saveDegree(degree1);
		degreeDao.saveDegree(degree2);
		
		Collection<Degree> degrees = degreeDao.getAllDegrees();
		
		assertTrue(degrees.contains(degree1));
		assertTrue(degrees.contains(degree2));
		assertFalse(degrees.contains(degree3));
	}

	@Test
	public void testDelDegree() {
		Degree degree = new Degree("Bachelor");
		int id = degreeDao.saveDegree(degree);

		assertTrue(degree.equals(degreeDao.getDegree(id)));
				
		degreeDao.delDegree(degree);
		
		assertNull(degreeDao.getDegree(id));

	}
}
