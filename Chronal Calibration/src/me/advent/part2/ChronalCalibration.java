package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pandaism
 * @date 12/1/2018 : 1:41 PM
 */
public class ChronalCalibration {
    public static void main(String[] args) throws IOException, URISyntaxException {
        File inputFile = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;

        int total = 0;
        List<String> frequencies = new ArrayList<>();
        List<Integer> storage = new ArrayList<>();
        storage.add(total);

        while((line = reader.readLine()) != null) {
            frequencies.add(line);
        }

        for(int i = 0; i < frequencies.size(); i++) {
            String frequency = frequencies.get(i);
            int num = Integer.parseInt(frequency);

            total += num;

            if(storage.contains(total)) {
                break;
            } else {
                storage.add(total);
            }

            if(i == frequencies.size() - 1) {
                i = -1;
            }
        }


        System.out.println(total);
    }
}
