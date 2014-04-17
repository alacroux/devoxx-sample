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
import fr.netapsys.devoxx.repository.Repository;

@RunWith(PaxExam.class)
public class RepositoryMongoIntegrationTest {
	
	@Inject 
	Repository service;

	@Configuration
	public Option[] config() {
		MavenArtifactUrlReference karafUrl = CoreOptions.maven()
				.groupId("org.apache.karaf").artifactId("apache-karaf")
				.version("3.0.0").type("tar.gz");

		MavenUrlReference karafStandardRepo = CoreOptions.maven()
				.groupId("fr.netapsys.devoxx").artifactId("repository-mongo")
				.classifier("features").version("1.0.0").type("xml");

		return new Option[] {
				KarafDistributionOption.karafDistributionConfiguration()
						.frameworkUrl(karafUrl)
						.unpackDirectory(new File("target/pax"))
						.useDeployFolder(false),
				KarafDistributionOption.keepRuntimeFolder(),
				KarafDistributionOption.configureConsole().ignoreLocalConsole(),
				KarafDistributionOption.logLevel(LogLevel.INFO),
				KarafDistributionOption.features(karafStandardRepo, "fr.netapsys.devoxx.mongoService"),
				
		};
	}

	
	@Test
	public void testBundle() throws IOException, BundleException {
		String login = "miles.davis";
		AccountInformation result = service.search(login);
		Assert.assertEquals("miles.davis@jazz.net", result.getEmail());
		Assert.assertEquals(login, result.getLogin());
		Assert.assertEquals("DAVIS", result.getLastname());
		Assert.assertEquals("Miles", result.getFirstname());

	}

}
