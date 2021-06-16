package process.comparators;

import process.Process;

import java.util.Comparator;

// this comparator is used to keep the priority queue's elements in FIFO order using the creation date
public class ProcessFIFOComparator implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {
        // compare the creation dates to establish the order
        if (p1.getCreationDate().compareTo(p2.getCreationDate()) < 0) {
            return -1;
        } else if (p1.getCreationDate().compareTo(p2.getCreationDate()) > 0) {
            return 1;
        }

        return 0;
    }
}
