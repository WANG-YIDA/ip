package app.dataaccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import app.models.tasks.Task;
import app.parsers.StorageStringParser;

/**
 * Helper to read and write Task list data from persistent storage.
 */
public class TaskListStorage {
    /**
     * Reads tasks from the provided file and returns them as a list.
     *
     * @param taskListFile file to read tasks from
     * @return list of tasks parsed from the file
     * @throws FileNotFoundException when the file does not exist
     * @throws DataFormatException   when the file contents are malformed
     */
    public static List<Task> readTaskList(File taskListFile) throws FileNotFoundException, DataFormatException {
        if (!taskListFile.exists()) {
            throw new FileNotFoundException("Task List Not Found");
        }

        // load tasks from source file
        Scanner scanner = new Scanner(taskListFile);
        List<Task> taskList = new ArrayList<>();
        while (scanner.hasNext()) {
            String taskStorageStr = scanner.nextLine();
            addTaskStorage(taskStorageStr, taskList);
        }

        return taskList;
    }

    /**
     * Parses a single storage-line representing a task and adds the parsed Task
     * to the provided task list.
     *
     * @param taskStorageStr a single line from the storage file representing a task
     * @param taskList       the list to which the parsed Task will be added
     * @throws DataFormatException if the storage string is corrupted or cannot be parsed
     */
    private static void addTaskStorage(String taskStorageStr, List<Task> taskList) throws DataFormatException {
        Task taskToAdd = StorageStringParser.parseTaskStorage(taskStorageStr);
        taskList.add(taskToAdd);
    }

    /**
     * Appends a single task to the given file.
     *
     * @param task         task to write
     * @param taskListFile destination file
     * @throws IOException when writing fails
     */
    public static void writeTask(Task task, File taskListFile) throws IOException {
        FileWriter taskListWriter = new FileWriter(taskListFile, true);
        String taskStorageStr = task.printStorageString();

        taskListWriter.write(taskStorageStr + System.lineSeparator());
        taskListWriter.close();
    }

    /**
     * Writes all provided tasks to the file, replacing any existing content.
     *
     * @param taskListFile file to write to
     * @param taskList     tasks to write
     * @throws IOException when writing fails
     */
    public static void writeAllTasks(File taskListFile, List<Task> taskList) throws IOException {
        FileWriter taskListWriter = new FileWriter(taskListFile, false);

        for (Task task : taskList) {
            String taskStorageStr = task.printStorageString();
            taskListWriter.write(taskStorageStr + System.lineSeparator());
        }

        taskListWriter.close();
    }
}
