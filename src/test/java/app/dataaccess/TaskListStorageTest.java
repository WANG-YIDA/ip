package app.dataaccess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import app.models.task.StubTodoTask;
import app.models.task.Task;

public class TaskListStorageTest {
    @Test
    public void writeAllTasks_withNotEmptyList() throws Exception {
        File tmp = Files.createTempFile("taskList", ".txt").toFile();
        Files.write(tmp.toPath(), Arrays.asList("old-line"));

        List<Task> tasks = Arrays.asList(
                new StubTodoTask("first", false),
                new StubTodoTask("second", true)
        );

        TaskListStorage.writeAllTasks(tmp, tasks);

        List<String> lines = Files.readAllLines(tmp.toPath());
        assertEquals(2, lines.size());
        assertEquals(tasks.get(0).printStorageString(), lines.get(0));
        assertEquals(tasks.get(1).printStorageString(), lines.get(1));

        tmp.delete();
    }

    @Test
    public void writeAllTasks_withEmptyList() throws Exception {
        File tmp = Files.createTempFile("taskList", ".txt").toFile();
        Files.write(tmp.toPath(), Arrays.asList("old-line"));

        TaskListStorage.writeAllTasks(tmp, Collections.emptyList());

        List<String> lines = Files.readAllLines(tmp.toPath());
        assertEquals(0, lines.size());
    }
}
