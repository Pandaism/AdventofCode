package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pandaism
 * @date 12/13/2018 : 8:07 PM
 */
public class ChronalCharge {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String serial = reader.readLine();

        int gridSize = 300;
        int[][] grid = new int[gridSize][gridSize];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                int power;
                int rackID = (i + 1) + 10;
                int startingPower = rackID * (j + 1);
                power = startingPower + Integer.parseInt(serial);
                power *= rackID;
                if(power < 1000) {
                    power /= 100;
                } else {
                    String sPower = String.valueOf(power);
                    char h = sPower.charAt(sPower.length() - 3);
                    power = Integer.parseInt(String.valueOf(h));
                }

                power -= 5;

                grid[j][i] = power;
            }
        }

        Map<Coordinates, Integer> powerLevel = new HashMap<>();
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length; x++) {
                for(int size = 1; size < 301; size++) {
                    if(x + size > grid[y].length) {
                        break;
                    }

                    if(y + size > grid.length) {
                        break;
                    }

                    int power = 0;

                    for(int i = y; i < y + size; i++) {
                        for(int j = x; j < x + size; j++) {
                            power += grid[i][j];
                        }
                    }

                    powerLevel.put(new Coordinates(x + 1, y + 1, size), power);
                }
            }
        }

        Coordinates ans = null;
        for(Coordinates coords : powerLevel.keySet()) {
            if(ans == null) {
                ans = coords;
            } else {
                if(powerLevel.get(coords) > powerLevel.get(ans)) {
                    ans = coords;
                }
            }

        }

        if(ans != null) System.out.printf("%d,%d,%d\n", ans.x, ans.y, ans.size);

        /*
        To find fuel cell level
        Example: Cell (3, 5) with serial 8

            Find the fuel cell's rack ID, which is its X coordinate plus 10.
                The rack ID is 3 + 10 = 13

            Begin with a power level of the rack ID times the Y coordinate.
                The power level starts at 13 * 5 = 65

            Increase the power level by the value of the grid serial number (your puzzle input).
                Adding the serial number produces 65 + 8 = 73

            Set the power level to itself multiplied by the rack ID.
                Multiplying by the rack ID produces 73 * 13 = 949

            Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
                The hundreds digit of 949 is 9

            Subtract 5 from the power level.
                Subtracting 5 produces 9 - 5 = 4

         The power level of this fuel cell is 4.
         */
    }

    static class Coordinates {
        private int x;
        private int y;
        private int size;

        Coordinates(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }
    }
}
