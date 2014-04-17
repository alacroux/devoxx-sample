package fr.netapssy.devoxx.repository;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.netapsys.devoxx.repository.AccountInformation;
import fr.netapsys.devoxx.repository.ldap.support.LdapRepository;

public class LdapRepositorySimpleTest {


 	public final Logger log = LoggerFactory.getLogger(LdapRepositorySimpleTest.class);


	@Test
	public void testRecherche()
	{
		LdapRepository repository = new LdapRepository();
		repository.setHost("localhost");
		repository.setPort(389);
		repository.setBaseDNSearch("ou=people,dc=devoxx-test,dc=fr");
		String login = "chet.baker";
		AccountInformation result = repository.search(login);
		Assert.assertEquals("chet.baker@jazz.net", result.getEmail());
		Assert.assertEquals(login, result.getLogin());
		Assert.assertEquals("BAKER", result.getLastname());
		Assert.assertEquals("Chet", result.getFirstname());
	}
}

