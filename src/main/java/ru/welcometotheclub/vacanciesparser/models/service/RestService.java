package ru.welcometotheclub.vacanciesparser.models.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.welcometotheclub.vacanciesparser.models.entity.Skill;
import ru.welcometotheclub.vacanciesparser.models.entity.Vacancy;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Vacancy> getVacanciesByNameFromHH(String name) throws JsonProcessingException {
        String url = "https://api.hh.ru/vacancies/?";
        List<Vacancy> vacancies = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int page = 1; page < 20; page++) {
            String json = this.restTemplate.getForObject(url + "text=NAME:" + name + "&page=" + page + "&per_page=" + 25, String.class);
            JsonNode jsonNode = objectMapper.readTree(json).get("items");
            if (jsonNode != null && jsonNode.isArray())
                for (JsonNode innerNode : jsonNode) {
                    Integer vacancyId = innerNode.get("id").asInt();
                    String vacancyName = innerNode.get("name").asText();
                    String companyName = innerNode.get("employer").get("name").asText();
                    JsonNode salaryNode = innerNode.get("salary");
                    Integer vacancyMinSalary;
                    Integer vacancyMaxSalary;
                    if (salaryNode.toString().equals("null")) {
                        vacancyMinSalary = null;
                        vacancyMaxSalary = null;
                    } else {
                        vacancyMinSalary = salaryNode.get("from").toString().equals("null") ? null : salaryNode.get("from").asInt();
                        vacancyMaxSalary = salaryNode.get("to").toString().equals("null") ? null : salaryNode.get("to").asInt();
                    }
                    Vacancy vacancy = new Vacancy();
                    vacancy.setId(vacancyId);
                    vacancy.setName(vacancyName);
                    vacancy.setMinimalSalary(vacancyMinSalary);
                    vacancy.setMaximalSalary(vacancyMaxSalary);
                    vacancy.setCompanyName(companyName);
                    vacancies.add(vacancy);
                }
        }
        return vacancies;
    }

    public List<Skill> findSkillsOfVacancyById(Integer vacancyId) throws JsonProcessingException {
        String url = String.format("https://api.hh.ru/vacancies/%d?host=hh.ru", vacancyId);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Skill> skills = new ArrayList<>();
        String json = this.restTemplate.getForObject(url, String.class);
        JsonNode jsonNode = objectMapper.readTree(json).get("key_skills");
        if (jsonNode != null && jsonNode.isArray())
            for (JsonNode innerNode : jsonNode) {
                Skill skill = new Skill();
                skill.setName(innerNode.get("name").asText());
                skills.add(skill);
            }
        return skills;
    }


}
