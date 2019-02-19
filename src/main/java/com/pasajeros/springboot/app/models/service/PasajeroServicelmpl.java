package com.pasajeros.springboot.app.models.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.pasajeros.springboot.app.entity.ArriendoHabitacion;
import com.pasajeros.springboot.app.entity.Habitaciones;

import com.pasajeros.springboot.app.entity.Pasajero;
import com.pasajeros.springboot.app.models.dao.IArriendoHabitacionDao;
import com.pasajeros.springboot.app.models.dao.IHabitacionDao;
import com.pasajeros.springboot.app.models.dao.IPasajeroDao;

@Service
public class PasajeroServicelmpl implements IPasajeroService  {
	
	
     @Autowired
	private IPasajeroDao pasajeroDao;
     
     @Autowired
    private IHabitacionDao habitacionDao;
     
     private IArriendoHabitacionDao arriendohabitacionDao;

     
 	@Override
 	
	public List<Pasajero> findAll() {
		return (List<Pasajero>) pasajeroDao.findAll();
	}
    @Transactional
	@Override
	public Page<Pasajero> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return pasajeroDao.findAll(pageable);
	}
    @Transactional
	@Override
	public void save(Pasajero pasajero) {
		pasajeroDao.save(pasajero);
		
	}
    @Transactional
	@Override
	public Pasajero findOne(Long id) {
		// TODO Auto-generated method stub
		return pasajeroDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
	pasajeroDao.delete(id);
		
	}
    @Transactional
	@Override
	public List<Habitaciones> findByNombre(String term) {
		// TODO Auto-generated method stub
		return pasajeroDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	public void saveArriendoHabitacion(ArriendoHabitacion arriendohabitacion) {
		arriendohabitacionDao.save(arriendohabitacion);
		
	}
    
	@Override
	public Habitaciones findHabitacionesById(Long id) {
		// TODO Auto-generated method stub
		return habitacionDao.findOne(id);
	}

	@Override
	public ArriendoHabitacion findArriendoHabitacionById(Long id) {
		// TODO Auto-generated method stub
		return arriendohabitacionDao.findOne(id);
	}

	@Override
	public void deleteArriendoHabitacion(Long id) {
		arriendohabitacionDao.delete(id);
		
	}
     
    
     
  
	
	
	
	
	

	
	
	



     

     
    
     

     
     

}
