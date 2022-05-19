package ru.welcometotheclub.vacanciesparser.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.welcometotheclub.vacanciesparser.models.entity.Vacancy;
import ru.welcometotheclub.vacanciesparser.models.repository.VacancyRepository;

import java.util.List;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    @Autowired
    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public void save(Vacancy vacancy) {
        if (findVacancyById(vacancy.getId()) == null)
            vacancyRepository.save(vacancy);
    }

    public void saveAll(List<Vacancy> vacancies) {
        for (Vacancy vacancy : vacancies)
            save(vacancy);
    }

    public Vacancy findVacancyByName(String name) {
        return vacancyRepository.findVacancyByName(name);
    }

    public Vacancy findVacancyById(Integer id) {
        return vacancyRepository.findVacancyById(id);
    }

    public List<Vacancy> findVacanciesByName(String name) {
        return vacancyRepository.findVacanciesByName(name);
    }
}
