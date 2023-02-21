package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import model.Management.eAddAnswer;

public class SqlConnector {
	private Connection conn = null;
	private Statement stmt;

	public SqlConnector(String userName , String password) throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver"); // start the driver
		String dbUrl = "jdbc:mysql://localhost:3306/exam_generator";
		conn = DriverManager.getConnection(dbUrl, userName, password); // the connection
		stmt = conn.createStatement();
	}

	public void close() throws SQLException {
		conn.close();
	}

	public boolean updateQuestion(int qId, String text) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT questionType FROM questionTable WHERE questionId = '"+qId+"'");
			rs.next();
			String type = rs.getString("questionType");
			rs.close();
			if(checkIfQuestionExist(text,type))
				return false;
			String s = "UPDATE questionTable SET questionText = '" + text + "' WHERE questionId = '" + qId + "'";
			stmt.executeUpdate(s);
			return true;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return false;
		}
	}


	public boolean checkIfQuestionExist(String text, String type) throws SQLException {
		ResultSet rs;
		rs = stmt.executeQuery("SELECT questionText FROM questionTable WHERE questionText = '"+text+"' AND questionType = '"+type+"'");
		return rs.next();

	}

	public void addOpenQuestion(int id, String text, String corecctAnswer) throws SQLException {
		String s1 = "INSERT INTO questionTable VALUES ('" + id + "','" + text + "','open')";
		addAnswerToTable(corecctAnswer);
		String s2 = "INSERT INTO openQuestionTable VALUES ('" + id + "','" + corecctAnswer + "')";
		stmt.executeUpdate(s1);
		stmt.executeUpdate(s2);
	}

	public void addCloseQuestion(int id, String text) throws SQLException {
		String s1 = "INSERT INTO questionTable VALUES ('" + id + "','" + text + "','close')";
		String s2 = "INSERT INTO closeQuestionTable VALUES ('" + id + "',0)";
		stmt.executeUpdate(s1);
		stmt.executeUpdate(s2);
	}

	public eAddAnswer addAnswerToCloseQuestion(int cQId, String ansText, boolean indication) {
		int ind = 0; // false
		if (indication)
			ind = 1; // true
		try {
			if (checkIfAnswerInCQ(cQId, ansText))
				return eAddAnswer.Already_Exist;
			String s1 = "INSERT INTO closeQuestionsAnswers VALUES ('" + cQId + "', '" + ansText + "' ,'" + ind + "')";
			stmt.executeUpdate(s1);
			String s2 = "UPDATE closeQuestionTable SET numOfAnswers = numOfAnswers + 1 WHERE CQID = '" + cQId + "'";
			stmt.executeUpdate(s2);
			return eAddAnswer.Added_Successfully;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return eAddAnswer.Error_In_SQL;
		}

	}

	public void addAnswerToTable(String ansText) {
		if (checkIfAnswerInTable(ansText))
			return;
		String s1 = "INSERT INTO answerTable VALUES ('" + ansText + "')";
		try {
			stmt.executeUpdate(s1);
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();

			}
		}
	}

	public boolean checkIfAnswerInTable(String ansText) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT answerText FROM answerTable");
			while (rs.next()) {
				if (rs.getString("answerText").equalsIgnoreCase(ansText))
					return true;
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return false;
	}

	public boolean checkIfAnswerInCQ(int id, String ansText) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT answerText FROM closeQuestionsAnswers WHERE CQID = '" + id + "' AND answerText = '"+ansText+"'");
			return rs.next();
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return false;
		}
	}

	public boolean checkIfCQ(int id) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT questionType FROM questionTable WHERE questionId = '" + id + "'");
			rs.next();
			if (rs.getString("questionType").equalsIgnoreCase("close"))
				return true;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return false;

	}

	public int getNumOfAnswersInQuestion(int id) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT numOfAnswers FROM closeQuestionTable WHERE CQID = '" + id + "'");
			rs.next();
			return rs.getInt("numOfAnswers");
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return -1;
		}
	}

	public void deleteAnswer(int id, int location) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery(
					"SELECT answerText FROM closeQuestionsAnswers WHERE CQID = '" + id + "' ORDER BY CQID");//
			for (int i = 0; i < location; i++) {
				rs.next();
			}
			String ans = rs.getString("answerText");
			rs = stmt.executeQuery("SELECT answerText,COUNT(answerText) as numOfSameAnswers from"
					+ "(SELECT answerText FROM openQuestionTable union all SELECT"
					+ " answerText FROM closeQuestionsAnswers union all SELECT TAnsText FROM closeQuestionsInTest ) as totalAnswers WHERE answerText = '" + ans + "'"
					+ "GROUP BY answerText");
			rs.next();
			int numOfSameAnswers = rs.getInt("numOfSameAnswers");
			stmt.executeUpdate(
					"DELETE FROM closeQuestionsAnswers WHERE( CQID = '" + id + "' AND answerText = '" + ans + "' )");
			stmt.executeUpdate(
					"UPDATE closeQuestionTable SET numOfAnswers = numOfAnswers -1 WHERE CQID = '" + id + "'");
			if (numOfSameAnswers == 1)
				stmt.executeUpdate("DELETE FROM answerTable WHERE(answerText = '" + ans + "' )");
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
	}

	public void updateAnswerText(int id, int location, String text) { // For specific question
		ResultSet rs;
		try {
			addAnswerToTable(text);
			if (location == -1)
				stmt.executeUpdate(
						"UPDATE openQuestionTable set answerText = '" + text + "' WHERE OQId = '" + id + "' ");

			else {
				rs = stmt.executeQuery("SELECT answerText FROM closeQuestionsAnswers WHERE CQID = '" + id + "'");
				for (int i = 0; i < location; i++) {
					rs.next();
				}
				String ans = rs.getString("answerText");
				rs = stmt.executeQuery("SELECT answerText,COUNT(answerText) as numOfSameAnswers from"
						+ "(SELECT answerText FROM openQuestionTable union all SELECT"
						+ " answerText FROM closeQuestionsAnswers union all SELECT TAnsText FROM closeQuestionsInTest) as totalAnswers WHERE answerText = '" + ans + "'"
						+ "GROUP BY answerText");
				rs.next();
				int numOfSameAnswers = rs.getInt("numOfSameAnswers");
				
				stmt.executeUpdate("UPDATE closeQuestionsAnswers SET answerText = '" + text + "' WHERE CQID = '" + id
						+ "' AND answerText = '" + ans + "' ");
				if (numOfSameAnswers == 1)
					stmt.executeUpdate("DELETE FROM answerTable WHERE(answerText = '" + ans + "' )");
			}
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
	}

	public boolean getAnswerIndication(int id, int location) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT CQID , indication FROM closeQuestionsAnswers WHERE CQID = '" + id + "'"); ///
			for (int i = 0; i < location; i++)
				rs.next();
			if (rs.getInt("indication") == 1)
				return true;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
		return false;
	}

	public String printAllQuestionsWithAnswers() throws SQLException {
		StringBuffer sb = new StringBuffer();
		int id;
		ResultSet rs;
		String qText;
		int numOfAnswers;
		boolean ind;
		String showAllQuestions = "SELECT questionId , questionText , questionType ,numOfAnswers, answerTable.answerText , indication FROM"
				+ " questionTable LEFT JOIN closeQuestionTable ON"
				+ " (CQID = questionId ) LEFT JOIN openQuestionTable ON (OQID = questionId)"
				+ " LEFT JOIN closeQuestionsAnswers ON (closeQuestionsAnswers.CQID = closeQuestionTable.CQID )"
				+ " LEFT JOIN answerTable ON (answerTable.answerText = openQuestionTable.answerText OR "
				+ " answerTable.answerText = closeQuestionsAnswers.answerText)" + " ORDER BY questionId ASC";
		rs = stmt.executeQuery(showAllQuestions);
		while (rs.next()) {
			id = rs.getInt("questionId");
			qText = rs.getString("questionText");
			sb.append(id + ") " + qText + "\n");
			if (rs.getString("questionType").equalsIgnoreCase("open")) {
				sb.append("\t" + rs.getString("answerText") + "\n");
			} else {
				numOfAnswers = rs.getInt("numOfAnswers");
				for (int i = 0; i < numOfAnswers; i++) {
					if (rs.getInt("indication") == 1)
						ind = true;
					else
						ind = false;
					sb.append("\t(" + (i + 1) + ") " + rs.getString("answerText") + " ---> " + ind + "\n");
					if (i != numOfAnswers - 1)
						rs.next();
				}

			}

			sb.append("\n\n");
		}
		return sb.toString();

	}

	public int getNumOfQuestions() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT Max(questionId) FROM questionTable");
			rs.next();
			return rs.getInt("Max(questionId)");
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return -1;
		}

	}

	public String getAnswerTextAndIndicationForCQ(int id, int ansLocation) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT answerText, indication FROM closeQuestionsAnswers WHERE CQID = '" + id + "'");
			for (int i = 0; i < ansLocation; i++) {
				rs.next();
			}
			String ans = rs.getString("answerText");
			int indication = rs.getInt("indication");
			boolean ind = true;
			if (indication == 0)
				ind = false;
			return ans + "--->" + ind;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return "ERROR";

		}
	}

	public int getNumOfFalseAnswersInCQ(int id) {
		int count = 0;
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT answerText, indication FROM closeQuestionsAnswers WHERE CQID = '" + id + "'");
			while (rs.next()) {
				if (rs.getInt("indication") == 0)
					count++;
			}
			return count;
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return -1;

		}
	}

	public String getQuestionBySerialNumber(int qId) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT questionText FROM questionTable WHERE questionId = '" + qId + "'");
			rs.next();
			return rs.getString("questionText");
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return null;

		}
	}

	public void createTest(int testId, String testType) {
		try {
			stmt.executeUpdate("INSERT INTO testTable(testID,testType) VALUES ('" + testId + "'  ,'" + testType + "')");
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}

		}
	}

	public void addOpenQuestionToTest(int qId, int testId) {
		try {
			stmt.executeUpdate("INSERT INTO testContains VALUES ('" + testId + "','" + qId + "')");
			stmt.executeUpdate(
					"UPDATE testTable SET numOfQuestions = numOfQuestions + 1 WHERE testID = '" + testId + "'");
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				System.out.println("addOpenQuestionToTest");
				ex = ex.getNextException();
			}

		}
	}

	public void addCloseQuestionAndAnswersToTest(int qId, ArrayList<Integer> locations, int testId) {
		ResultSet rs;
		int i = 1;
		LinkedHashMap<String, Integer> answers = new LinkedHashMap<>();
		try {
			rs = stmt
					.executeQuery("SELECT answerText,indication FROM closeQuestionsAnswers WHERE CQID = '"+qId+ "'");
			while (rs.next()) {
				if (locations.contains(i)) {
					answers.put(rs.getString("answerText"), rs.getInt("indication"));
				}
				i++;
			}
			setCorecctAnswer(answers);
			Iterator<Entry<String, Integer>> it = answers.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Integer> ans = it.next();
				stmt.executeUpdate("INSERT INTO closeQuestionsInTest VALUES ('"+testId+"' , '"+qId+"' , '"+ans.getKey()+"' , '"+ ans.getValue()+"')");
			}
			stmt.executeUpdate(
					"UPDATE testTable SET numOfQuestions = numOfQuestions + 1 WHERE testID = '"+testId+"'");
			stmt.executeUpdate("INSERT INTO testContains VALUES ('"+testId+"','"+qId+"')");

		} catch (SQLException ex) {
			while (ex != null) { 
				System.out.println("SQL exception: " + ex.getMessage());
				System.out.println("addCloseQuestionAndAnswersToTest");
				ex = ex.getNextException();
			}

		}
	}

	public void setCorecctAnswer(LinkedHashMap<String, Integer> answers) {
		int count = 0;
		String correctAnswer = " ";
		Iterator<Entry<String, Integer>> it = answers.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Integer> ans = it.next();
			if (ans.getValue() == 1) {
				count++;
				correctAnswer = ans.getKey();
			}
		}
		if (count > 1) {
			it = answers.entrySet().iterator();
			correctAnswer = "There is more than one answer";
			while (it.hasNext()) {
				Entry<String, Integer> ans = it.next();
				ans.setValue(0);
			}
		}

		else if (count == 0)
			correctAnswer = "There is no correct answer";
		answers.put("There is more than one answer", 0);
		answers.put("There is no correct answer", 0);
		answers.replace(correctAnswer, 1);
	}

	public boolean checkIfQuestionExistInTest(int qId, int testId) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT TQID FROM testContains WHERE TQID = '" + qId + "' AND TID = '" + testId + "'");
			return rs.next();
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return false;

		}
	}
	
	public int getNumOfQuestionInTest(int testId) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT numOfQuestions FROM testTable WHERE testID = '"+testId+"'");
			rs.next();
			return rs.getInt("numOfQuestions");
		}catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return -1;
		}
	}

	public String testQuestionsWithCorecctAnswers(int testId) {
		int i = 1;
		String qText ="" , ansText = "";
		int numOfQuestions = getNumOfQuestionInTest(testId);
		StringBuffer sb = new StringBuffer("This test with the corecct answers has " + numOfQuestions + " questions :\n");
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT questionText , answerText FROM testContains JOIN openQuestionTable JOIN questionTable  ON (TQID = OQID AND TQID=questionID) WHERE TID = '" + testId + "'  ORDER BY questionText");
			while(rs.next())
			{
				qText = rs.getString("questionText");
				ansText = rs.getString("answerText");
				sb.append(i + ") " + qText + "\n");
				sb.append(ansText + "\n\n");
				i++;
			}
			rs = stmt.executeQuery("SELECT questionText ,TAnsText FROM questionTable JOIN closeQuestionsInTest ON (questionID = TCQID ) WHERE TID = '"+testId+"' AND TAnsInd = 1 ORDER BY questionText");
			while(rs.next()) {
				qText = rs.getString("questionText");
				ansText = rs.getString("TAnsText");
				sb.append(i + ") " + qText + "\n");
				sb.append(ansText + "\n\n");
				i++;
			}
			return sb.toString();
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return null;
		}
	}
	

	public String testToString(int testId) {
		int i = 1;
		int j =1;
		String qText ="";
		int numOfQuestions = getNumOfQuestionInTest(testId);
		StringBuffer sb = new StringBuffer("This test has " + numOfQuestions + " questions :\n");
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT questionText FROM testContains JOIN questionTable ON (TQID = questionId) WHERE TID = '" + testId + "' AND questionType = 'open'  ORDER BY questionText");
			while(rs.next())
			{
				qText = rs.getString("questionText");
				sb.append(i + ") " + qText + "\n\n");
				i++;
			}
			rs = stmt.executeQuery("SELECT questionText ,TAnsText FROM questionTable JOIN closeQuestionsInTest ON (questionId = TCQID) WHERE TID = '"+testId+"' ORDER BY questionText");
			boolean fContinue = rs.next();
			while(fContinue) {
				qText = rs.getString("questionText");
				sb.append(i + ") " + qText + "\n");
				j=1;
				i++;
				while(fContinue && rs.getString("questionText").equals(qText) ) {
					sb.append("\t(" +j + ") " + rs.getString("TAnsText") + "\n");
					j++;
					fContinue = rs.next();
				}
				sb.append("\n\n");
				
			}
			return sb.toString();
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return null;
		}
	}
	
	public int getNumOfTests() {
		try {
			ResultSet rs = stmt.executeQuery("SELECT Max(testID) FROM testTable");
			rs.next();
			return rs.getInt("Max(testID)");
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return -1;
		}

	}
}
