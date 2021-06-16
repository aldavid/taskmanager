package process;

import java.util.Date;
import java.util.UUID;

//the process class
public class Process {
    private String PID = null;
    // default priority is low
    private String priority = "low";
    // default priorityNo is 3, corresponding to the low priority. this attribute is used to simplify the comparators
    private int priorityNo = 3;
    // default is the current date
    private Date creationDate = new Date();

    // Constructor creating a new process with a new random PID and a default priority
    public Process() {
        UUID uuid = UUID.randomUUID();
        String PID = uuid.toString();

        this.PID = PID;
    }

    // Constructor creating a new process with a new random PID and a custom priority
    public Process(String priority) {
        UUID uuid = UUID.randomUUID();
        String PID = uuid.toString();

        this.PID = PID;
        this.priority = priority;

        if (priority.equals("low")) {
            this.priorityNo = 3;
        }
        if (priority.equals("medium")) {
            this.priorityNo = 2;
        }
        if (priority.equals("high")) {
            this.priorityNo = 1;
        }
    }

    public String getPID() {
        return PID;
    }

    public String getPriority() {
        return priority;
    }

    public int getPriorityNo() {
        return priorityNo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    // kill method that sets all the attribute to null/0
    // the object is not actually destroyed as this is what the garbage collector will do
    public void kill() {
        this.PID = null;
        this.priority = null;
        this.priorityNo = 0;
        this.creationDate = null;
    }
}
