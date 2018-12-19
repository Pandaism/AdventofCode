package me.advent.part2;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
        int diff = 0;
        int total = 0;

        HashSet<String> memo = new HashSet<>();

        while(counter < 50000000000L) {
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

            int firstIndex = input.indexOf("#");
            int startIndex = zeroLocation - (firstIndex - 1);
            String t = input.substring(zeroLocation - startIndex, input.toString().lastIndexOf("#") + 2);

            total = 0;

            for(int i = 0; i < t.length(); i++) {
                int value = i - startIndex;

                if(t.charAt(i) == '#') {
                    total += value;
                }

                if(!t.substring(i, t.length()).contains("#")) {
                    break;
                }
            }

            if(memo.contains(t)) {
                diff = total - diff;
                break;
            } else {
                memo.add(t);
                diff = total;
            }

            counter++;
        }

        long v = ((50000000000L - (counter - 1)) * diff) + (total - diff);

        System.out.println(v);
    }
}
