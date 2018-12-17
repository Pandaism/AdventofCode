package me.advent.part1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Pandaism
 * @date 12/14/2018 : 3:11 PM
 */
public class SubterraneanSustainability {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String initial = reader.readLine();

        StringBuilder input = new StringBuilder(initial.substring(initial.indexOf(":") + 2));

        Map<String, String> notes = new HashMap<>();
        String line;
        while((line = reader.readLine()) != null) {
            if(!line.isEmpty()) {
                String test = line.substring(0, line.indexOf("=")).trim();
                String result = line.substring(line.indexOf(">") + 1).trim();
                notes.put(test, result);
            }
        }

        long counter = 1;
        int zeroLocation = 0;

        while(counter < 21) {
            int length = input.length();
            StringBuilder nextGen = new StringBuilder();

            for(int center = 0; center < input.length(); center++) {
                if(center == length + 2) {
                    break;
                }

                if(center - 2 < 0) {
                    nextGen.insert(0, ".");
                    input.insert(0, ".");
                    zeroLocation++;
                    continue;
                }

                if(center + 2 == input.length()) {
                    input.insert(input.length(), ".");
                }

                String test = input.substring(center - 2, center) + input.substring(center, center + 3);
                if(notes.containsKey(test)) {
                    if(notes.get(test).equals("#")) {
                        nextGen.insert(center,"#");
                    } else {
                        nextGen.insert(center,".");
                    }
                }
            }

            nextGen.append(".").append(".");
            input = nextGen;

            counter++;
        }

        int startIndex = zeroLocation - (input.indexOf("#") - 1);
        input = new StringBuilder(input.substring(zeroLocation - startIndex));

        long total = 0L;
        for(int i = 0; i < input.length(); i++) {
            int value = i - startIndex;

            if(input.charAt(i) == '#') {
                total += value;
            }

            if(!input.substring(i, input.length()).contains("#")) {
                break;
            }
        }

        System.out.println(total);
    }
}
