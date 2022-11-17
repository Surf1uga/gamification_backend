package com.solera.gamification.controller;



import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

import com.bootcamp.soleraproject.exceptions.TeamNotFoundException;
import com.solera.gamification.entity.Project;
import com.solera.gamification.entity.Team;
import com.solera.gamification.jpa.ProjectRepository;
import com.solera.gamification.jpa.TeamRepository;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class WebJpaController {
	
	private TeamRepository userRepository;
	private ProjectRepository postRepository;
	
	private MessageSource messageSource;
	
	public WebJpaController(TeamRepository userRepository,ProjectRepository postRepository, MessageSource messageSource) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.messageSource = messageSource;
	}
	
	@GetMapping(path="/jpa/hello")
	public String helloTeams() {
		return "Hello Teams";
	}

	@GetMapping(path="/jpa/teams")
	public List<Team> retrieveAllTeams(){
		return userRepository.findAll();
	}
	
	
	@GetMapping(path="/jpa/teams/{id}")
	public EntityModel<Team> findTeam(@PathVariable Integer id) {
		
		Optional<Team> team = userRepository.findById(id);
		
		if(team.isEmpty()) {
			throw new TeamNotFoundException("id: " + id);
		}
		
		EntityModel<Team> entityModel = EntityModel.of(team.get());
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllTeams());
		entityModel.add(link.withRel("all-teams"));
		
		return entityModel;
	}
	
	
	@PostMapping("jpa/teams")
	public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) {
		
		Team savedTeam = userRepository.save(team);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedTeam.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path="/jpa/teams/{id}")
	public void deleteTeam(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping(path="/jpa/teams/{id}/projects")
	public List<Project> retrieveTeamProjects(@PathVariable Integer id) {
		Optional<Team> team = userRepository.findById(id);
		
		if(team.isEmpty()) {
			throw new TeamNotFoundException("id: " + id);
		}
		
		return team.get().getProjects();
	}
	
	@PostMapping(path="/jpa/teams/{id}/projects")
	public ResponseEntity<Object> createProjectForTeam(@PathVariable int id, @Valid @RequestBody Project project) {
		Optional<Team> team = userRepository.findById(id);
		
		if(team.isEmpty()) {
			throw new TeamNotFoundException("id: " + id);
		}
		
		project.setTeam(team.get());
		
		Project savedProject = postRepository.save(project);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedProject.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@DeleteMapping(path="/jpa/teams/{id}/projects/{projectId}")
	public void deleteProject(@PathVariable Integer id,@PathVariable Integer projectId) {
		Optional<Team> team = userRepository.findById(id);
		
		if(team.isEmpty()) {
			throw new TeamNotFoundException("id: " + id);
		}
		
		List <Project> teamProjects = team.get().getProjects();
		
		for(Project elem : teamProjects) {
			if(elem.getId().equals(projectId)) {
				postRepository.delete(elem);
			}
		}
		
	}
	
	
	
	@GetMapping(path="/jpa/hello-internationalized")
	public String helloTeamsInternationalized() {
		
		Locale locale = 
				LocaleContextHolder.getLocale();
		
		return messageSource.getMessage("good.morning.message", null,"Default Message", locale);
	}
}
