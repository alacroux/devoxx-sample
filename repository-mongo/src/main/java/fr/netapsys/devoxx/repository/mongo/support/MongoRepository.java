package fr.netapsys.devoxx.repository.mongo.support;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import fr.netapsys.devoxx.repository.AccountInformation;
import fr.netapsys.devoxx.repository.Repository;

public class MongoRepository implements Repository {
	
	private final Logger log = LoggerFactory.getLogger(MongoRepository.class);
	
	private String host = "";
	private int port = 27017;
	private String database = "";
	
	public DBCollection getCollection(String collection) throws UnknownHostException
	{
		MongoClient client = new MongoClient(new ServerAddress(getHost(), getPort()));
		DB database = client.getDB(getDatabase());
		return database.getCollection(collection);
	}
	
	public AccountInformation search(String login) {
		DBCollection collection;
		try {
			collection = getCollection("users");
			DBObject query = new BasicDBObject("username", login);
			DBObject document = collection.findOne(query);
			if (document == null) {
				return new AccountInformation();
			}
			AccountInformation result = new AccountInformation();
			result.setEmail(document.get("mail").toString());
			result.setFirstname(document.get("firstname").toString());
			result.setLastname(document.get("lastname").toString());
			result.setLogin(login);
			return result;
		} catch (UnknownHostException exception) {
			log.error("error : {}", exception.getMessage());
			return new AccountInformation();
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

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
