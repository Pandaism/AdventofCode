package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

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

        ArrayList<Integer> overlapIDs = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String indexes = line.substring(line.indexOf('@') + 1, line.indexOf(':')).replaceAll(" ", "");
            String dimension = line.substring(line.indexOf(':') + 1).replaceAll(" ", "");
            String id = line.substring(0, line.indexOf('@')).replaceAll(" ", "").replaceAll("#", "");

            int x = Integer.parseInt(indexes.substring(indexes.indexOf(',') + 1));
            int y = Integer.parseInt(indexes.substring(0, indexes.indexOf(',')));

            int width = Integer.parseInt(dimension.substring(0, dimension.indexOf('x')));
            int height = Integer.parseInt(dimension.substring(dimension.indexOf('x') + 1));

            ids.add(Integer.parseInt(id));

            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {
                    int val = fabric[y + i][x + j];

                    if(val != 0) {
                        if(val != -1) {
                            if(!overlapIDs.contains(val)) {
                                overlapIDs.add(val);
                            }

                            if(!overlapIDs.contains(Integer.parseInt(id))) {
                                overlapIDs.add(Integer.parseInt(id));
                            }

                            fabric[y + i][x + j] = -1;
                        } else {
                            if(!overlapIDs.contains(Integer.parseInt(id))) {
                                overlapIDs.add(Integer.parseInt(id));
                            }
                        }
                    } else {
                        fabric[y + i][x + j] = Integer.parseInt(id) + val;
                    }

                }
            }
        }

        ids.stream().filter(integer -> !overlapIDs.contains(integer)).findFirst().ifPresent(System.out::println);
    }
}
