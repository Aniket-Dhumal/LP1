import java.util.*;

class ShedulingAlgo {

    int AT, BT, PID, CT, TAT, WT, Priority;
    float avg_TAT, avg_WT;

    static int pCount;
    int ps_count;

    ShedulingAlgo Processes[];

    ShedulingAlgo() {
        AT = BT = PID = CT = TAT = WT = pCount = 0;
        avg_WT = avg_TAT = 0.0f;
    }

    ShedulingAlgo(int AT, int BT, int PID) {

        this.AT = AT;
        this.BT = BT;
        this.PID = PID;
    }

    ShedulingAlgo(int AT, int BT, int PID, int Priority) {

        this.AT = AT;
        this.BT = BT;
        this.PID = PID;
        this.Priority = Priority;
    }

    void input() {

        System.out.println("Enter the Number of Processes you want to Schedule: ");
        Scanner sc = new Scanner(System.in);
        ps_count = sc.nextInt();

        Processes = new ShedulingAlgo[ps_count];

        for (int i = 0; i < ps_count; i++) {

            System.out.println("Enter ARoundRobinival Time of PS" + (i + 1) + ": ");
            AT = sc.nextInt();
            System.out.println("Enter Burst Time of PS" + (i + 1) + ": ");
            BT = sc.nextInt();
           

            PID = ++pCount;

            Processes[i] = new ShedulingAlgo(AT, BT, PID);

        }
        sc.close();

        
        RoundRobin();

    }

    void FCFS() {

        // sorting - AT

        int prev_CT = 0;

        for (int i = 0; i < ps_count; i++) {

            for (int j = i + 1; j < ps_count; j++) {

                if (Processes[i].AT > Processes[j].AT) {

                    ShedulingAlgo temp = Processes[i];
                    Processes[i] = Processes[j];
                    Processes[j] = temp;
                }
            }
        }

        for (int i = 0; i < ps_count; i++) {

            Processes[i].CT = prev_CT + Processes[i].BT;
            prev_CT = Processes[i].CT;

            Processes[i].TAT = Processes[i].CT - Processes[i].AT;

            Processes[i].WT = Processes[i].TAT - Processes[i].BT;
        }

        System.out.println("  PID \t AT \t BT \t CT \t TAT \t WT ");

        for (int i = 0; i < ps_count; i++) {

            System.out.println(Processes[i].PID + "\t" + Processes[i].AT + "\t" + Processes[i].BT + "\t"
                    + Processes[i].CT + "\t" + Processes[i].TAT + "\t" + Processes[i].WT);

            avg_TAT += Processes[i].TAT;
            avg_WT += Processes[i].WT;
        }

        System.out.println("\n\nAverage Waiting Time is: " + (avg_WT / ps_count));
        System.out.println("Average Turn Around Time is: " + (avg_TAT / ps_count));

    }

    void SJF() {

        // sorting - AT

        int prev_CT = 0;

        for (int i = 0; i < ps_count; i++) {

            for (int j = i + 1; j < ps_count; j++) {

                if (Processes[i].AT > Processes[j].AT) {

                    ShedulingAlgo temp = Processes[i];
                    Processes[i] = Processes[j];
                    Processes[j] = temp;
                }
            }
        }

        int index = 0;

        // sorting - BT based on AT

        for (int i = 0; i < ps_count; i++) {

            for (int j = i + 1; j < ps_count; j++) {

                if (Processes[i].AT == Processes[j].AT) {
                    index = j;
                }
            }

            for (int i1 = i; i1 <= index; i1++) {

                for (int j1 = i1 + 1; j1 <= index; j1++) {

                    if (Processes[i1].BT > Processes[j1].BT) {

                        ShedulingAlgo temp = Processes[i1];
                        Processes[i1] = Processes[j1];
                        Processes[j1] = temp;
                    }
                }
            }

        }

        boolean PTerminated[] = new boolean[ps_count];

        for (int i = 0; i < ps_count; i++) {
            PTerminated[i] = false;
        }

        for (int i = 0; i < ps_count; i++) {

            Processes[i].CT = prev_CT + Processes[i].BT;

            System.out.println("CT: " + Processes[i].CT);

            for (int j = i + 1; j < ps_count; j++) {

                if (Processes[i].CT > Processes[j].AT && Processes[i].AT != Processes[j].AT && PTerminated[j] != true) {

                    Processes[i].CT += Processes[j].BT;
                    PTerminated[j] = true;
                }

            }
            prev_CT = Processes[i].CT;

            if (i == ps_count - 1) {
                Processes[i].CT = Processes[i].BT + Processes[i].AT;
            }

            Processes[i].TAT = Processes[i].CT - Processes[i].AT;
            Processes[i].WT = Processes[i].TAT - Processes[i].BT;

        }

        System.out.println("  PID \t AT \t BT \t CT \t TAT \t WT ");

        for (int i = 0; i < ps_count; i++) {

            System.out.println(Processes[i].PID + "\t" + Processes[i].AT + "\t" + Processes[i].BT + "\t"
                    + Processes[i].CT + "\t" + Processes[i].TAT + "\t" + Processes[i].WT);

            avg_TAT += Processes[i].TAT;
            avg_WT += Processes[i].WT;
        }

        System.out.println("\n\nAverage Waiting Time is: " + (avg_WT / ps_count));
        System.out.println("Average Turn Around Time is: " + (avg_TAT / ps_count));

    }

