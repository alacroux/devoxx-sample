package fr.netapssy.devoxx.repository;

import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.netapsys.devoxx.repository.AccountInformation;
import fr.netapsys.devoxx.repository.Repository;

public class LdapRepositoryTest 
extends CamelBlueprintTestSupport {


	public final Logger log = LoggerFactory.getLogger(LdapRepositoryTest.class);


 	@Override
    protected String getBlueprintDescriptor() {
        return "OSGI-INF/blueprint/blueprint.xml,/test.xml";
    }

    @Override
    protected String[] loadConfigAdminConfigurationFile() {
        return new String[] {"src/main/resources/repository-ldap.cfg","repository-ldap"};
    }

	@Test
	public void testRecherche()
	{
		Repository repository = getOsgiService(Repository.class);
		String login = "chet.baker";
		AccountInformation result = repository.search(login);
		Assert.assertEquals("chet.baker@jazz.net", result.getEmail());
		Assert.assertEquals(login, result.getLogin());
		Assert.assertEquals("BAKER", result.getLastname());
		Assert.assertEquals("Chet", result.getFirstname());
	}
}

