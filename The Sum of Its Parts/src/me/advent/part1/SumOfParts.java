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
 * @date 12/7/2018 : 5:43 AM
 */
public class SumOfParts {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        Map<String, Set<String>> map = new TreeMap<>();
        while((line = reader.readLine()) != null) {
            String key = line.substring(line.indexOf("step "), line.indexOf(" can")).split(" ")[1];
            String requirement = line.substring(line.indexOf("Step "), line.indexOf(" must")).split(" ")[1];

            Set<String> requirements = map.containsKey(key) ? map.get(key) : new HashSet<>();
            requirements.add(requirement);

            map.put(key, requirements);
        }

        int alphaLimit = 26;

        for(int i = 65; i < 65 + alphaLimit; i++) {
            String alpha = String.valueOf((char) i);
            if(!map.keySet().contains(alpha)) {
                map.put(alpha, new HashSet<>());
            }
        }

        ArrayList<String> ans = new ArrayList<>();

        while(ans.size() < alphaLimit) {
            for(String key : map.keySet()) {
                if(map.get(key).size() == 0) {
                    if(!ans.contains(key)) {
                        ans.add(key);
                        break;
                    }
                }

                int checks = 0;
                for(String requirement : map.get(key)) {
                    if(ans.contains(requirement)) {
                        checks++;
                    }
                }

                if(checks == map.get(key).size()) {
                    if(!ans.contains(key)) {
                        ans.add(key);
                        break;
                    }
                }
            }
        }

        ans.forEach(System.out::print);
        System.out.println();
    }
}
