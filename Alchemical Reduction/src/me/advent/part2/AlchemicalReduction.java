package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Pandaism
 * @date 12/5/2018 : 8:36 PM
 */
public class AlchemicalReduction {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String input = reader.readLine();
        Map<Integer, Integer> polymerLengths = new HashMap<>();

        for(int alpha = 65; alpha < 65 + 26; alpha++) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < input.length(); i++) {
                char indexChar = input.charAt(i);

                if(indexChar != alpha && indexChar != alpha + 32) {
                    sb.append(indexChar);
                }
            }

            Deque<Character> reacted = react(sb.toString());
            polymerLengths.put(alpha, reacted.size());
        }

        int ans = -1;
        for (Integer key: polymerLengths.keySet()) {
            if(ans == -1) {
                ans = polymerLengths.get(key);
            } else {
                if(polymerLengths.get(key) < ans) {
                    ans = polymerLengths.get(key);
                }
            }
        }

        System.out.println(ans);
    }

    private static Deque<Character> react(String input) {
        Deque<Character> output = new LinkedList<>();

        for (int i = 0; i < input.length(); i++) {
            if (output.isEmpty()) {
                output.push(input.charAt(i));
            } else {
                char peek = output.peekFirst();

                if (Math.abs(peek - input.charAt(i)) == 32) {
                    output.pop();
                } else {
                    output.push(input.charAt(i));
                }
            }
        }

        return output;
    }
}
