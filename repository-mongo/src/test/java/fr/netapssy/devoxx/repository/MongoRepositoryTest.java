package fr.netapssy.devoxx.repository;

import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.netapsys.devoxx.repository.AccountInformation;
import fr.netapsys.devoxx.repository.Repository;

public class MongoRepositoryTest 
extends CamelBlueprintTestSupport {


	public final Logger log = LoggerFactory.getLogger(MongoRepositoryTest.class);


 	@Override
    protected String getBlueprintDescriptor() {
        return "OSGI-INF/blueprint/blueprint.xml,/test.xml";
    }

    @Override
    protected String[] loadConfigAdminConfigurationFile() {
        return new String[] {"src/main/resources/repository-mongo.cfg","repository-mongo"};
    }

	@Test
	public void testRecherche()
	{
		Repository repository = getOsgiService(Repository.class);
		String login = "miles.davis";
		AccountInformation result = repository.search(login);
		Assert.assertEquals("miles.davis@jazz.net", result.getEmail());
		Assert.assertEquals(login, result.getLogin());
		Assert.assertEquals("DAVIS", result.getLastname());
		Assert.assertEquals("Miles", result.getFirstname());
	}
}

