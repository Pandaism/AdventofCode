package me.advent.part1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Pandaism
 * @date 12/5/2018 : 8:36 PM
 */
public class AlchemicalReduction {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String input = reader.readLine();

        Deque<Character> output = new LinkedList<>();

        for(int i = 0; i < input.length(); i++) {
            if(output.isEmpty()) {
                output.push(input.charAt(i));
            } else {
                char peek = output.peekFirst();

                if(Math.abs(peek - input.charAt(i)) == 32) {
                    output.pop();
                } else {
                    output.push(input.charAt(i));
                }
            }
        }

        System.out.println("\n" + output.size());
    }


}
