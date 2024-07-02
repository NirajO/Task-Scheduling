/*
Name: Niraj Ojha
Date: 04/18/2024
Class: CSCI 308 Spring Semester
 */
package lab6_taskscheduling_ojha;

import java.util.*;
import java.io.*;

public class Lab6_TaskScheduling_Ojha 
{
    public static void main(String[] args) 
    {
        new File("Input").mkdir();
        new File("Output").mkdir();
        UserInterface_Ojha ui = new UserInterface_Ojha();
        TaskScheduler_Ojha scheduler = new TaskScheduler_Ojha(3);
    
        ErrorChecker_Ojha errorChecker = new ErrorChecker_Ojha();

        List<TaskScheduler_Ojha.Task> tasks = ui.loadTasks("Input_Ojha.txt");
    
        // First, check if a file was selected
        if (tasks == null) 
        {
            System.out.println("No file was selected. Please select a file and try again.");
        } else if (!errorChecker.isValidTaskList(tasks))
        {
            // If a file was selected but the task list is not valid, report the specific validation error
            // The error messages are printed inside the isValidTaskList method
        } else 
        {
            // If the file is selected and the task list is valid, proceed with scheduling
            scheduler.scheduleTasks(tasks);
            List<List<TaskScheduler_Ojha.Task>> machineSchedules = scheduler.getMachineSchedules();
            ui.writeScheduleToFile(machineSchedules);
        }
    }
}
