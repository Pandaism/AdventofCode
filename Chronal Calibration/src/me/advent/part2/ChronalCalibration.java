package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Pandaism
 * @date 12/1/2018 : 1:41 PM
 */
public class ChronalCalibration {
    public static void main(String[] args) throws IOException, URISyntaxException {
        File inputFile = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;

        List<Integer> frequencies = new ArrayList<>();
        while((line = reader.readLine()) != null) {
            frequencies.add(Integer.parseInt(line));
        }

        Set<Integer> storage = new HashSet<>();
        int total = 0;
        for(int i = 0; i < frequencies.size(); i++) {
            int num = frequencies.get(i);

            total += num;

            if(storage.contains(total)) {
                break;
            } else {
                storage.add(total);
            }

            //Return to beginning of the list
            if(i == frequencies.size() - 1) {
                i = -1;
            }
        }


        System.out.printf("The answer: %d\n", total);
    }
}
