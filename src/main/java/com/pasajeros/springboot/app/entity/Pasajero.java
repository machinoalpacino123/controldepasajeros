package com.pasajeros.springboot.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;





@Entity
@Table(name="pasajeros")
public class Pasajero implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size (min=4, max=20)
     private String nombre;
	

	
	@NotEmpty
	@Size (min=4, max=20)
	private String apellido;
	
	

	@NotEmpty
	private String nacionalidad;
	

	
	
	
	@NotNull
	@Column(name = "createAt")
	@DateTimeFormat(pattern="yyyy-MM-dd-")
	private Date createAt;
	

	
	

	@OneToMany(mappedBy = "pasajero", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArriendoHabitacion> arriendohabitacion;
	
	
	
	private String foto;

	public Pasajero() {
		arriendohabitacion = new ArrayList<ArriendoHabitacion>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	

	


	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}




	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<ArriendoHabitacion> getArriendohabitacion() {
		return arriendohabitacion;
	}

	public void setArriendohabitacion(List<ArriendoHabitacion> arriendohabitacion) {
		this.arriendohabitacion = arriendohabitacion;
	}

	@Override
	public String toString() {
		return nombre + " " + apellido;
	}

	
	
	
	
}
