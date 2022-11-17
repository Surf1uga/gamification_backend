package com.solera.gamification.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solera.gamification.entity.Team;

public interface TeamRepository extends JpaRepository<Team,Integer>{

}
