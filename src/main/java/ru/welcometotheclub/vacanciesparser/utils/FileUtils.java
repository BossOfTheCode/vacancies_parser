package ru.welcometotheclub.vacanciesparser.utils;

import ru.welcometotheclub.vacanciesparser.models.entity.Skill;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {

    public static void saveToCSV(String filename, HashMap<String, Integer> data) throws IOException {
        File csvOutputFile = new File("src/main/resources/datasets/" + filename + ".csv");
        if (!csvOutputFile.exists())
            csvOutputFile.createNewFile();
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(csvOutputFile), StandardCharsets.UTF_8));
        printWriter.write("Навык;Частота\n");
        for (String s : prepareData(data))
            printWriter.write(s);
        printWriter.close();
    }

    public static void getInfographic(String vacancyName) {
        String inputFileName = "C:/Users/ching/IdeaProjects/vacancies_parser/src/main/resources/python/" + vacancyName + ".csv";
        String outputFileName = "C:/Users/ching/IdeaProjects/vacancies_parser/src/main/resources/python/" + vacancyName + ".png";
        ProcessBuilder processBuilder = new ProcessBuilder("python", "C:/Users/ching/IdeaProjects/vacancies_parser/src/main/resources/python/main.py", inputFileName, outputFileName, "10");
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            System.out.println(process.waitFor());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<String> prepareData(HashMap<String, Integer> data) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> preparedData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            stringBuilder.append(entry.getKey()).append(";").append(entry.getValue()).append("\n");
            preparedData.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return preparedData;
    }
}
