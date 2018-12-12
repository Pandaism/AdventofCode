package me.advent.part2;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Pandaism
 * @date 12/12/2018 : 5:17 AM
 */

//85min 09s 761ms
public class MarbleMania {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File testcase = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(testcase));
        String line = reader.readLine();
//        line = reader.readLine();
//
//        File out = new File("out.txt");
//        out.createNewFile();
//        BufferedWriter writer = new BufferedWriter(new FileWriter(out));
//        StringBuilder sb = new StringBuilder();


        String[] split = line.split(" ");

        int players = Integer.parseInt(split[0]);
        int lastMarble = Integer.parseInt(split[6]) * 100;

        List<Integer> game = new ArrayList<>();
        game.add(0);

        int currentPlayer = 1;
        Map<Integer, Long> score = new HashMap<>();

        int pointer = 0;
        for (int i = 1; i < lastMarble; i++) {
            if (i % 23 == 0) {
                //get i - 7 and add to score for player
                int index = pointer - 7 > 0 ? pointer - 7 : game.size() - Math.abs(pointer - 7);

                long take = game.get(index);
                long point;
                if (score.containsKey(currentPlayer)) {
                    point = score.get(currentPlayer) + take + i;

                    score.put(currentPlayer, point);
                } else {
                    point = take + i;

                    score.put(currentPlayer, point);
                }

                //remove i - 7
                game.remove(index);
                //new currentPoint = i - 7
                pointer = index;
            } else {
                if (pointer + 1 == game.size()) {
                    pointer = 0;
                    game.add(pointer + 1, i);
                    pointer++;
                } else {
                    game.add(pointer + 2, i);
                    pointer += 2;
                }
            }

//            sb.append("[" + i + "] ");
//            for(Integer j : gameStack) {
//                sb.append(j + " ");
//            }
//
//            sb.append("\n");

            if (currentPlayer + 1 > players) {
                currentPlayer = 1;
            } else {
                currentPlayer++;
            }

            //System.out.println(i);
        }

        int winner = -1;
        for (Integer player : score.keySet()) {
            if (winner == -1) {
                winner = player;
                continue;
            }

            if (score.get(player) > score.get(winner)) {
                winner = player;
            }
        }

//        writer.write(sb.toString());
//        writer.close();
        System.out.printf("Winner: %d with score %d\n", winner, score.get(winner));

    }
}
