package ru.welcometotheclub.vacanciesparser.models.service;

import org.springframework.stereotype.Service;
import ru.welcometotheclub.vacanciesparser.models.entity.Skill;
import ru.welcometotheclub.vacanciesparser.models.repository.SkillRepository;

@Service
public class SkillService {

    private SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill save(Skill skill) {
        Skill foundSkill = findSkillByName(skill.getName());
        if (foundSkill == null) {
            skillRepository.save(skill);
            return findSkillByName(skill.getName());
        }
        else
            return foundSkill;
    }

    public Skill findSkillByName(String name) {
        return skillRepository.findSkillByName(name);
    }
}
