# Dynamic-Round-Robin
An Operating System project that compares two kinds of scheduling, Dynamic Round Robin VS Normal Round Robin.

To read all the details of the experiment you can click (here)[https://mohaddz.github.io/portfolio/projects/drr/].

1. Main class is called DRR
All the queues are imported as normal queues except HoldQ1 where its imported as priority queue.

2. Process2 class is the class where all the attributes for the process, and it has all the scheduling algorithm methods.

3. Config class where all the specs for the PC that is going to handle all the processes.

4. Comparator class where it contains one function and that is used in the priority queue to help sorting the priorities the queue based on the memeory units
that the process use.
