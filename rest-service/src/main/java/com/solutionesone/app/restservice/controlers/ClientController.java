package com.solutionesone.app.restservice.controlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutionesone.app.restservice.models.entity.Client;
import com.solutionesone.app.restservice.services.impl.ClientService;

@RestController
@RequestMapping("/api")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@GetMapping("/clients")
	public List<Client> list() {
		return clientService.findAllClients();
	}
	
	@GetMapping("/clients/page/{page}")
	public Page<Client> findPage(@PathVariable(name = "page")Integer page){
		return clientService.findPageCliente(PageRequest.of(page, 5));
	}
	
	@GetMapping("clients/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id")Long id) {
		
		Map<String, Object> response = new HashMap<>(); 
		Client client = clientService.findById(id);
		
		if( client == null ) {
			response.put("mensaje", "El registro que intenta buscar no existe");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			response.put("client", client);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar recuparar registro");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/clients")
	public ResponseEntity<?> createClient(@RequestBody Client client){
		
		Client clientNew = null;
		Map<String, Object> response = new HashMap<>(); 
		
		try {
			clientNew = clientService.createClient(client);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar almacenar registro");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("client",clientNew);
		response.put("mensaje", "Registro almacenado satisfactoriamente");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/clients/{id}")
	public ResponseEntity<?> createClient(@RequestBody Client client, @PathVariable(name = "id") Long id){
		
		Map<String, Object> response = new HashMap<>(); 
		Client oldClient = clientService.findById(id);
		
		if( client == null ) {
			response.put("mensaje", "El registro que intenta buscar no existe");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			oldClient.setFirstName(client.getFirstName());
			oldClient.setLastName(client.getLastName());
			oldClient.setEmail(client.getEmail());
			oldClient.setCreated(client.getCreated());
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar almacenar registro");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("client",clientService.createClient(oldClient));
		response.put("mensaje", "Registro actualizado satisfactoriamente");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/clients/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable(name = "id")Long id){
		
		Map<String, Object> response = new HashMap<>(); 
		Client client = clientService.findById(id);
		
		if( client == null ) {
			response.put("mensaje", "El registro que intenta buscar no existe");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			clientService.deleteClient(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar eliminar registro");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Registro eliminado satisfactoriamente");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
