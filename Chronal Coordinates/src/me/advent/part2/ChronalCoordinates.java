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
 * @date 12/6/2018 : 10:05 AM
 */
public class ChronalCoordinates {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        Map<Integer, Coordinate> coordinates = new HashMap<>();

        int index = 1;
        while ((line = reader.readLine()) != null) {
            String[] coords = line.split(", ");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);

            coordinates.put(index, new Coordinate(x, y));
            index++;
        }

        final int size = 10000;

        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int length = 0;

                for (int point : coordinates.keySet()) {
                    int pointX = coordinates.get(point).getX();
                    int pointY = coordinates.get(point).getY();

                    int manLength = Math.abs(j - pointX) + Math.abs(i - pointY);

                    length += manLength;

                }

                if (length < size) {
                    count++;
                }

            }
        }

        System.out.println(count);

    }

    public static class Coordinate {
        private int x;
        private int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }
    }
}
