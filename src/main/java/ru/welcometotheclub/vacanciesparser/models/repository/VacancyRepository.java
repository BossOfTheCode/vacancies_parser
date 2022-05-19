package ru.welcometotheclub.vacanciesparser.models.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.welcometotheclub.vacanciesparser.models.entity.Vacancy;

import java.util.List;

@Transactional
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    Vacancy findVacancyByName(String name);
    Vacancy findVacancyById(Integer id);
    List<Vacancy> findVacanciesByName(String name);
}
