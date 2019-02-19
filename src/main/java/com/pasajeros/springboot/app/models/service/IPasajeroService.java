package com.pasajeros.springboot.app.models.service;




import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.pasajeros.springboot.app.entity.ArriendoHabitacion;
import com.pasajeros.springboot.app.entity.Habitaciones;
import com.pasajeros.springboot.app.entity.Pasajero;


public interface IPasajeroService {
	
	

		
public List<Pasajero> findAll();
	
	public Page<Pasajero> findAll(Pageable pageable);

	public void save(Pasajero pasajero);
	
	public Pasajero findOne(Long id);
	
	public void delete(Long id);
	
	public List<Habitaciones> findByNombre(String term);
	
	public void saveArriendoHabitacion(ArriendoHabitacion arriendohabitacion);
	
	public Habitaciones findHabitacionesById(Long id);
	
	public ArriendoHabitacion findArriendoHabitacionById(Long id);
	
	public void deleteArriendoHabitacion(Long id);
		

	
	
	
	


}

	

	

	
