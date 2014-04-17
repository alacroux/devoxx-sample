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

public class ProvisionNoRepositoryTest {
	
	@Inject 
	SearchService service;

	@Configuration
	public Option[] config() {
		MavenArtifactUrlReference karafUrl = CoreOptions.maven()
				.groupId("org.apache.karaf").artifactId("apache-karaf")
				.version("3.0.0").type("tar.gz");

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
				KarafDistributionOption.features(provision, "fr.netapsys.devoxx.provision"),
		};
	}

	
	@Test
	public void testBundle() throws IOException, BundleException {
		
		Assert.assertNotNull(service);
		String login = "chet.backer";
		AccountInformation result = service.search(login);
		Assert.assertTrue(!result.isValid());
	
		login = "miles.davis";
		result = service.search(login);
		Assert.assertTrue(!result.isValid());
	}

}
