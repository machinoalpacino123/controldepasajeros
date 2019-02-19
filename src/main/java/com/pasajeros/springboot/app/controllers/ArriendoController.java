package com.pasajeros.springboot.app.controllers;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pasajeros.springboot.app.entity.ArriendoHabitacion;
import com.pasajeros.springboot.app.entity.Habitaciones;
import com.pasajeros.springboot.app.entity.ItemHabitaciones;
import com.pasajeros.springboot.app.entity.Pasajero;
import com.pasajeros.springboot.app.models.service.IPasajeroService;



@Controller
@RequestMapping("/arriendohabitacion")
@SessionAttributes("arriendohabitacion")
public class ArriendoController {
	
	@Autowired
	private IPasajeroService pasajeroService;
	
	
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		ArriendoHabitacion arriendohabitacion = pasajeroService.findArriendoHabitacionById(id);

		if (arriendohabitacion == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos!");
			return "redirect:/listar";
		}

		model.addAttribute("arriendohabitacion", arriendohabitacion);
		model.addAttribute("titulo", "arriendohabitacion: ".concat(arriendohabitacion.getObservacion()));
		return "arriendohabitacion/ver";
	}

	@GetMapping("/form/{pasajeroId}")
	public String crear(@PathVariable(value = "pasajeroId") Long pasajeroId, Map<String, Object> model,
			RedirectAttributes flash) {

		Pasajero pasajero = pasajeroService.findOne(pasajeroId);

		if (pasajero == null) {
			flash.addFlashAttribute("error", "el pasajero no existe en la base de datos");
			return "redirect:/listar";
		}

		ArriendoHabitacion arriendohabitacion = new ArriendoHabitacion ();
		arriendohabitacion.setPasajero(pasajero);

		model.put("arriendohabitacion", arriendohabitacion);
		model.put("titulo", "Crear Arriendo de Habitacion");

		return "arriendohabitacion/form";
	}

	@GetMapping(value = "/cargar-Habitaciones/{term}", produces = { "application/json" })
	public @ResponseBody List<Habitaciones> cargarHabitaciones(@PathVariable String term) {
		return pasajeroService.findByNombre(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid ArriendoHabitacion arriendohabitacion, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear ArriendoHabitacion");
			return "arriendohabitacion/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error", "Error: La factura NO puede no tener líneas!");
			return "arriendohabitacion/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Habitaciones habitaciones = pasajeroService.findHabitacionesById(itemId[i]);

			ItemHabitaciones linea = new ItemHabitaciones();
			linea.setCantidad(cantidad[i]);
			linea.setHabitaciones(habitaciones);
			arriendohabitacion.addItemHabitaciones(linea);

			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}

		pasajeroService.saveArriendoHabitacion(arriendohabitacion);
		status.setComplete();

		flash.addFlashAttribute("success", "Factura creada con éxito!");

		return "redirect:/ver/" + arriendohabitacion.getPasajero().getId();
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		ArriendoHabitacion arriendohabitacion = pasajeroService.findArriendoHabitacionById(id);

		if (arriendohabitacion != null) {
			pasajeroService.deleteArriendoHabitacion(id);
			flash.addFlashAttribute("success", "Arriendo de Habitacion eliminado con éxito!");
			return "redirect:/ver/" + arriendohabitacion.getPasajero().getId();
		}
		flash.addFlashAttribute("error", "el arriendo no existe en la base de datos, no se pudo eliminar!");

		return "redirect:/listar";
	}

}
