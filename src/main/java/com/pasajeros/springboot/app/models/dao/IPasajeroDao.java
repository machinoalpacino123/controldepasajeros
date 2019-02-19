package com.pasajeros.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pasajeros.springboot.app.entity.Habitaciones;
import com.pasajeros.springboot.app.entity.Pasajero;



public interface IPasajeroDao extends PagingAndSortingRepository<Pasajero, Long> {

	List<Habitaciones> findByNombreLikeIgnoreCase(String string);

}