    void Priority() {

        boolean PTerminated[] = new boolean[ps_count];

        for (int i = 0; i < ps_count; i++) {
            PTerminated[i] = false;
        }

        // sorting AT

        int prev_CT = 0;
        int index = 0;

        for (int i = 0; i < ps_count; i++) {

            for (int j = i + 1; j < ps_count; j++) {

                if (Processes[i].AT > Processes[j].AT) {

                    ShedulingAlgo temp = Processes[i];
                    Processes[i] = Processes[j];
                    Processes[j] = temp;
                }
            }
        }

        // sorting Priority based on AT

        for (int i = 0; i < ps_count; i++) {

            for (int j = i + 1; j < ps_count; j++) {

                if (Processes[i].AT == Processes[j].AT) {
                    index = j;
                }
            }

            for (int i1 = i; i1 <= index; i1++) {

                for (int j1 = i1 + 1; j1 <= index; j1++) {

                    if (Processes[i1].Priority > Processes[j1].Priority) {

                        ShedulingAlgo temp = Processes[i1];
                        Processes[i1] = Processes[j1];
                        Processes[j1] = temp;
                    }
                }

            }

        }

        for (int i = 0; i < ps_count; i++) {

            Processes[i].CT = prev_CT + Processes[i].BT;
            PTerminated[i] = true;

            Processes[i].TAT = Processes[i].CT - Processes[i].AT;
            Processes[i].WT = Processes[i].TAT - Processes[i].BT;

            prev_CT = Processes[i].CT;

            for (int i1 = i; i1 < ps_count; i1++) {

                for (int j1 = i1 + 1; j1 < ps_count; j1++) {

                    if (Processes[i1].Priority > Processes[j1].Priority && PTerminated[i1] != true) {

                        ShedulingAlgo temp = Processes[i1];
                        Processes[i1] = Processes[j1];
                        Processes[j1] = temp;
                    }
                }

            }

        }

        System.out.println("  PID \t AT \t BT \t Priority \t CT \t TAT \t WT ");

        for (int i = 0; i < ps_count; i++) {

            System.out.println(Processes[i].PID + "\t" + Processes[i].AT + "\t" + Processes[i].BT + "\t" +
                    Processes[i].Priority + "\t\t" + Processes[i].CT + "\t" + Processes[i].TAT + "\t"
                    + Processes[i].WT);

            avg_TAT += Processes[i].TAT;
            avg_WT += Processes[i].WT;
        }

        System.out.println("\n\nAverage Waiting Time is: " + (avg_WT / ps_count));
        System.out.println("Average Turn Around Time is: " + (avg_TAT / ps_count));

    }

    void RoundRobin() {

        int time_slice = 10;
        int prev_CT = 0;

        int PsBT[] = new int[ps_count];

        for(int i=0;i<ps_count;i++){
            PsBT[i] = Processes[i].BT;
        }

        // sorting AT

        for (int i = 0; i < ps_count; i++) {

            for (int j = i + 1; j < ps_count; j++) {

                if (Processes[i].AT > Processes[j].AT) {

                    ShedulingAlgo temp = Processes[i];
                    Processes[i] = Processes[j];
                    Processes[j] = temp;
                }
            }
        }

        int i = 0;
        int count = 0;

        while (true) {


            if (Processes[i].BT > time_slice){

                Processes[i].CT = prev_CT + time_slice;
                Processes[i].BT -= time_slice;

                prev_CT +=time_slice;
            }

            else if(Processes[i].BT <= time_slice && Processes[i].BT!=0){

                Processes[i].CT = prev_CT + Processes[i].BT;
                Processes[i].BT = 0; 

                count ++;

                prev_CT = Processes[i].CT;

            }
            if(count == ps_count)
                break;

            i = (i+1)%ps_count;
        }

        for(int j=0;j<ps_count;j++){

            Processes[j].BT = PsBT[j]; 
            
            Processes[j].TAT = Processes[j].CT - Processes[j].AT;
            Processes[j].WT = Processes[j].TAT - Processes[j].BT;
        }

        System.out.println("  PID \t AT \t BT \t CT \t TAT \t WT ");

        for (int j = 0; j < ps_count; j++) {

            System.out.println(Processes[j].PID + "\t" + Processes[j].AT + "\t" + Processes[j].BT + "\t"
                    + Processes[j].CT + "\t" + Processes[j].TAT + "\t" + Processes[j].WT);

            avg_TAT += Processes[j].TAT;
            avg_WT += Processes[j].WT;
        }

        System.out.println("\n\nAverage Waiting Time is: " + (avg_WT / ps_count));
        System.out.println("Average Turn Around Time is: " + (avg_TAT / ps_count));

    }

}

public class lp1 {

    public static void main(String[] args) {

        ShedulingAlgo a = new ShedulingAlgo();
        a.input();
    }
}