package ru.welcometotheclub.vacanciesparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.welcometotheclub.vacanciesparser.utils.FileUtils;

@SpringBootApplication
public class VacanciesParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(VacanciesParserApplication.class, args);
        FileUtils.getInfographic("Python");
    }

}
