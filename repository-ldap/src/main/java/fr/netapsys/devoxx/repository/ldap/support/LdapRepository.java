package fr.netapsys.devoxx.repository.ldap.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

import fr.netapsys.devoxx.repository.AccountInformation;
import fr.netapsys.devoxx.repository.Repository;

public class LdapRepository implements Repository {

	private final Logger log = LoggerFactory.getLogger(LdapRepository.class);

	private String host = "";
	private int port = 389;
	private String baseDNSearch = "";

	protected LDAPConnection getConnection() throws LDAPException {
		LDAPConnection connection = new LDAPConnection(getHost(), getPort());
		return connection;
	}

	@Override
	public AccountInformation search(String login) {
		LDAPConnection connection = null;
		try {
			connection = getConnection();
			String filter = String.format("(uid=%s)", login);
			SearchResultEntry search = connection.searchForEntry( getBaseDNSearch(), SearchScope.SUB,
																  filter, "sn", "givenName", "mail");
			AccountInformation result = new AccountInformation();
			if (search == null) {
				return result;
			}
			result.setLastname(search.getAttributeValue("sn"));
			result.setEmail(search.getAttributeValue("mail"));
			result.setLogin(login);
			result.setFirstname(search.getAttributeValue("givenName"));
			return result;
		} catch (LDAPException exception) {
			log.warn("error:{}", exception.getMessage());
			return new AccountInformation();
		} finally {
			connection.close();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getBaseDNSearch() {
		return baseDNSearch;
	}

	public void setBaseDNSearch(String baseDNSearch) {
		this.baseDNSearch = baseDNSearch;
	}

	public Logger getLog() {
		return log;
	}

}
