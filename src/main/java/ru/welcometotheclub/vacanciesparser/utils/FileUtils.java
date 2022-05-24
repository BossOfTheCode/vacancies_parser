package ru.welcometotheclub.vacanciesparser.utils;


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
        for (String s : prepareData(data))
            printWriter.write(s);
        printWriter.close();
    }

    public static void getInfographic(String vacancyName) {
        File python = new File (System.getenv("LOCALAPPDATA") + "/Programs/Python/Python39/python.exe");
        File inputFile = new File("src/main/resources/datasets/" + vacancyName + ".csv");
        File outFile = new File("src/main/resources/static/images/" + vacancyName + ".png");
        File scriptFile = new File("src/main/resources/python/main.py");
        ProcessBuilder processBuilder =
                new ProcessBuilder(python.getAbsolutePath(),
                        scriptFile.getAbsolutePath(),
                        inputFile.getAbsolutePath(),
                        outFile.getAbsolutePath(),
                        "10");
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            process.waitFor();
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
