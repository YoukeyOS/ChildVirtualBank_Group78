package control;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.SavingsGoal;

public class goalControlTest {
    private static final String TEST_USERNAME = "testUser";

    @Test
    public void testGenerateGoalID() {
        SavingsGoal goal = new SavingsGoal();
        goalControl.generateGoalID(goal);
        String goalID = goal.getGoalID();
        assertEquals(36, goalID.length());
    }

    @Test
    public void testSetGoal() throws IOException {
        SavingsGoal goal = new SavingsGoal();
        goal.setGoalID("testGoalID");
        goal.setAccountID("testAccountID");
        goal.setGoalAmount(1000.0);
        goal.setGoalStartAmount(500.0);
        goal.setGoalStatus("Active");

        goalControl.setGoal(goal, TEST_USERNAME);

        File goalFile = new File(goalControl.USER_DIR, TEST_USERNAME + "/goal.csv");
        List<SavingsGoal> goals = goalControl.getGoalsByUsername(TEST_USERNAME);
        assertEquals(1, goals.size());
        assertEquals(goal.getGoalID(), goals.get(0).getGoalID());
        assertEquals(goal.getAccountID(), goals.get(0).getAccountID());
        assertEquals(goal.getGoalAmount(), goals.get(0).getGoalAmount(), 0.001);
        assertEquals(goal.getGoalStartAmount(), goals.get(0).getGoalStartAmount(), 0.001);
        assertEquals(goal.getGoalStatus(), goals.get(0).getGoalStatus());

        // Clean up the test file
        goalFile.delete();
    }

    @Test
    public void testGetGoalsByUsername() throws IOException {
        SavingsGoal goal1 = new SavingsGoal();
        goal1.setGoalID("goal1");
        goal1.setAccountID("account1");
        goal1.setGoalAmount(1000.0);
        goal1.setGoalStartAmount(500.0);
        goal1.setGoalStatus("Active");

        SavingsGoal goal2 = new SavingsGoal();
        goal2.setGoalID("goal2");
        goal2.setAccountID("account2");
        goal2.setGoalAmount(2000.0);
        goal2.setGoalStartAmount(1000.0);
        goal2.setGoalStatus("Completed");

        goalControl.setGoal(goal1, TEST_USERNAME);
        goalControl.setGoal(goal2, TEST_USERNAME);

        List<SavingsGoal> goals = goalControl.getGoalsByUsername(TEST_USERNAME);
        assertEquals(2, goals.size());

        SavingsGoal retrievedGoal1 = goals.get(0);
        assertEquals(goal1.getGoalID(), retrievedGoal1.getGoalID());
        assertEquals(goal1.getAccountID(), retrievedGoal1.getAccountID());
        assertEquals(goal1.getGoalAmount(), retrievedGoal1.getGoalAmount(), 0.001);
        assertEquals(goal1.getGoalStartAmount(), retrievedGoal1.getGoalStartAmount(), 0.001);
        assertEquals(goal1.getGoalStatus(), retrievedGoal1.getGoalStatus());

        SavingsGoal retrievedGoal2 = goals.get(1);
        assertEquals(goal2.getGoalID(), retrievedGoal2.getGoalID());
        assertEquals(goal2.getAccountID(), retrievedGoal2.getAccountID());
        assertEquals(goal2.getGoalAmount(), retrievedGoal2.getGoalAmount(), 0.001);
        assertEquals(goal2.getGoalStartAmount(), retrievedGoal2.getGoalStartAmount(), 0.001);
        assertEquals(goal2.getGoalStatus(), retrievedGoal2.getGoalStatus());

        // Clean up the test file
        File goalFile = new File(goalControl.USER_DIR, TEST_USERNAME + "/goal.csv");
        goalFile.delete();
    }

    @Test
    public void testCheckGoalStatus() throws IOException {
        SavingsGoal goal1 = new SavingsGoal();
        goal1.setGoalID("goal1");
        goal1.setAccountID("account1");
        goal1.setGoalAmount(1000.0);
        goal1.setGoalStartAmount(500.0);
        goal1.setGoalStatus("Active");

        SavingsGoal goal2 = new SavingsGoal();
        goal2.setGoalID("goal2");
        goal2.setAccountID("account2");
        goal2.setGoalAmount(2000.0);
        goal2.setGoalStartAmount(1000.0);
        goal2.setGoalStatus("Active");

        List<SavingsGoal> goals = new ArrayList<>();
        goals.add(goal1);
        goals.add(goal2);

        goalControl.checkGoalStatus(goals, TEST_USERNAME);

        assertEquals("Completed", goals.get(0).getGoalStatus());
        assertEquals("Completed", goals.get(1).getGoalStatus());

        // Clean up the test file
        File goalFile = new File(goalControl.USER_DIR, TEST_USERNAME + "/goal.csv");
        goalFile.delete();
    }
}