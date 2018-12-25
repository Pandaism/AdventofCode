package me.advent.part2;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Pandaism
 * @date 12/21/2018 : 6:39 AM
 */
public class ChocolateCharts {

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String inputString = reader.readLine();

        int[] elves = new int[2];
        elves[0] = 0;
        elves[1] = 1;


        List<Integer> recipes = new ArrayList<>(Arrays.asList(3, 7));
        while (recipes.size() < 7) {
            String[] total = String.valueOf(recipes.get(elves[0]) + recipes.get(elves[1])).split("");
            for (String s : total) {
                recipes.add(Integer.parseInt(s));
            }

            elves[0] = incrementBy(recipes.size(), recipes.get(elves[0]) + 1, elves[0]);
            elves[1] = incrementBy(recipes.size(), recipes.get(elves[1]) + 1, elves[1]);
        }

        List<Integer> input = new ArrayList<>();
        for(String s : inputString.split("")) {
            input.add(Integer.parseInt(s));
        }

        while(!match(recipes.subList(recipes.size() - 7, recipes.size()), input)) {
            String[] total = String.valueOf(recipes.get(elves[0]) + recipes.get(elves[1])).split("");
                for(String s : total) {
                    recipes.add(Integer.parseInt(s));
                }

                elves[0] = incrementBy(recipes.size(), recipes.get(elves[0]) + 1, elves[0]);
                elves[1] = incrementBy(recipes.size(), recipes.get(elves[1]) + 1, elves[1]);
        }

        String result = String.valueOf(recipes).replaceAll(", ", "").substring(1);
        System.out.println(result.indexOf(String.valueOf(inputString)));

    }

    private static boolean match(List<Integer> subList, List<Integer> input) {
        String sub = String.valueOf(subList).replaceAll(", ", "");
        String in = String.valueOf(input).replaceAll(", ", "");
        in = in.substring(1, in.length() - 1);
        return sub.contains(in);
    }

    private static int incrementBy(int size, int increment, int current) {
        if(current + increment >= size) {
            int moves = increment % size;

            if(moves == 0) {
                return current;
            }


            if(moves + current == size) {
                return 0;
            } else if(moves + current > size) {
                int diff = size - current;

                return moves - diff;
            } else {
                return current + moves;
            }
        } else {
            return current + increment;
        }
    }
}
