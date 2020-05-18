package com.solutionesone.app.restservice.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.solutionesone.app.restservice.models.entity.Client;

public interface IClientService {
	
	public Page<Client> findPageCliente(Pageable pageable);
	
	public List<Client> findAllClients();
	
	public Client findById(Long id);
	
	public Client createClient(Client client);
	
	public void deleteClient(Long id);
}
