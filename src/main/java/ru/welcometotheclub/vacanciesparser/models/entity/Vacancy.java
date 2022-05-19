package ru.welcometotheclub.vacanciesparser.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vacancies")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Vacancy {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer minimalSalary;
    @Column
    private Integer maximalSalary;
    @Column
    private String companyName;
    @ManyToMany
    @JoinTable(name = "vacancies_skills",
            joinColumns = {@JoinColumn(name = "vacancy_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")})
    @ToString.Exclude
    @JsonManagedReference
    List<Skill> skills = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vacancy vacancy = (Vacancy) o;

        if (!id.equals(vacancy.id)) return false;
        if (!name.equals(vacancy.name)) return false;
        if (minimalSalary != null ? !minimalSalary.equals(vacancy.minimalSalary) : vacancy.minimalSalary != null)
            return false;
        if (maximalSalary != null ? !maximalSalary.equals(vacancy.maximalSalary) : vacancy.maximalSalary != null)
            return false;
        return companyName != null ? companyName.equals(vacancy.companyName) : vacancy.companyName == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (minimalSalary != null ? minimalSalary.hashCode() : 0);
        result = 31 * result + (maximalSalary != null ? maximalSalary.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        return result;
    }
}
