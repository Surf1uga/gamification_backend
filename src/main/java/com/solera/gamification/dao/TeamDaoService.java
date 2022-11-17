package com.solera.gamification.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.solera.gamification.entity.Team;

@Component
public class TeamDaoService {
	
	private static List<Team> teams = new ArrayList<>();
	private static int counter = 0;
	static {
		teams.add(new Team(++counter,"Team 1"));
		teams.add(new Team(++counter,"Team 2"));
		teams.add(new Team(++counter,"Team 3"));
		teams.add(new Team(++counter,"Team 4"));
		teams.add(new Team(++counter,"Team 5"));
	}
	
	public List<Team> findAll(){
		return teams;
	}
	
	public Team findTeam(int id) {
		Predicate<? super Team> predicate = team -> team.getId().equals(id); 
		  return teams.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public void deleteTeamById(int id) {
		Predicate<? super Team> predicate = team -> team.getId().equals(id); 
		teams.removeIf(predicate);
	}
	
	public Team save(Team team) {
		++counter;
		teams.add(team);
		return team;
	}
	

}
