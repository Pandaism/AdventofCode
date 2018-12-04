package me.advent.part1;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Pandaism
 * @date 12/4/2018 : 1:11 PM
 */
public class ReposeRecord {

    public static void main(String[] args) throws IOException, URISyntaxException {
        File input = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String line;

        Map<String, String> sortedData = new TreeMap<>();

        while((line = reader.readLine()) != null) {
            String time = line.substring(6, line.indexOf("]"));
            String data = line.substring(line.indexOf("]") + 2);
            sortedData.put(time, data);
        }

        Map<Integer, Long> sleepMap = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> timeCheck = new HashMap<>();
        Map<Integer, Integer> timeCount = null;

        int guardID = 0;
        Calendar sleepTime = null;
        Calendar wakeUpTime;

        int sleepSave = 0;

        for(String timeStamp : sortedData.keySet()) {
            String[] timeStampSplit = timeStamp.split(" ");
            String[] date = timeStampSplit[0].split("-");
            String[] time = timeStampSplit[1].split(":");

            String data = sortedData.get(timeStamp);

            if(data.contains("Guard")) {
                guardID = Integer.parseInt(data.substring(data.indexOf("#") + 1, data.indexOf("begins") - 1));

                if(!timeCheck.containsKey(guardID)) {
                    timeCount = new HashMap<>();
                } else {
                    timeCount = timeCheck.get(guardID);
                }
            }

            int month = Integer.parseInt(date[0]);
            int day = Integer.parseInt(date[1]);
            int minute = Integer.parseInt(time[1]);

            if(data.contains("falls asleep")) {
                sleepTime = new GregorianCalendar(1518, month, day, 0, minute);
                if(timeCount != null) {
                    if(timeCount.containsKey(minute)) {
                        int count = timeCount.get(minute);
                        timeCount.put(minute, count + 1);
                    } else {
                        timeCount.put(minute, 1);
                    }
                }

                sleepSave = minute;
            }

            if(data.contains("wakes up")) {
                wakeUpTime = new GregorianCalendar(1518, month, day, 0, minute);

                if(sleepTime != null) {
                    long diff = wakeUpTime.getTime().getTime() - sleepTime.getTime().getTime();
                    long min = diff / 60000;

                    if(!sleepMap.keySet().contains(guardID)) {
                        sleepMap.put(guardID, min);
                    } else {
                        long storedTime = sleepMap.get(guardID);
                        sleepMap.put(guardID, min + storedTime);
                    }
                }

                if(timeCount != null) {
                    for(int i = sleepSave + 1; i < minute; i++) {
                        if(timeCount.containsKey(i)) {
                            int count = timeCount.get(i);
                            timeCount.put(i, count + 1);
                        } else {
                            timeCount.put(i, 1);
                        }
                    }
                }

                timeCheck.put(guardID, timeCount);
            }
        }

        guardID = sleepMap.keySet().iterator().next();

        for(Integer id : sleepMap.keySet()) {
            if(sleepMap.get(id) > sleepMap.get(guardID)) {
                guardID = id;
            }
        }

        timeCount = timeCheck.get(guardID);
        int mostTime = timeCount.keySet().iterator().next();
        for(Integer min : timeCount.keySet()) {
            if(timeCount.get(min) > timeCount.get(mostTime)) {
                mostTime = min;
            }
        }

        System.out.println(guardID * mostTime);
    }
}
