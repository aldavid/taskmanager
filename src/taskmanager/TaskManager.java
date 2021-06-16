package taskmanager;

import process.Process;
import process.comparators.ProcessFIFOComparator;
import process.comparators.ProcessPriorityComparator;

import java.util.*;

// the task manager class
public class TaskManager {

    // attribute that holds the maximum number of processes that the task amanger can hold
    private final int maxNoOfProcesses = 10;

    // the task manager uses a priority queue (with different comparators) to keep track of the elements
    private PriorityQueue<Process> processesQueue = null;

    // the type of the task manager
    // taskManagerType = 0 means the PriorityQueue is using the FIFO order and comparator and
    // no elements will be added to the queue when the maxNoOfProcesses limit is reached

    // taskManagerType = 1 means the PriorityQueue is using the FIFO order and comparator and
    // elements will be added to the queue when the maxNoOfProcesses limit is reached, but removing the first ones

    // taskManagerType = 2 means the PriorityQueue is using new comparator based on priority and
    // elements may be added to the queue when the maxNoOfProcesses limit is reached, by removing processes with a lower priority (if any)
    private final int taskManagerType;

    //the hashmap attribute is being used to simplify and optimize the kill process
    private final HashMap<String, Process> processesHashMap = new HashMap<>();

    // constructor parameterized with the task manager type
    public TaskManager(int taskManagerType) {
        this.taskManagerType = taskManagerType;

        if (taskManagerType == 0 || taskManagerType == 1) {
            processesQueue = new PriorityQueue<>(maxNoOfProcesses, new ProcessFIFOComparator());
        }

        if (taskManagerType == 2) {
            processesQueue = new PriorityQueue<>(maxNoOfProcesses, new ProcessPriorityComparator());
        }
    }

    // the add method uses the task manager type to decide how to add new elements
    public void add(Process process) {

        // for the first type of task manager, when the queue is full, new elements cannot be added to the queue
        if (this.taskManagerType == 0) {
            if (processesQueue.size() < maxNoOfProcesses) {
                processesQueue.add(process);
                processesHashMap.put(process.getPID(), process);
            }
        }

        // for the second type of task manager, when the queue is full, the front of the queue is removed and the new element is added
        if (this.taskManagerType == 1) {
            if (processesQueue.size() == maxNoOfProcesses) {
                Process firstProcess = processesQueue.peek();
                processesHashMap.remove(firstProcess.getPID());
                firstProcess.kill();
                processesQueue.remove();
            }
            processesQueue.add(process);
            processesHashMap.put(process.getPID(), process);
        }

        // for the first third of task manager, when the queue is full, the front of the queue is removed and the new element is added
        // only if the priority of the front element is lower than the one of the new element. otherwise, it does nothing
        if (this.taskManagerType == 2) {
            if (processesQueue.size() == maxNoOfProcesses) {
                Process firstProcess = processesQueue.peek();
                if (process.getPriorityNo() < firstProcess.getPriorityNo()) {
                    processesHashMap.remove(firstProcess.getPID());
                    firstProcess.kill();
                    processesQueue.remove();
                } else {
                    return;
                }
            }

            processesQueue.add(process);
            processesHashMap.put(process.getPID(), process);
        }
    }

    // method used to list the elements of the queue in a given order
    // sortOrder = -1 can be used to test the actual order of the priority queue. however, it empties the queue
    // sortOrder = 0, lists the contents of the queue sorted by the creation date
    // sortOrder = 1, lists the contents of the queue sorted by the priority
    // sortOrder = 2, lists the contents of the queue sorted by the PID
    public void list(int sortOrder) {

        if(processesQueue.isEmpty()){
            System.out.println("---------------------There are no running processes---------------------");
        }

        List<Process> processList = new ArrayList(processesQueue);

/*        if (sortOrder == -1) {
            process.Process process = null;
            while (!processesQueue.isEmpty()) {
                process = processesQueue.poll();
                System.out.println(process.getPID() + " - " + process.getPriority() + " - " + process.getPriorityNo() + " - " + process.getCreationDate());
            }
        }*/

        if (sortOrder == 0) {
            System.out.println("---------------------Processes by Creation Date---------------------");
            processList.sort(Comparator.comparing(Process::getCreationDate));
            for (Process process : processList) {
                System.out.println(process.getPID() + " - " + process.getPriority() + " - " + process.getCreationDate());
            }
        }

        if (sortOrder == 1) {
            System.out.println("---------------------Processes by Priority---------------------");
            processList.sort(Comparator.comparing(Process::getPriorityNo));
            for (Process process : processList) {
                System.out.println(process.getPID() + " - " + process.getPriority() + " - " + process.getCreationDate());
            }
        }

        if (sortOrder == 2) {
            System.out.println("---------------------Processes by PID---------------------");
            processList.sort(Comparator.comparing(Process::getPID));
            for (Process process : processList) {
                System.out.println(process.getPID() + " - " + process.getPriority() + " - " + process.getCreationDate());
            }
        }
    }

    // method used to remove a process from the queue using a predicate/filter
    // kills the process
    // removes the object from the hashmap
    public void kill(String PID) {
        processesQueue.removeIf(process -> (process.getPID().equals(PID)));

        Process currentProcess = processesHashMap.get(PID);
        currentProcess.kill();
        processesHashMap.remove(PID);
    }

    // method used to iterate through the queue, remove and kill all the process that have the given priority
    // another option would be to use a predicate, without an iterator
    public void killGroup(String priority) {
        List<Process> processList = new ArrayList(processesQueue);

        for (Process p : processList) {
            if (p.getPriority().equals(priority)) {
                this.kill(p.getPID());
            }
        }
    }

    // method used to iterate through the queue, remove and kill all the processes
    // another option would be to use a predicate, without an iterator
    public void killAll() {
        List<Process> processList = new ArrayList(processesQueue);

        for (Process p : processList) {
            this.kill(p.getPID());
        }
    }
}
