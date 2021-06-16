package process.comparators;

import process.Process;

import java.util.Comparator;

// this comparator is used to keep the priority queue's elements in order using the priority and the creation date
public class ProcessPriorityComparator implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {

        // compare the priorities to establish the order
        if (p1.getPriorityNo() > p2.getPriorityNo()) {
            return -1;
        } else if (p1.getPriorityNo() < p2.getPriorityNo()) {
            return 1;
        }

        // if the elements have the same priorities, compare creation dates
        if (p1.getCreationDate().compareTo(p2.getCreationDate()) < 0) {
            return -1;
        } else if (p1.getCreationDate().compareTo(p2.getCreationDate()) > 0) {
            return 1;
        }

        return 0;
    }
}
