package control;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import model.Task;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class taskControl {
    private static final String TASK_DIR = "src/storage/Task";

    public static void setTask(Task task, String username) throws IOException {
        File taskFile = new File(TASK_DIR, username + ".csv");
        boolean fileExists = taskFile.exists();
        CsvWriter csvWriter = new CsvWriter(new FileWriter(taskFile, true), ',');
        if (!fileExists) {
            String[] headers = {"TaskID", "TaskDescription", "TaskStatus", "RewardAmount"};
            csvWriter.writeRecord(headers);
        }
        String[] taskInfo = {task.getTaskID(), task.getTaskDescription(), task.getTaskStatus(), String.valueOf(task.getRewardAmount())};
        csvWriter.writeRecord(taskInfo);
        csvWriter.close();
    }

    public static List<Task> getTasksByUsername(String username) throws IOException {
        File taskFile = new File(TASK_DIR, username + ".csv");
        boolean fileExists = taskFile.exists();
        if (!fileExists) {
            return null;
        }
        CsvReader csvReader = new CsvReader(new FileReader(taskFile), ',');
        csvReader.readHeaders(); // skip header
        List<Task> tasks = new ArrayList<>();
        while (csvReader.readRecord()) {
            Task task = new Task(csvReader.get("TaskID"), csvReader.get("TaskDescription"), csvReader.get("TaskStatus"), Double.parseDouble(csvReader.get("RewardAmount")));
            tasks.add(task);
        }
        csvReader.close();
        return tasks;
    }

    public static void finishTask(Task task, String username) throws IOException {
        File taskFile = new File(TASK_DIR, username + ".csv");
        List<String[]> allElements = new ArrayList<>();
        CsvReader csvReader = new CsvReader(new FileReader(taskFile), ',');
        csvReader.readHeaders(); // skip header
        while (csvReader.readRecord()) {
            allElements.add(csvReader.getValues());
        }
        csvReader.close();

        // Find the task and set its status to "Completed"
        for (String[] strings : allElements) {
            if (strings[0].equals(task.getTaskID()) && strings[1].equals(task.getTaskDescription()) && Double.parseDouble(strings[3]) == task.getRewardAmount()) {
                strings[2] = "Completed";
                break;
            }
        }

        // Write the updated task status back to task.csv
        CsvWriter csvWriter = new CsvWriter(new FileWriter(taskFile, false), ',');
        csvWriter.writeRecord(new String[]{"TaskID", "TaskDescription", "TaskStatus", "RewardAmount"});
        for (String[] strings : allElements) {
            csvWriter.writeRecord(strings);
        }
        csvWriter.close();
    }


}
