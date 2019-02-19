package com.pasajeros.springboot.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;








@Entity
@Table(name = "arriendo_habitacion")
public class ArriendoHabitacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nhabitacion;
	private String observacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private Pasajero pasajero;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "arriendohabitacion_id")
	private List<ItemHabitaciones> items;

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	
	
	public Double getTotal() {
		Double total= 0.0;
		int size = items.size();
		for (int i=0; i< size ; i++) {
			total += items.get(i).calcularImporte();
			
		}
		
		return total;
		
		
		
	}
	public ArriendoHabitacion(Long id, String nhabitacion, String observacion, Date createAt, Pasajero pasajero,
			List<ItemHabitaciones> items) {
		super();
		this.id = id;
		this.nhabitacion = nhabitacion;
		this.observacion = observacion;
		this.createAt = createAt;
		this.pasajero = pasajero;
		this.items = items;
	}


	public void addItemHabitaciones(ItemHabitaciones item) {
		this.items.add(item);
		
	}

	public ArriendoHabitacion() {
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNhabitacion() {
		return nhabitacion;
	}



	public void setNhabitacion(String nhabitacion) {
		this.nhabitacion = nhabitacion;
	}



	public String getObservacion() {
		return observacion;
	}



	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}



	public Date getCreateAt() {
		return createAt;
	}



	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}



	public Pasajero getPasajero() {
		return pasajero;
	}



	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}



	public List<ItemHabitaciones> getItems() {
		return items;
	}



	public void setItems(List<ItemHabitaciones> items) {
		this.items = items;
	}

	


	
	
	
   

}


	
