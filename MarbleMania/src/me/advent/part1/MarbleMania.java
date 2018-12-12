package me.advent.part1;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Pandaism
 * @date 12/12/2018 : 5:17 AM
 */
public class MarbleMania {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File testcase = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(testcase));
        String line = reader.readLine();

        String[] split = line.split(" ");

        int players = Integer.parseInt(split[0]);
        int lastMarble = Integer.parseInt(split[6]);

        Deque<Integer> gameStack = new LinkedList<>();
        gameStack.push(0);

        int currentPlayer = 1;
        Map<Integer, Integer> score = new HashMap<>();

        int pointer = 0;
        for (int i = 1; i < lastMarble; i++) {
            if (i % 23 == 0) {
                //get i - 7 and add to score for player
                int index = pointer - 7 > 0 ? pointer - 7 : gameStack.size() - Math.abs(pointer - 7);

                int take = ((LinkedList<Integer>) gameStack).get(index);
                int point;
                if (score.keySet().contains(currentPlayer)) {
                    point = score.get(currentPlayer) + take + i;

                    score.put(currentPlayer, point);
                } else {
                    point = take + i;

                    score.put(currentPlayer, point);
                }

                //remove i - 7

                ((LinkedList<Integer>) gameStack).remove(index);
                //new currentPoint = i - 7
                pointer = index;
            } else {
                if (pointer + 1 == gameStack.size()) {
                    pointer = 0;
                    ((LinkedList<Integer>) gameStack).add(pointer + 1, i);
                    pointer++;
                } else {
                    ((LinkedList<Integer>) gameStack).add(pointer + 2, i);
                    pointer += 2;
                }
            }

            if (currentPlayer + 1 > players) {
                currentPlayer = 1;
            } else {
                currentPlayer++;
            }
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

        System.out.printf("Winner: %d with score %d\n", winner, score.get(winner));

    }
}
