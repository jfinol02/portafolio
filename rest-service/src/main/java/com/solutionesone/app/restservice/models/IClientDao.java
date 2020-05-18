package com.solutionesone.app.restservice.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solutionesone.app.restservice.models.entity.Client;

public interface IClientDao extends JpaRepository<Client, Long> {

}
