package com.pasajeros.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pasajeros.springboot.app.entity.Recepcionista;




public interface IRecepcionistaService {
	
	
public List<Recepcionista> findAll();
	
	public Page<Recepcionista> findAll(Pageable pageable);

	public void save(Recepcionista recepcionista);
	
	public Recepcionista findOne(Long id);
	
	public void delete(Long id);
	



	}



