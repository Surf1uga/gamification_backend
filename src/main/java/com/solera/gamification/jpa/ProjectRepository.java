package com.solera.gamification.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solera.gamification.entity.Project;


public interface ProjectRepository extends JpaRepository<Project,Integer>{

}
