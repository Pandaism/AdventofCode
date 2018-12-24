package me.advent.part1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pandaism
 * @date 12/21/2018 : 6:39 AM
 */
public class ChocolateCharts {

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Integer input = Integer.parseInt(reader.readLine());

        int[] elves = new int[2];
        elves[0] = 0;
        elves[1] = 1;

        List<Integer> recipes = new ArrayList<>(Arrays.asList(3, 7));
        while (recipes.size() < input + 11) {
            String[] total = String.valueOf(recipes.get(elves[0]) + recipes.get(elves[1])).split("");
            for (String s : total) {
                recipes.add(Integer.parseInt(s));
            }

            elves[0] = incrementBy(recipes.get(elves[0]) + 1, elves[0], recipes.size());
            elves[1] = incrementBy(recipes.get(elves[1]) + 1, elves[1], recipes.size());
        }


        for (int i = input; i < input + 10; i++) {
            System.out.printf("%d", recipes.get(i));
        }
        System.out.println();
    }


    private static int incrementBy(int value, int currentIndex, int size) {
        while (value > 0) {
            if(currentIndex >= size - 1) {
                currentIndex = -1;
            }

            value--;
            currentIndex++;
        }

        return currentIndex;
    }
}
