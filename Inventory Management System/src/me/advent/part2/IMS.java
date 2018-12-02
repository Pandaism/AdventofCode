package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pandaism
 * @date 12/2/2018 : 3:51 PM
 */
public class IMS {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        Map<Character, ArrayList<String>> groups = new HashMap<>();
        int replacmentIndex = 0;

        readinput: while ((line = reader.readLine()) != null) {
            char firstLetter = line.charAt(0);

            ArrayList<String> strings;
            if(groups.containsKey(firstLetter)) {
                strings = groups.get(firstLetter);

                for(String value : groups.get(firstLetter)) {
                    int replacement = 0;

                    for(int i = 0; i < value.length(); i++) {
                        if(replacement > 1) {
                            break;
                        }

                        char a = value.charAt(i);
                        char b = line.charAt(i);

                        if(b != a) {
                            replacmentIndex = i;
                            replacement++;
                        }

                        if(i == value.length() - 1 && replacement == 1) {
                            break readinput;
                        }
                    }

                }

                strings.add(line);
                groups.put(firstLetter, strings);
            } else {
                strings = new ArrayList<>();
                strings.add(line);
                groups.put(firstLetter, strings);
            }
        }

        assert line != null;
        System.out.println(line.substring(0, replacmentIndex) + "" + line.substring(replacmentIndex + 1));
    }
}
