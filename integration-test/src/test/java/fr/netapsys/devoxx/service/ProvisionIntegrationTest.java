package fr.netapsys.devoxx.service;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.KarafDistributionOption;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.osgi.framework.BundleException;

import fr.netapsys.devoxx.repository.AccountInformation;

@RunWith(PaxExam.class)

public class ProvisionIntegrationTest {
	
	@Inject 
	SearchService service;

	@Configuration
	public Option[] config() {
		MavenArtifactUrlReference karafUrl = CoreOptions.maven()
				.groupId("org.apache.karaf").artifactId("apache-karaf")
				.version("3.0.0").type("tar.gz");

		MavenUrlReference repositoryMongo = CoreOptions.maven()
				.groupId("fr.netapsys.devoxx").artifactId("repository-mongo")
				.classifier("features").version("1.0.0").type("xml");

		MavenUrlReference repositoryLdap = CoreOptions.maven()
				.groupId("fr.netapsys.devoxx").artifactId("repository-ldap")
				.classifier("features").version("1.0.0").type("xml");

		MavenUrlReference provision = CoreOptions.maven()
				.groupId("fr.netapsys.devoxx").artifactId("provision")
				.classifier("features").version("1.0.0").type("xml");

		return new Option[] {
				KarafDistributionOption.karafDistributionConfiguration()
						.frameworkUrl(karafUrl)
						.unpackDirectory(new File("target/pax"))
						.useDeployFolder(false),
				KarafDistributionOption.keepRuntimeFolder(),
				KarafDistributionOption.configureConsole().ignoreLocalConsole(),
				KarafDistributionOption.logLevel(LogLevel.INFO),
				KarafDistributionOption.features(repositoryLdap, "fr.netapsys.devoxx.ldapService"),
				KarafDistributionOption.features(repositoryMongo, "fr.netapsys.devoxx.mongoService"),
				KarafDistributionOption.features(provision, "fr.netapsys.devoxx.provision"),
		};
	}

	
	@Test
	public void testBundle() throws IOException, BundleException {
		
		Assert.assertNotNull(service);
		String login = "chet.baker";
		AccountInformation result = service.search(login);
		Assert.assertEquals("chet.baker@jazz.net", result.getEmail());
		Assert.assertEquals(login, result.getLogin());
		Assert.assertEquals("BAKER", result.getLastname());
		Assert.assertEquals("Chet", result.getFirstname());
		
		login = "miles.davis";
		result = service.search(login);
		Assert.assertEquals("miles.davis@jazz.net", result.getEmail());
		Assert.assertEquals(login, result.getLogin());
		Assert.assertEquals("DAVIS", result.getLastname());
		Assert.assertEquals("Miles", result.getFirstname());
	}

}
