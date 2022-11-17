package com.solera.gamification.controller;



import java.net.URI;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.solera.gamification.dao.TeamDaoService;
import com.solera.gamification.entity.Team;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class WebController {
	
	private TeamDaoService service;
	
	private MessageSource messageSource;
	
	public WebController(TeamDaoService teamDaoService, MessageSource messageSource) {
		this.service = teamDaoService;
		this.messageSource = messageSource;
	}
	
	@GetMapping(path="/hello")
	public String helloTeams() {
		return "Hello Teams";
	}

	@GetMapping(path="/teams")
	public List<Team> retrieveAllTeams(){
		return service.findAll();
	}
	
	@GetMapping(path="/teams/{id}")
	public EntityModel<Team> findTeam(@PathVariable Integer id) {
		
		Team team = service.findTeam(id);
		
		EntityModel<Team> entityModel = EntityModel.of(team);
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllTeams());
		entityModel.add(link.withRel("all-teams"));
		
		return entityModel;
	}
	
	
	@PostMapping("teams")
	public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) {
		
		Team savedTeam = service.save(team);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedTeam.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path="/teams/{id}")
	public void deleteTeam(@PathVariable Integer id) {
		service.deleteTeamById(id);
	}
	
	@GetMapping(path="/hello-internationalized")
	public String helloTeamsInternationalized() {
		
		Locale locale = 
				LocaleContextHolder.getLocale();
		
		return messageSource.getMessage("good.morning.message", null,"Default Message", locale);
	}
}
