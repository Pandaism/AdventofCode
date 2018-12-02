package me.advent.part1;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pandaism
 * @date 12/2/2018 : 3:28 PM
 */
public class IMS {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        int doubles = 0;
        int triples = 0;

        while ((line = reader.readLine()) != null) {
            Map<Character, Integer> duplications = new HashMap<>();

            for(int i = 0; i < line.length(); i++) {
                char character = line.charAt(i);

                if(duplications.containsKey(character)) {
                    int value = duplications.get(character);

                    duplications.put(character, value + 1);
                } else {
                    duplications.put(character, 1);
                }
            }

            if(duplications.containsValue(2)) {
                doubles++;
            }

            if(duplications.containsValue(3)) {
                triples++;
            }
        }

        System.out.println(doubles * triples);
    }
}
