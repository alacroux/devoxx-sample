package fr.netapsys.devoxx.service.impl;

import java.util.List;

import fr.netapsys.devoxx.repository.AccountInformation;
import fr.netapsys.devoxx.repository.Repository;
import fr.netapsys.devoxx.service.SearchService;

public class SearchServiceImpl 
implements SearchService {
	
	private List<Repository> repositories;
	
	
	public AccountInformation search(final String login)
	{
		for (Repository repository : repositories)  {
			AccountInformation information = repository.search(login);
			if (information.isValid()) {
				return information;
			}
		}
		return new AccountInformation();
	}


	public void setRepositories(List<Repository> repositories) {
		this.repositories = repositories;
	}

}
