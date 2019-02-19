package com.pasajeros.springboot.app.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




@Entity
@Table(name = "habitaciones_item")
public class ItemHabitaciones implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private Integer cantidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="habitaciones_id")
	private Habitaciones habitaciones;

	public Double calcularImporte() {

		return cantidad.doubleValue() * habitaciones.getPrecio();

	}
	
	public ItemHabitaciones() {
	}


	public Habitaciones getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(Habitaciones habitaciones) {
		this.habitaciones = habitaciones;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	private static final long serialVersionUID = 1L;
}

	