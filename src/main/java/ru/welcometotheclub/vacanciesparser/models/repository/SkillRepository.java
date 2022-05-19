package ru.welcometotheclub.vacanciesparser.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.welcometotheclub.vacanciesparser.models.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Skill findSkillByName(String name);
}
