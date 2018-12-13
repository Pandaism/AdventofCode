package me.advent.part1;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Pandaism
 * @date 12/10/2018 : 3:49 PM
 */
public class StarsAlign {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        ArrayList<Star> stars = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] position = line.substring(line.indexOf("position"), line.indexOf("velocity")).split("=")[1].replace("<", "").replace(">", "").split(",");
            String[] velocity = line.substring(line.indexOf("velocity")).split("=")[1].replace("<", "").replace(">", "").split(",");

            stars.add(new Star(Integer.parseInt(position[0].trim()), Integer.parseInt(position[1].trim()), Integer.parseInt(velocity[0].trim()), Integer.parseInt(velocity[1].trim())));
        }

        /*
        Each line represents one point. Positions are given as <X, Y> pairs: X represents how far left (negative) or right (positive)
        the point appears, while Y represents how far up (negative) or down (positive) the point appears.
         */

        //Reduce size of sample to a more reasonable size
        int height = 0;

        while(height > 9 || height == 0) {
            ArrayList<Integer> characterHeight = new ArrayList<>();

            for(Star star : stars) {
                characterHeight.add(star.y);

                star.x += star.vx;
                star.y += star.vy;
            }

            int max = characterHeight.stream().max(Comparator.comparing(Integer::intValue)).get();
            int min = characterHeight.stream().min(Comparator.comparing(Integer::intValue)).get();

            height = max - min;
        }

        for(Star star : stars) {
            star.x -= star.vx;
            star.y -= star.vy;
        }

        String[][] sky = new String[(getYLimit(stars) * 2) + 1][(getXLimit(stars) * 2) + 1];
        for(String[] row : sky) {
            Arrays.fill(row, ".");
        }

        int yAxis = (sky.length / 2) - 1;
        int xAxis = (sky[0].length / 2) - 1;

        for(Star star : stars) {
            sky[yAxis + star.y][xAxis + star.x] = "#";
        }

        sky[yAxis][xAxis] = "C";

        StringBuilder sb = new StringBuilder();

        for(String[] r : sky) {
            for(String s : r) {
                sb.append(s).append(" ");
            }
            sb.append("\n");
        }

        File out = new File("out.txt");
        out.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(out));
        writer.write(sb.toString());

        writer.close();
    }

    private static int getYLimit(ArrayList<Star> stars) {
        int ans = -1;

        for(Star star : stars) {
            if(Math.abs(star.y) > ans) ans = Math.abs(star.y);
        }

        return ans;
    }

    private static int getXLimit(ArrayList<Star> stars) {
        int ans = -1;

        for(Star star : stars) {
            if(Math.abs(star.x) > ans) ans = Math.abs(star.x);
        }

        return ans;
    }

    public static class Star {
        private int x;
        private int y;
        private int vx;
        private int vy;

        Star(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }


    }
}
