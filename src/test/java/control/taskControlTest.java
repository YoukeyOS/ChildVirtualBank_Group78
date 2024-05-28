package control;

import model.Task;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.List;

public class taskControlTest {

    @Test
    public void testSetTask() throws IOException {
        // Arrange
        Task task = new Task("1", "Task 1", "Active", 10.0);
        String username = "john";

        // Act
        taskControl.setTask(task, username);

        // Assert
        List<Task> tasks = taskControl.getTasksByUsername(username);
        Assertions.assertNotNull(tasks);
        
        Task savedTask = tasks.get(0);
        Assertions.assertEquals(task.getTaskID(), savedTask.getTaskID());
        Assertions.assertEquals(task.getTaskDescription(), savedTask.getTaskDescription());
        Assertions.assertEquals(task.getTaskStatus(), savedTask.getTaskStatus());
        Assertions.assertEquals(task.getRewardAmount(), savedTask.getRewardAmount());
    }

  

    @Test
    public void testFinishTask() throws IOException {
        // Arrange
        Task task = new Task("1", "Task 1", "Active", 10.0);
        String username = "john";
        taskControl.setTask(task, username);

        // Act
        taskControl.finishTask(task, username);

        // Assert
        List<Task> tasks = taskControl.getTasksByUsername(username);
        Assertions.assertNotNull(tasks);
        
        Task finishedTask = tasks.get(0);
        Assertions.assertEquals(task.getTaskID(), finishedTask.getTaskID());
        Assertions.assertEquals(task.getTaskDescription(), finishedTask.getTaskDescription());
        Assertions.assertEquals("Completed", finishedTask.getTaskStatus());
        Assertions.assertEquals(task.getRewardAmount(), finishedTask.getRewardAmount());
    }
}