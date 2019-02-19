package com.pasajeros.springboot.app.models.service;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pasajeros.springboot.app.entity.Recepcionista;

import com.pasajeros.springboot.app.models.dao.IRecepcionistaDao;

@Service
public class RecepcionistaServicelmpl implements IRecepcionistaService{
	
	@Autowired
    private IRecepcionistaDao recepcionistaDao;

	@Override
	public List<Recepcionista> findAll() {
		
		return (List<Recepcionista>) recepcionistaDao.findAll();
	}

	@Override
	public Page<Recepcionista> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return recepcionistaDao.findAll(pageable);
	}

	@Override
	public void save(Recepcionista recepcionista) {
		 recepcionistaDao.save(recepcionista);
		
	}

	@Override
	public Recepcionista findOne(Long id) {
		// TODO Auto-generated method stub
		return recepcionistaDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		recepcionistaDao.delete(id);
		
	}

    
    
    

	

	
}
