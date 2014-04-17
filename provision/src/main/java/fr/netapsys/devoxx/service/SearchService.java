package fr.netapsys.devoxx.service;

import fr.netapsys.devoxx.repository.AccountInformation;

public interface SearchService {
	
	AccountInformation search(String login);
	
}
