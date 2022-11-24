import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;

public class Process2 {
    //Initialization
    private int timeArrival;
    private int jobNo;
    private int memUnit;
    private int devices;
    private int TimeBurst;
    private int priority;
    private int TotalBurstTime;
    private int systemTime;
    private int remainingBurstTime;
    
     //Empty constructor for dummy object
    public Process2() {
    }
    //Main constructor
    public Process2(int timeArrival, int jobNo, int memUnit, int devices, int TimeBurst, int priority) {
        this.timeArrival = timeArrival;
        this.jobNo = jobNo;
        this.memUnit = memUnit;
        this.devices = devices;
        this.TimeBurst = TimeBurst;
        this.priority = priority;
        this.remainingBurstTime = TimeBurst;
    }

    //Setters and getters
    public int getremainingBurstTime() {
        return this.remainingBurstTime;
    }

    public void setremainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    public int getsystemTime() {
        return this.systemTime;
    }

    public void setsystemTime(int systemTime) {
        this.systemTime = systemTime;
    }

    public int getTimeArrival() {
        return this.timeArrival;
    }

    public void setTimeArrival(int timeArrival) {
        this.timeArrival = timeArrival;
    }

    public int getTotalBurstTime() {
        return this.TotalBurstTime;
    }

    public void setTotalBurstTime(int TotalBurstTime) {
        this.TotalBurstTime = TotalBurstTime;
    }


    public int getTimeBurst() {
        return this.TimeBurst;
    }

    public void setTimeBurst(int TimeBurst) {
        this.TimeBurst = TimeBurst;
    }

    @Override
    public String toString() {
        return "{" +
                " timeArrival='" + getTimeArrival() + "'" +
                ", jobNo='" + getJobNo() + "'" +
                ", memUnit='" + getMemUnit() + "'" +
                ", devices='" + getDevices() + "'" +
                ", TimeBurst='" + getTimeBurst() + "'" +
                ", priority='" + getPriority() + "'" +
                "}";
    }

    public int getJobNo() {
        return this.jobNo;
    }

    public void setJobNo(int jobNo) {
        this.jobNo = jobNo;
    }

    public int getMemUnit() {
        return this.memUnit;
    }

