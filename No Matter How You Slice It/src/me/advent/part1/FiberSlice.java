package me.advent.part1;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author Pandaism
 * @date 12/3/2018 : 1:00 PM
 */
public class FiberSlice {

    public static void main(String[] args) throws URISyntaxException, IOException {
        File input = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line;

        int[][] fabric = new int[1000][1000];
        for(int[] row : fabric) {
            Arrays.fill(row, 0);
        }

        int overlap = 0;
        while ((line = reader.readLine()) != null) {
            String indexes = line.substring(line.indexOf('@') + 1, line.indexOf(':')).replaceAll(" ", "");
            String dimension = line.substring(line.indexOf(':') + 1).replaceAll(" ", "");

            int x = Integer.parseInt(indexes.substring(indexes.indexOf(',') + 1));
            int y = Integer.parseInt(indexes.substring(0, indexes.indexOf(',')));

            int width = Integer.parseInt(dimension.substring(0, dimension.indexOf('x')));
            int height = Integer.parseInt(dimension.substring(dimension.indexOf('x') + 1));

            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {
                    fabric[y + i][x + j] += 1;
                    if(fabric[y + i][x + j] == 2) {
                        overlap++;
                    }
                }
            }
        }

        System.out.println(overlap);
    }
}
