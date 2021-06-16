package test;

import process.Process;
import taskmanager.TaskManager;

import java.util.Random;

// main class used for testing purposes
public class Main {

    public static void main(String[] args) throws InterruptedException {

        // creates a task manager with a type
        TaskManager taskManager = new TaskManager(0);

        // the variables are used to randomly pick priorities
        String[] priorities = {"low", "medium", "high"};
        Random random = new Random();

        // creates noOfProcesses process with random priorities for testing purposes
        Process process = null;
        int noOfProcesses = 15;
        for (int i = 0; i < noOfProcesses; i++) {
            process = new Process(priorities[random.nextInt(priorities.length)]);
            Thread.sleep(200);
            taskManager.add(process);

        }

        // kills all the high priority elements
        taskManager.killGroup("high");

        // lists the elements sorted by the creation date
        taskManager.list(0);
    }
}
