/*
 Name: Niraj Ojha
Date: 04/18/2024
Class: CSCI 308 Spring Semester
 */
package lab6_taskscheduling_ojha;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UserInterface_Ojha 
{
   public List<TaskScheduler_Ojha.Task> loadTasks(String filename) 
   {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("Input"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(selectedFile)) 
            {
                List<TaskScheduler_Ojha.Task> tasks = new ArrayList<>();
                while (scanner.hasNextLine())
                {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",\\s*");
                    if (parts.length == 4)
                    {
                        String name = parts[0];
                        int startTime = Integer.parseInt(parts[1]);
                        int endTime = Integer.parseInt(parts[2]);
                        int profit = Integer.parseInt(parts[3]);
                        tasks.add(new TaskScheduler_Ojha.Task(name, startTime,
                                endTime, profit));
                    }
                }
                return tasks.isEmpty() ? Collections.emptyList() : tasks;
            } 
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "File not found: " + 
                        selectedFile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else 
        {
            JOptionPane.showMessageDialog(null, "No file selected.", "Warning", 
                    JOptionPane.WARNING_MESSAGE);
            return null; // Indicates no file was selected
        }
        return Collections.emptyList();  // Empty list means the file was empty
    }

    public void displayScheduledTasks(List<TaskScheduler_Ojha.Task> tasks) 
    {
        if (tasks == null || tasks.isEmpty()) 
        {
            System.out.println("No tasks scheduled.");
            return;
        }
    
        // Define the path to the output file
        File outputFile = new File("Output/output.txt");

        // Try-with-resources to ensure the PrintWriter is closed after use
        try (PrintWriter out = new PrintWriter(outputFile))
        {
            // Write tasks to the file
            int totalProfit = 0;
            for (TaskScheduler_Ojha.Task task : tasks) 
            {
                out.printf("Task: %s | Start Time: %d | End Time: %d | Profit: %d%n", 
                task.getName(), task.getStartTime(), task.getEndTime(), 
                task.getProfit());
                totalProfit += task.getProfit();
            }
        
            // Write total profit to the file
            out.printf("Total Profit: %d%n", totalProfit);

            // Notify on the console where the results were written
            System.out.println("Results written to output file");
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error writing to file: " + 
                    outputFile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void writeScheduleToFile(List<List<TaskScheduler_Ojha.Task>> scheduledTasks) 
    {
        try 
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Output/output.txt"));
            
            final int columnWidth = 12;

            // Write the "Time" header
            writer.write(String.format("%-" + columnWidth + "s", "Time"));
            for (int i = 0; i <= 9; i++) 
            {
                writer.write(String.format("%-" + columnWidth + "s", i));
            }
            writer.newLine();

            // Write each machine's tasks
            for (int machine = 0; machine < scheduledTasks.size(); machine++) 
            {
                writer.write(String.format("%-" + columnWidth + "s", "M" + (machine + 1))); // Machine number

                int lastEndTime = 0;
                for (int time = 0; time <= 9; time++) 
                {
                    TaskScheduler_Ojha.Task taskAtTime = findTaskAtTime(scheduledTasks.get(machine), time);
                    if (taskAtTime != null)
                    {
                        writer.write(String.format("%-" + columnWidth + "s", taskAtTime.getName()));
                    } 
                    else 
                    {
                        writer.write(String.format("%-" + columnWidth + "s", ""));
                    }
                }
                writer.newLine();
            }

            // Calculate and write the total profit
            int totalProfit = scheduledTasks.stream()
                                        .flatMap(List::stream)
                                        .mapToInt(TaskScheduler_Ojha.Task::getProfit)
                                        .sum();

            writer.write(String.format("%-" + columnWidth + "s", "Total Profit:") + totalProfit + "pts");
            writer.newLine();

            writer.close();
        
            System.out.println("Schedule written to 'Output/output.txt'");

        } 
        catch (IOException e) 
        {
            System.err.println("An error occurred when writing to the output file.");
            e.printStackTrace();
        }
    }
    
    private TaskScheduler_Ojha.Task findTaskAtTime(List<TaskScheduler_Ojha.Task> tasks, int time) 
    {
        for (TaskScheduler_Ojha.Task task : tasks)
        {
            if (time >= task.getStartTime() && time < task.getEndTime()) 
            {
                return task;
            }
        }
        return null; // No task at the given time
    }
}
