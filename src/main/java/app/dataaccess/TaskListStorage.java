package app.dataaccess;

import app.model.DeadlineTask;
import app.model.EventTask;
import app.model.Task;
import app.model.TodoTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class TaskListStorage {
    public static List<Task> readTaskList(File taskListFile) throws FileNotFoundException, DataFormatException {
        if (!taskListFile.exists()) {
            throw new FileNotFoundException("Task List Not Found");
        }

        // load tasks from source file
        Scanner scanner = new Scanner(taskListFile);
        List<Task> taskList = new ArrayList<>();
        while (scanner.hasNext()) {
            String taskString = scanner.nextLine();
            taskParser(taskString, taskList);
        }

        return taskList;
    }


    /**
     * Parse each component of the task stored in source file, create corresponding task with these components and store the task in list.
     * Storage Pattern: Done/TaskType/TaskContent/Deadline(if applicable)/StartTime(if applicable)/EndTime(if applicable).
     * @param taskStorageStr the string representation of the task in persistent storage.
     */
    private static void taskParser(String taskStorageStr, List<Task> taskList) throws DataFormatException {
        String[] parts = taskStorageStr.split("/", 6);
        Boolean done = (Integer.parseInt(parts[0].trim(), 10) == 1);
        String taskContent = parts[2].trim();
        String deadline = parts[3].trim();
        String startTime = parts[4].trim();
        String endTime = parts[5].trim();

        Task taskToADD;

        switch (parts[1].trim()) {
            case "T":
                if (taskContent.isEmpty()) {
                    String errMsg = "Task Content Missing";
                    throw new DataFormatException(errMsg);
                }

                taskToADD = new TodoTask(taskContent, done);
                break;
            case "D":
                if (taskContent.isEmpty()) {
                    String errMsg = "Task Content Missing";
                    throw new DataFormatException(errMsg);
                } else if (deadline.isEmpty()) {
                    String errMsg =  "Deadline Missing";
                    throw new DataFormatException(errMsg);
                }

                taskToADD = new DeadlineTask(taskContent, deadline, done);
                break;
            case "E":
                if (taskContent.isEmpty()) {
                    String errMsg = "Task Content Missing";
                    throw new DataFormatException(errMsg);
                } else if (startTime.isEmpty()) {
                    String errMsg =  "Start Time Missing";
                    throw new DataFormatException(errMsg);
                } else if (endTime.isEmpty()) {
                    String errMsg =  "End Time Missing";
                    throw new DataFormatException(errMsg);
                }

                taskToADD = new EventTask(taskContent, startTime, endTime, done);
                break;
            default:
                throw new DataFormatException("Invalid Task Type");
        }

        taskList.add(taskToADD);
    }

    public static void writeTask(Task task, File taskListFile) throws IOException {
        FileWriter taskListWriter = new FileWriter(taskListFile, true);
        String taskStorageStr = task.printStorageString();

        taskListWriter.write(taskStorageStr + System.lineSeparator());
        taskListWriter.close();
    }

    public static void writeAllTasks(File taskListFile, List<Task> taskList) throws IOException {
        FileWriter taskListWriter = new FileWriter(taskListFile, false);

        for (Task task: taskList) {
            String taskStorageStr = task.printStorageString();
            taskListWriter.write(taskStorageStr + System.lineSeparator());
        }

        taskListWriter.close();
    }
}
