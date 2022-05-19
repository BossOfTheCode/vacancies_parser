package ru.welcometotheclub.vacanciesparser.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.welcometotheclub.vacanciesparser.models.entity.Skill;
import ru.welcometotheclub.vacanciesparser.models.entity.Vacancy;
import ru.welcometotheclub.vacanciesparser.models.service.RestService;
import ru.welcometotheclub.vacanciesparser.models.service.SkillService;
import ru.welcometotheclub.vacanciesparser.models.service.VacancyService;

import java.util.List;

@Controller
public class AppController {

    private final RestService restService;
    private final VacancyService vacancyService;
    private final SkillService skillService;

    @Autowired
    public AppController(RestService restService, VacancyService vacancyService, SkillService skillService) {
        this.restService = restService;
        this.vacancyService = vacancyService;
        this.skillService = skillService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("vacancyName", "");
        return "index";
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<List<Vacancy>> analyseVacancyByName(@RequestParam String vacancyName) throws JsonProcessingException {
        List<Vacancy> vacancies = vacancyService.findVacanciesByName(vacancyName);
        if (vacancies.size() == 0) {
            vacancies = restService.getVacanciesByNameFromHH(vacancyName);
            for (Vacancy vacancy : vacancies) {
                List<Skill> skillsOfVacancy = restService.findSkillsOfVacancyById(vacancy.getId());
                for (int i = 0; i < skillsOfVacancy.size(); i++)
                    skillsOfVacancy.set(i, skillService.save(skillsOfVacancy.get(i)));
                vacancy.setSkills(skillsOfVacancy);
            }
            vacancyService.saveAll(vacancies);
        }
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }
}
