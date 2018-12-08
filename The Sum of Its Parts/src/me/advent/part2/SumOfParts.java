package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Pandaism
 * @date 12/7/2018 : 5:43 AM
 */
public class SumOfParts {
    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        Map<String, Set<String>> map = new TreeMap<>();
        while((line = reader.readLine()) != null) {
            String key = line.substring(line.indexOf("step "), line.indexOf(" can")).split(" ")[1];
            String requirement = line.substring(line.indexOf("Step "), line.indexOf(" must")).split(" ")[1];

            Set<String> requirements = map.containsKey(key) ? map.get(key) : new HashSet<>();
            requirements.add(requirement);

            map.put(key, requirements);
        }

        int alphaLimit = 26;

        for(int i = 65; i < 65 + alphaLimit; i++) {
            String alpha = String.valueOf((char) i);
            if(!map.keySet().contains(alpha)) {
                map.put(alpha, new HashSet<>());
            }
        }

        ArrayList<String> ans = new ArrayList<>();

        int limit = 5;
        Worker[] workers = new Worker[limit];
        for(int i = 0; i < limit; i++) {
            workers[i] = new Worker(i + 1);
        }
        int time = -1;

        while(ans.size() < alphaLimit) {
            String storedKey = "";

            for(Worker worker : workers) {
                if(time >= worker.getEndTime() && worker.getEndTime() != -1) {
                    System.out.printf("Worker %s finished task %s at %d\n", worker.getId(), worker.getTask(), time);
                    ans.add(worker.getTask());
                    worker.clearTask();
                    time--;
                }

                if (!worker.isBusy()) {
                    for (String key : map.keySet()) {
                        if (map.get(key).size() == 0) {
                            if (!ans.contains(key)) {
                                if (!isWorkerDoing(workers, key)) {
                                    worker.setTask(key, time);
                                    storedKey = key;
                                    break;
                                }
                            }
                        }

                        int checks = 0;
                        for (String requirement : map.get(key)) {
                            if (ans.contains(requirement)) {
                                checks++;
                            }
                        }

                        if (checks == map.get(key).size()) {
                            if (!ans.contains(key)) {
                                worker.setTask(key, time);
                                storedKey = key;
                                break;
                            }
                        }
                    }

                    map.remove(storedKey);
                }

            }

            time++;
        }

        ans.forEach(System.out::print);
        System.out.println("\n" + time);
    }

    private static boolean isWorkerDoing(Worker[] workers, String task) {
        for(Worker worker : workers) {
            if(worker.getTask().equals(task)) {
                return true;
            }
        }

        return false;
    }

    private static class Worker {
        private int id;
        private String task;
        private int endTime;
        private boolean busy;

        private Worker(int id) {
            this.id = id;
            this.task = "";
            this.endTime = -1;
            this.busy = false;
        }

        void setTask(String task, int time) {
            this.task = task;
            this.endTime = 61 +(task.charAt(0) - 64) + time; // Allotted + (Ascii Index - Index of Ascii (Uppercase A - 1)) + time;
            this.busy = true;
        }

        String getTask() {
            return this.task;
        }

        int getEndTime() {
            return this.endTime;
        }

        boolean isBusy() {
            return busy;
        }

        void clearTask() {
            this.task = "";
            this.endTime = -1;
            this.busy = false;
        }

        int getId() {
            return id;
        }
    }
}
