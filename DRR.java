import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DRR {



    public static void main(String[] args) throws FileNotFoundException {
        // Initialization
        Queue<Process2> SubmitQ = new LinkedList<>();
        ArrayList<Config> PC = new ArrayList<Config>();
        Queue<Process2> ReadyQ = new LinkedList<>();
        Queue<Process2> HoldQ2 = new LinkedList<>();
        PriorityQueue<Process2> HoldQ1p = new PriorityQueue<Process2>(5, new Comp());
        StringBuffer str = new StringBuffer();
        // New Dummy Object for using Process class
        Process2 p = new Process2();
        int quantum = 0;

        // Reading the input file
        File file = new File("C:\\Users\\Mohad\\Documents\\GitHub\\Round-Robin-Test\\in.txt");
        Scanner sc = new Scanner(file);
        sc.useDelimiter(" M=| S=| J=| R=| P=|\s|\r\n");
        while (sc.hasNext()) {
            String var = sc.next();
            if (var.charAt(0) == 'C') {
                Config pc1 = new Config(Integer.valueOf(sc.next()), Integer.valueOf(sc.next()),
                        Integer.valueOf(sc.next()));
                PC.add(pc1);
            } else if (var.equals("A")) {
                Process2 p1 = new Process2(Integer.valueOf(sc.next()), Integer.valueOf(sc.next()),
                        Integer.valueOf(sc.next()), Integer.valueOf(sc.next()), Integer.valueOf(sc.next()),
                        Integer.valueOf(sc.next()));
                SubmitQ.add(p1);
            } else if (var.equals("D")) {

                //Reading user input
                Scanner in = new Scanner(System.in);
                System.out.println("Choose Scheduling Type:\n1. Dynamic Round Robin (D)\n2. Normal Round Robin (N)\nInput (D/N): ");

                Character choice = in.next().charAt(0);
                p.setsystemTime(0);

                //Switch case for user input
                switch (choice) {
                    //Dynamic Round Robin
                    case 'D':
                        //Checks if all queues are empty
                        while (!SubmitQ.isEmpty() || !HoldQ1p.isEmpty() || !HoldQ2.isEmpty() || !ReadyQ.isEmpty()) {
                            //Checks if submit is empty
                            if (!SubmitQ.isEmpty()) {
                                //Checks if the process arrived on the system time
                                if (SubmitQ.peek().getTimeArrival() > p.getsystemTime()
                                        && SubmitQ.peek().getMemUnit() <= PC.get(0).getremainingMEM()
                                        && SubmitQ.peek().getDevices() <= PC.get(0).getremainingDEV())
                                    p.setsystemTime(p.getsystemTime() + 1);                                 //If not process arrived, the system time is increamented

                                //Check for all processes arrived during the previous process was being processed
                                while (SubmitQ.peek().getTimeArrival() <= p.getsystemTime()) {
                                    //Moving the processes to the Ready queue
                                    p.MoveToReadyQ(ReadyQ, HoldQ1p, HoldQ2, PC, SubmitQ, SubmitQ.peek());
                                    if (SubmitQ.isEmpty()) {
                                        break;
                                    }
                                }
                            }
                            //Round robin
                            if (!ReadyQ.isEmpty()) {
                                p.RoundRobin(ReadyQ, HoldQ1p, HoldQ2, PC, quantum, str);
                            }

                            if (!HoldQ1p.isEmpty()) {                                                   //If hold queue 1 has processes that are able to move to the ready queue
                                p.MoveToReadyQ(ReadyQ, HoldQ1p, HoldQ2, PC, SubmitQ, HoldQ1p.peek());
                            } else if (!HoldQ2.isEmpty()) {                                             //If hold queue 2 has processes that are able to move to the ready queue
                                p.MoveToReadyQ(ReadyQ, HoldQ1p, HoldQ2, PC, SubmitQ, HoldQ2.peek());
                            }
                        }
                        display(str);                                                                   //Printing the output
                        break;
                    case 'N':
                        //Normal Round Robin
                        while (!SubmitQ.isEmpty()) {                                                    //if submit queue is empty or not
                            while (SubmitQ.peek().getTimeArrival() > p.getsystemTime()) {               //Checks if the process arrived on the system time
                                p.setsystemTime(p.getsystemTime() + 1);                                 //If not process arrived, the system time is increamented
                            }
                            //Calling normal Round Robin method
                            p.RoundRobinNormal(SubmitQ, HoldQ1p, HoldQ2, PC, quantum, str);

                        }
                        display(str);                                                                   //Printing the output
                        break;

                }

            }

        }
        sc.close();
    }

    //Disply method that will print all the processes
    public static void display(StringBuffer str) {

        String table = " -------------------------------------------------------------------------------------------\n"
                + "|  Process   | Burst Time | Arrival Time | Completion Time | Turnaround Time | Waiting Time |\n"
                + "|------------+------------+--------------+-----------------+-----------------+--------------|";
        System.out.println(table);
        System.out.println(str);

    }

}
