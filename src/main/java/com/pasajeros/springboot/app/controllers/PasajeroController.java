package com.pasajeros.springboot.app.controllers;



import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.pasajeros.springboot.app.entity.Pasajero;
import com.pasajeros.springboot.app.models.service.IPasajeroService;
import com.pasajeros.springboot.app.models.service.IUploadFileService;
import com.pasajeros.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("pasajero_nuevo")
public class PasajeroController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IPasajeroService pasajeroService;
	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Pasajero pasajero = pasajeroService.findOne(id);
		if (pasajero == null) {
			flash.addFlashAttribute("error", "El pasajero no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("pasajero", pasajero);
		model.put("titulo", "Detalle pasajero: " + pasajero.getNombre());
		return "ver";
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = new PageRequest(page, 4);

		Page<Pasajero> pasajero = pasajeroService.findAll(pageRequest);

		PageRender<Pasajero> pageRender = new PageRender<Pasajero>("/listar", pasajero);
		model.addAttribute("titulo", "Listado de Pasajeros");
		model.addAttribute("pasajeros", pasajero);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Pasajero pasajero = new Pasajero();
		model.put("pasajero", pasajero);
		model.put("titulo", "Crear Pasajero");
		return "form";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Pasajero pasajero = null;

		if (id > 0) {
			pasajero = pasajeroService.findOne(id);
			if (pasajero == null) {
				flash.addFlashAttribute("error", "El ID del pasajero no existe en la BBDD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del pasajero no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("pasajero", pasajero);
		model.put("titulo", "Editar Pasajero");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Pasajero pasajero, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Pasajero");
			return "form";
		}

		if (!foto.isEmpty()) {

			if (pasajero.getId() != null && pasajero.getId() > 0 && pasajero.getFoto() != null
					&& pasajero.getFoto().length() > 0) {

				uploadFileService.delete(pasajero.getFoto());
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			pasajero.setFoto(uniqueFilename);

		}

		String mensajeFlash = (pasajero.getId() != null) ? "Pasajero editado con éxito!" : "Cliente creado con éxito!";

		pasajeroService.save(pasajero);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Pasajero pasajero = pasajeroService.findOne(id);

			pasajeroService.delete(id);
			flash.addFlashAttribute("success", "Pasajero eliminado con éxito!");

			if (uploadFileService.delete(pasajero.getFoto())) {
				flash.addFlashAttribute("info", "Foto " + pasajero.getFoto() + " eliminada con exito!");
			}

		}
		return "redirect:/listar";
	}
}