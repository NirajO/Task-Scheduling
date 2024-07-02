/*
 Name: Niraj Ojha
Date: 04/18/2024
Class: CSCI 308 Spring Semester
 */
package lab6_taskscheduling_ojha;

import java.util.*;
import java.io.*;

public class TaskScheduler_Ojha
{
    // Stores the schedule for each machine
    private List<List<Task>> machineSchedules = new ArrayList<>();

     /**
     * Constructor to initialize the scheduler with a specific number of machines.
     * @param numberOfMachines The number of machines available for scheduling tasks.
     */
    public TaskScheduler_Ojha(int numberOfMachines)
    {
        // Initialize the schedules for each machine
        for (int i = 0; i < numberOfMachines; i++) 
        {
            machineSchedules.add(new ArrayList<>());
        }
    }
    
    /**
     * Schedules the provided list of tasks across the available machines.
     * Tasks are sorted by end time to prioritize tasks with earlier deadlines.
     * @param tasks List of tasks to be scheduled.
     */
    public void scheduleTasks(List<Task> tasks) 
    {
        // Sort tasks based on end time in ascending order to prioritize tasks 
        //with earlier deadlines
        tasks.sort(Comparator.comparingInt(Task::getEndTime));

        // Initialize machine availability times
        int[] machineEndTimes = new int[machineSchedules.size()]; // Tracks when each machine will be free next

        for (Task task : tasks) 
        {
            // Find the first machine that will be available to handle this task
            int earliestMachine = findEarliestAvailableMachine(machineEndTimes, task.getStartTime());
            if (earliestMachine != -1) { // If a machine is available to start this task
                machineSchedules.get(earliestMachine).add(task);
                // Update the machine's next available time
                machineEndTimes[earliestMachine] = task.getEndTime();
            }
        }
    }

    /**
     * Finds the earliest available machine that can start the task at the specified start time.
     * @param machineEndTimes Array tracking when each machine will be free.
     * @param taskStartTime The start time of the task.
     * @return The index of the first available machine or -1 if no machine is available.
     */
    private int findEarliestAvailableMachine(int[] machineEndTimes, int taskStartTime) 
    {
        for (int i = 0; i < machineEndTimes.length; i++) 
        {
            if (machineEndTimes[i] <= taskStartTime) 
            {
                return i; // Machine is available to start this task
            }
        }
        return -1; // No machine available to start this task at its start time
    }

    /**
     * Returns the schedules for all machines.
     * @return List of lists, each containing tasks scheduled on a machine.
     */
    public List<List<Task>> getMachineSchedules() 
    {
        return machineSchedules;
    }
    
    /**
     * Nested static class representing a task with a name, start time, end time, and profit.
     */
     public static class Task implements Comparable<Task> 
    {
        private final String name;
        private final int startTime;
        private final int endTime;
        private final int profit;

        /**
         * Constructs a new Task.
         * @param name Name of the task.
         * @param startTime Start time of the task.
         * @param endTime End time of the task; must be greater than start time.
         * @param profit Profit associated with completing the task.
         */
        public Task(String name, int startTime, int endTime, int profit) 
        {
            if (name == null || name.isEmpty()) 
            {
                throw new IllegalArgumentException("Task name cannot be null or empty.");
            }
            if (startTime < 0) {
                throw new IllegalArgumentException("Start time cannot be negative.");
            }
            if (endTime <= startTime) {
                throw new IllegalArgumentException("End time must be greater than start time.");
            }
            if (profit < 0) {
                throw new IllegalArgumentException("Profit cannot be negative.");
            }

            this.name = name;
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }

        // Getters
        public String getName() { return name; }
        public int getStartTime() { return startTime; }
        public int getEndTime() { return endTime; }
        public int getProfit() { return profit; }

         /**
         * Compares this task to another based on start time for sorting purposes.
         * @param other The other task to compare to.
         * @return Negative if this task starts earlier, zero if same time, positive if this starts later.
         */
        @Override
        public int compareTo(Task other) 
        {
            return Integer.compare(this.startTime, other.startTime);
        }

        /**
         * Returns a string representation of this task.
         * @return String that includes name, start time, end time, profit
         */
        @Override
        public String toString() 
        {
            return "Task{name='" + name + "', startTime=" + startTime + 
                    ", endTime=" + endTime + ", profit=" + profit + '}';
        }
    }
}
