package com.solutionesone.app.restservice.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.solutionesone.app.restservice.models.IClientDao;
import com.solutionesone.app.restservice.models.entity.Client;
import com.solutionesone.app.restservice.services.IClientService;

@Service
public class ClientService implements IClientService {
	
	@Autowired
	private IClientDao clientDao;

	@Override
	public Page<Client> findPageCliente(Pageable pageable) {
		return clientDao.findAll(pageable);
	}

	@Override
	public List<Client> findAllClients() {
		return clientDao.findAll();
	}

	@Override
	public Client findById(Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	public Client createClient(Client client) {
		return clientDao.save(client);
	}

	@Override
	public void deleteClient(Long id) {
		clientDao.deleteById(id);
	}
}
