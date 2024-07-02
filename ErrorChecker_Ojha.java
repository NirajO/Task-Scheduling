/*
Name: Niraj Ojha
Date: 04/18/2024
Class: CSCI 308 Spring Semester
 */
package lab6_taskscheduling_ojha;

import java.util.*;
public class ErrorChecker_Ojha 
{
    public boolean fileWasSelected(List<TaskScheduler_Ojha.Task> tasks) 
    {
        return tasks != null;
    }

    public boolean isValidTaskList(List<TaskScheduler_Ojha.Task> tasks) 
    {
        // Check if the task list contains exactly 10 tasks
        if (tasks == null || tasks.size() != 10) 
        {
            System.out.println("Error: The task list must contain exactly 10 tasks.");
            return false;
        }

        Set<String> taskNames = new HashSet<>();
        
        for (TaskScheduler_Ojha.Task task : tasks) 
        {
            // Ensure task names are unique
            if (!taskNames.add(task.getName())) 
            {
                System.out.println("Error: Duplicate task name found: " + task.getName());
                return false;
            }

            // Check that start time is before end time
            if (task.getStartTime() >= task.getEndTime()) 
            {
                System.out.println("Error: Task start time must be before its end time for task: " +
                        task.getName());
                return false;
            }

            // Validate points (assuming points are represented by the profit field)
            if (task.getProfit() <= 0 || task.getProfit() > 100) 
            {
                System.out.println("Error: Task points must be greater than 0 "
                        + "and less than or equal to 100 for task: " + task.getName());
                return false;
            }
        }
        // If all checks pass
        return true;
    }
}
