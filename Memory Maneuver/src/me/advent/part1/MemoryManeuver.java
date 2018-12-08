package me.advent.part1;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
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

        int sumMeta = 0;

        for(int i = 0; i < input.length; i++) {
            int a = input[i];
            int b = input[i + 1];

            if(i == 0) {
                nodes.push(new Node(a, b));
                i++;
            } else {
                Node node = nodes.peekFirst();

                if(node.getChildren() > 0) {
                    nodes.push(new Node(a, b));
                    i++;
                } else {
                    int j;
                    for(j = i; j < i + node.getMetadata(); j++) {
                        sumMeta += input[j];
                    }

                    i = (i + node.getMetadata()) - 1;
                    nodes.pop();

                    if(nodes.peekFirst() != null) {
                        Node ancestor = nodes.peekFirst();
                        ancestor = ancestor.removeChild();
                        nodes.pop();
                        nodes.push(ancestor);
                    } else {
                        if(j == input.length) {
                            break;
                        }
                    }
                }
            }
        }

        System.out.println(sumMeta);
    }

    static class Node {
        private int children;
        private int metadata;

        Node(int children, int metadata) {
            this.children = children;
            this.metadata = metadata;
        }

        int getChildren() {
            return children;
        }

        Node removeChild() {
            children -= 1;
            return this;
        }

        int getMetadata() {
            return metadata;
        }
    }
}
