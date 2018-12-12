package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Pandaism
 * @date 12/8/2018 : 3:35 PM
 */
public class MemoryManeuver {

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();

        int[] input = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
        Deque<Node> nodes = new LinkedList<>();

        Node root = null;

        for(int i = 0; i < input.length; i++) {
            int a = input[i];
            int b = input[i + 1];

            if(i == 0) {
                root = new Node(a, b);
                nodes.push(root);

                i++;
            } else {
                Node peek = nodes.peekFirst();

                if(peek.getChildren().size() < peek.getNumOfChildren()) {
                    Node child = new Node(a, b);
                    peek.getChildren().add(child);
                    nodes.push(child);

                    i++;
                } else {
                    for (int j = i; j < i + peek.getNumOfMetadata(); j++) {
                        if (peek.getNumOfChildren() == 0) {
                            peek.addValue(input[j]);
                        } else {
                            if (input[j] > peek.getChildren().size()) {
                                continue;
                            }

                            peek.addValue(peek.getChildren().get(input[j] - 1).getValue());
                        }
                    }


                    i += peek.getNumOfMetadata() - 1;
                    nodes.pop();
                }
            }
        }

        if(root != null) {
            System.out.println(root.getValue());
        }
    }

    static class Node {
        private int numOfChildren;
        private int numOfMetadata;
        private ArrayList<Node> children;

        private int value;

        Node(int children, int metadata) {
            this.numOfChildren = children;
            this.numOfMetadata = metadata;

            this.children = new ArrayList<>();

            this.value = 0;
        }

        int getNumOfChildren() {
            return numOfChildren;
        }

        int getNumOfMetadata() {
            return numOfMetadata;
        }
        ArrayList<Node> getChildren() {
            return children;
        }

        void addValue(int meta) {
            this.value += meta;
        }

        int getValue() {
            return value;
        }
    }
}