    public void setMemUnit(int memUnit) {
        this.memUnit = memUnit;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDevices() {
        return this.devices;
    }

    public void setDevices(int devices) {
        this.devices = devices;
    }


    //A method to move the process from a queue to the ready queue
    public void MoveToReadyQ(Queue<Process2> ReadyQ, PriorityQueue<Process2> HoldQ1p, Queue<Process2> HoldQ2,
            ArrayList<Config> PC, Queue<Process2> SubmitQ, Process2 proc) {

        if (proc.getMemUnit() > PC.get(0).getMemUnit() || proc.getDevices() > PC.get(0).getDevices()) {
            SubmitQ.remove();
            return;
        }

        //Check if the ready queue has a place for the incoming process
        if (proc.getMemUnit() <= PC.get(0).getremainingMEM() && proc.getDevices() <= PC.get(0).getremainingDEV()
                && !ReadyQ.contains(proc)) {
            //Deleting the process if its coming from the submit queue
            if (SubmitQ.contains(proc))
                SubmitQ.remove();
            //Add the process to the ready queue
            ReadyQ.add(proc);
            //Deleting the process from the hold queues if its coming from the hold queues
            if (proc.getPriority() == 1 && HoldQ1p.contains(proc))
                HoldQ1p.poll();
            else if (proc.getPriority() == 2 && HoldQ2.contains(proc))
                HoldQ2.poll();
            //Setting the system specs based on the ready queue capacity
            PC.get(0).setremainingDEV(PC.get(0).getremainingDEV() - proc.getDevices());
            PC.get(0).setremainingMEM(PC.get(0).getremainingMEM() - proc.getMemUnit());
            setTotalBurstTime(proc.getTimeBurst() + getTotalBurstTime());

        } else if (proc.getPriority() == 1 && !HoldQ1p.contains(proc)) {                    //If the process has no place in the ready queue, it will be directed into
            HoldQ1p.add(proc);                                                              //one of the hold queues based on their priority
            if (SubmitQ.contains(proc))
                SubmitQ.remove();

        } else if (proc.getPriority() == 2) {
            HoldQ2.add(proc);
            if (SubmitQ.contains(proc))
                SubmitQ.remove();
        }
    }
    //Dynamic round robin method
    public void RoundRobin(Queue<Process2> ReadyQ, PriorityQueue<Process2> HoldQ1p, Queue<Process2> HoldQ2,
            ArrayList<Config> PC, int quantum, StringBuffer str) {
        //Calculating the quantum which is the average memory value for the processes that are inside the ready queue
        quantum = getTotalBurstTime() / ReadyQ.size();
        //If ready queue is empty it will break;
        if (ReadyQ.isEmpty()) {
            return;
        }
        //If the process burst time is lower than the quantum value, it will be processed and removed from the ready queue
        if (ReadyQ.peek().getremainingBurstTime() <= quantum) {
            //Calculating the system time and PC config
            systemTime += ReadyQ.peek().getremainingBurstTime();
            ReadyQ.peek().setremainingBurstTime(0);
            PC.get(0).setremainingDEV(PC.get(0).getremainingDEV() + ReadyQ.peek().getDevices());
            PC.get(0).setremainingMEM(PC.get(0).getremainingMEM() + ReadyQ.peek().getMemUnit());

            //Adding the process to the output
            setTotalBurstTime(getTotalBurstTime() - ReadyQ.peek().getTimeBurst());
            str.append("\n|      " + ReadyQ.peek().getJobNo() + "     |      " + ReadyQ.peek().getTimeBurst()
                    + "     |      " + ReadyQ.peek().getTimeArrival() + "      |      "
                    + (systemTime) + "         |      " + (systemTime - ReadyQ.peek().getTimeArrival())
                    + "          |      "
                    + ((systemTime - ReadyQ.peek().getTimeArrival()) - ReadyQ.peek().getTimeBurst()) + "     |\n"
                    + "|------------+------------+--------------+-----------------+-----------------+--------------|\n");
            ReadyQ.remove();
            //If the process burst time is greater than the quantum, it will be process into multiple parts
        } else if (ReadyQ.peek().getremainingBurstTime() > quantum) {

            ReadyQ.peek().setremainingBurstTime(ReadyQ.peek().getTimeBurst() - quantum);
            systemTime += quantum;
            //Re inqueue the process
            ReadyQ.add(ReadyQ.poll());
        }
    }
    //Normal round robin method
    public void RoundRobinNormal(Queue<Process2> ReadyQ, PriorityQueue<Process2> HoldQ1p, Queue<Process2> HoldQ2,
            ArrayList<Config> PC, int quantum, StringBuffer str) {
        //Static quantum value 
        quantum = 10;


        //checks if the ready queue is empty
        if (ReadyQ.isEmpty()) {
            return;
        }

        if (ReadyQ.peek().getremainingBurstTime() <= quantum) {

            systemTime += ReadyQ.peek().getremainingBurstTime();
            ReadyQ.peek().setremainingBurstTime(0);

            str.append("\n|      " + ReadyQ.peek().getJobNo() + "     |      " + ReadyQ.peek().getTimeBurst()
                    + "     |      " + ReadyQ.peek().getTimeArrival() + "      |      "
                    + (systemTime) + "         |      " + (systemTime - ReadyQ.peek().getTimeArrival())
                    + "          |      "
                    + ((systemTime - ReadyQ.peek().getTimeArrival()) - ReadyQ.peek().getTimeBurst()) + "     |\n"
                    + "|------------+------------+--------------+-----------------+-----------------+--------------|\n");
            ReadyQ.remove();

        } else if (ReadyQ.peek().getremainingBurstTime() > quantum) {

            ReadyQ.peek().setremainingBurstTime(ReadyQ.peek().getTimeBurst() - quantum);
            systemTime += quantum;

            ReadyQ.add(ReadyQ.poll());
        }
    }

}
