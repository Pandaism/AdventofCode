package me.advent.part1;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * @author Pandaism
 * @date 12/1/2018 : 1:21 PM
 */
public class ChronalCalibration {

    public static void main(String[] args) throws IOException, URISyntaxException {
        File inputFile = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;

        int total = 0;

        while((line = reader.readLine()) != null) {
            total += Integer.parseInt(line);
        }

        System.out.printf("The answer: %d\n", total);
    }
}
