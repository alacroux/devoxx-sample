package fr.netapssy.devoxx.repository;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.netapsys.devoxx.repository.AccountInformation;
import fr.netapsys.devoxx.repository.mongo.support.MongoRepository;


public class MongoRepositorySimpleTest {


 	public final Logger log = LoggerFactory.getLogger(MongoRepositorySimpleTest.class);


	@Test
	public void testRecherche()
	{
		MongoRepository repository = new MongoRepository();
		repository.setHost("localhost");
		repository.setPort(27017);
		repository.setDatabase("repository");
		String login = "miles.davis";
		AccountInformation result = repository.search(login);
		Assert.assertEquals("miles.davis@jazz.net", result.getEmail());
		Assert.assertEquals(login, result.getLogin());
		Assert.assertEquals("DAVIS", result.getLastname());
		Assert.assertEquals("Miles", result.getFirstname());
	}
	
}

