package com.pasajeros.springboot.app.controllers;



import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.pasajeros.springboot.app.entity.Recepcionista;
import com.pasajeros.springboot.app.models.service.IRecepcionistaService;
import com.pasajeros.springboot.app.util.paginator.PageRender;

@SessionAttributes("recepcionista")
@Controller
public class RecepcionistaController {
	
	private IRecepcionistaService recepcionistaService;

	
	
	@GetMapping(value = "/recepcionista/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Recepcionista recepcionista = recepcionistaService.findOne(id);
		if (recepcionista == null) {
			flash.addFlashAttribute("error", "El hospedaje no existe en la base de datos");
			return "redirect:/recepcionista/listar";
		}

		model.put("recepcionista", recepcionista);
		model.put("titulo", "Detalle recepcion : " + recepcionista.getNombre());
		return "casas/ver";
	}

	@RequestMapping(value = "/recepcionista/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = new PageRequest(page, 4);

		Page<Recepcionista> recepcionista = recepcionistaService.findAll(pageRequest);

		PageRender<Recepcionista> pageRender = new PageRender<Recepcionista>("/recepcionista/listar", recepcionista);
		model.addAttribute("titulo", "Listado de recepcionistas");
		model.addAttribute("casa", recepcionista);
		model.addAttribute("page", pageRender);
		return "recepcionista/listar";
	}
	@RequestMapping(value = "/recepcionista/form")
	public String crear(Map<String, Object> model) {

		Recepcionista recepcionista = new Recepcionista();
		model.put("casas", recepcionista);
		model.put("titulo", "Crear recepcion");
		return "recepcionista/form";
	}
	
	@RequestMapping(value="/recepcionista/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Recepcionista recepcionista = null;
		
		if(id > 0) {
			recepcionista = recepcionistaService.findOne(id);
			if(recepcionista == null) {
				flash.addFlashAttribute("error", "El ID del recepcionista no existe en la BBDD!");
				return "redirect:recepcionista/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la casa no puede ser cero!");
			return "redirect:/recepcionista/Listar";
		}
		model.put("casas", recepcionista);
		model.put("titulo", "Editar Recepcionista");
		return "recepcionista/form";
		
	}
	@RequestMapping(value = "/recepcionista/form", method = RequestMethod.POST)
	public String guardar(@Valid Recepcionista recepcionista, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Recepcionistas");
			return "recepcionista/form";
		
	}
	
	String mensajeFlash = (recepcionista.getId() != null)? "Recepcionista editado con éxito!" : "recepcionista creado con éxito!";

	recepcionistaService.save(recepcionista);
	status.setComplete();
	flash.addFlashAttribute("success", mensajeFlash);
	return "redirect:/recepcionista/listar";
}
	@RequestMapping(value="/recepcionista/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			Recepcionista recepcionista = recepcionistaService.findOne(id);

			recepcionistaService.delete(id);
			flash.addFlashAttribute("success", "Recepcionista eliminado con éxito!");
			
				}
		
		return "redirect:/recepcionista/listar";
	}

  
}