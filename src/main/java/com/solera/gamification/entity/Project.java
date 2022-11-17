package com.solera.gamification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Project {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=2)
	private String name;
	
	private int score;
	
	@ManyToOne(
			fetch=FetchType.LAZY)
	@JsonIgnore
	private Team team;
	
	public Project() {
		super();
	}
	
	public Project(Integer id, String name, int score) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", score=" + score + "]";
	}
	
}
