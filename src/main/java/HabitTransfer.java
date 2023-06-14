import java.sql.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.FileWriter;
import java.io.IOException;


public class HabitTransfer {

	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		
		long timestampValue = 1619204950;
		Timestamp timestamp = new Timestamp(timestampValue * 1000);
		
		System.out.println(getRepetition(1,timestamp,1).getString("date"));
		
		
		
		//connection();
		
	}
	
	public static JSONObject getHabit(int id, String name) throws JSONException {
		JSONObject habit = new JSONObject();
		habit.put("orderIndex", 0);
		habit.put("lastUpdatedAt", "2023-04-24T20:05:35.423204");
		habit.put("createdAt", "2023-04-24T13:41:26.850664");
		habit.put("archived", false);
		habit.put("color", "slate");
		habit.put("icon", "dumbbell");
		habit.put("description", "null");
		habit.put("name", name);
		habit.put("id", id);
		return habit;
	}
	
	public static int transformHabit(JSONObject jo) {
		return 25;
	}
	
	public static JSONObject getRepetition(int id, Timestamp ts, int habitID) throws JSONException {
		JSONObject repetition = new JSONObject();
		Date date = new Date(ts.getTime());
		//System.out.println(id +"; " + ts + "; " + date);
		repetition.put("timezoneOffsetInMinutes", 120);
		repetition.put("habitId", habitID);
		repetition.put("date", date + "T00:00:00.000");
		repetition.put("id", id);
		return repetition;
	}
	
	public static JSONObject getInterval(int id) throws JSONException {
		JSONObject interval = new JSONObject();
		interval.put("requiredNumberOfCompletions", "null");
		interval.put("type", "none");
		interval.put("endDate", "null");
		interval.put("startDate", "2021-04-11T00:00:00.000");
		interval.put("habitId", id);
		interval.put("id", id);
		return interval;
	}
	
	public static void connection() {
		
		Connection conn = null;
		
		 try {
	            // Register JDBC driver
	            Class.forName("org.sqlite.JDBC");

	            // Open a connection
	            String url = "jdbc:sqlite:./lib/Loop Habits Backup.db";
	            conn = DriverManager.getConnection(url);

	            // Execute a query
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits");
	            JSONObject json = new JSONObject();
	            JSONArray habits = new JSONArray();
	            JSONArray repetitions = new JSONArray();
	            JSONArray intervals = new JSONArray();
	            
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                habits.put(getHabit(id,name));
	                intervals.put(getInterval(id));
	                
	            }
	            
	            rs = stmt.executeQuery("SELECT * FROM Repetitions");
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                int habitId = rs.getInt("habit");
	                Timestamp timestamp = rs.getTimestamp("timestamp");
	                repetitions.put(getRepetition(id, timestamp, habitId));
	            }
	            
	   		 	json.put("habits", habits);
	   		 	json.put("completions", repetitions);
	   		 	json.put("intervals", intervals);
	   		 	JSONArray reminders = new JSONArray();
	   		 	json.put("reminders", reminders);
	   		 	          
	            
	   		 	try (FileWriter fileWriter = new FileWriter("output.json")) {
		            fileWriter.write(json.toString());
	   		 	} catch (IOException e) {
		            e.printStackTrace();
		        }
	   		 	
	            // Clean-up environment
	            rs.close();
	            stmt.close();
	            conn.close();
	        } catch (ClassNotFoundException | SQLException | JSONException e) {
	            e.printStackTrace();
	        }

	}
	
	//Just a Test for Branch ausgabe-branch
	public static void ausgabe(int indikator) {
		switch(indikator) {
		case 1:
			System.out.println("1");
		case 2:
			System.out.println("2");
		case 3:
			System.out.println("3");
		case 4:
			System.out.println("4");
		}
	}
}
