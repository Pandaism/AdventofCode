package me.advent.part1;

import java.io.*;
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
        while((line = reader.readLine()) != null) {
            String[] coords = line.split(", ");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);

            coordinates.put(index, new Coordinate(x, y));
            index++;
        }

        final int size = 500;
        String[][] map = new String[size][size];

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                int bestCoord = 0;
                int length = -1;

                for(int point : coordinates.keySet()) {
                    int pointX = coordinates.get(point).getX();
                    int pointY = coordinates.get(point).getY();

                    if(j == pointX && i == pointY) {
                        bestCoord = point;
                        break;
                    }

                    int manLength = Math.abs(j - pointX) + Math.abs(i - pointY);

                    if(length == -1) {
                        length = manLength;
                        bestCoord = point;
                    } else {
                        if(manLength < length) {
                            bestCoord = point;
                            length = manLength;
                        } else if (manLength == length) {
                            bestCoord = 0;
                        }
                    }
                }

                map[i][j] = String.valueOf(bestCoord);
            }
        }

        ArrayList<String> infinite = new ArrayList<>();

        for(int i = 0; i < map.length; i++) {
            for(int j  = 0; j < map[i].length; j++) {
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    if(!infinite.contains(map[i][j])) {
                        infinite.add(map[i][j]);
                    }
                }
            }
        }

        Map<String, Integer> finiteCount = new HashMap<>();

        for(int i = 0; i < map.length; i++) {
            for(int j  = 0; j < map[i].length; j++) {
                if(i != 0 && i != size - 1 && j != 0 && j != size - 1) {
                    if(!infinite.contains(map[i][j])) {
                        if(!finiteCount.containsKey(map[i][j])) {
                            finiteCount.put(map[i][j], 1);
                        } else {
                            int c = finiteCount.get(map[i][j]);
                            finiteCount.put(map[i][j], c + 1);
                        }
                    }
                }
            }
        }

        int result = -1;
        for(String s : finiteCount.keySet()) {
            if(finiteCount.get(s) > result) {
                result = finiteCount.get(s);
            }
        }

        System.out.println(result);
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
