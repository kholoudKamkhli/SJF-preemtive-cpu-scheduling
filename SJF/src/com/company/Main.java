package com.company;

import java.util.ArrayList;
import java.util.Scanner;

import static com.company.Process.*;

class Process{
    String name;
    int ArrivalTime;
    int BrustTime;

    Process(String name, int ArrivalTime , int BrustTime )
    {
        this.ArrivalTime=ArrivalTime;
        this.BrustTime=BrustTime;
        this.name=name;

    }
                                    // Preemptive SJF

    public static void calculateWaitingTime(ArrayList<Process> p, int n, int waitingTime[],int remainingTime[], int contextSwitch) {
        ArrayList<Integer> executionArr = new ArrayList<>();

        Process current = null;
        int time = 0, minimum = 1000000000, indexOfmin = 0, finish_time;
        boolean flag = false;

        for(int j=0;j<n;){
            for (int i = 0; i < n; i++) {
                if ((p.get(i).ArrivalTime <= time)  &&  (remainingTime[i] < minimum)  &&  remainingTime[i] > 0) {
                    minimum = remainingTime[i];
                    indexOfmin = i;
                    flag = true;
                }
            }

            if (flag == false) {
                time+=1;
                continue;
            }
            if (current != null && p.get(indexOfmin)!= current) {
                time += contextSwitch;

                current = null;
                continue;
            } else if (current == null)
                executionArr.add(indexOfmin + 1);

            remainingTime[indexOfmin]--;
            current = p.get(indexOfmin);

            minimum = remainingTime[indexOfmin];
            if (minimum == 0)
                minimum = 1000000000;

            if (remainingTime[indexOfmin] == 0) {


                j++;
                flag = false;

                finish_time = time + 1;

                waitingTime[indexOfmin] = (int) (finish_time - p.get(indexOfmin).BrustTime -p.get(indexOfmin).ArrivalTime);
                time += contextSwitch;
                current = null;

            }

            time++;
        }

        System.out.println("Execution Order : ");
        for (int i = 0; i < executionArr.size(); i++)

            System.out.println(executionArr.get(i));

    }




}

public class Main {
    public static void calculate(ArrayList<Process> p, int n, int contextSwitch) {

        int waitingTime[] = new int[n];

        int remainingTime[] = new int[n];

        for (int i = 0; i < n; i++)
            remainingTime[i] = (int) p.get(i).BrustTime;

        double totalWaitingTime = 0, totalTurnaroundTime = 0;

        calculateWaitingTime(p, n, waitingTime, remainingTime, contextSwitch);


        System.out.println("Processes \t" + " Waiting time \t" + "Turnaround time" );

        for (int i = 0; i < n; i++) {
            totalWaitingTime +=waitingTime[i];
            totalTurnaroundTime += p.get(i).BrustTime+waitingTime[i];
            System.out.println(p.get(i).name + "\t\t\t"  + waitingTime[i] + "\t\t\t\t" + (p.get(i).BrustTime+waitingTime[i]) + "\t\t\t\t\t");

        }

        System.out.println("Average waiting time = " + totalWaitingTime / n);
        System.out.println("Average turn around time = " + totalTurnaroundTime / n);
    }
    public static void main(String[] args) {
	// write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of Processes: ");
        int numOfPrcoesses = scanner.nextInt();
        //Process p[] = new Process[numOfPrcoesses];
        Process temp=null;
        ArrayList<Process> p=new ArrayList<>();
        for (int i = 0; i < numOfPrcoesses; i++) {
            System.out.print("Enter the name of Process " + (i + 1) + ": ");
            String ProcessName = scanner.next();
            System.out.print("Enter the arrival time of Process " + (i + 1) + ":");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter the burst time of Process " + (i + 1) + ":");
            int burstTime = scanner.nextInt();
            temp= new Process(ProcessName, arrivalTime, burstTime);
            p.add(temp);
        }
        System.out.print("Enter context switch: ");
        int contextSwitch = scanner.nextInt();
        calculate(p, numOfPrcoesses, contextSwitch);
    }
}
