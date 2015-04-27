package db.manager;

import static db.generated.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import data_structures.treasure.*;
import data_structures.treasure.Treasure.*;

public class DatabaseManager {
	public static void main(String[] args) {
		try {
			Type test = getQuizFromId(1);
			System.out.println(test);
			test = setTypeAttributesFromId(2, test);
			System.out.println(test);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public boolean deleteTreasure (int tid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(BOX).where(BOX.TID.equal(tid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public boolean deleteLocation (int lid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(LOCATION).where(LOCATION.LID.equal(lid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public boolean deleteType (int tid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(TYPE).where(TYPE.TID.equal(tid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public boolean deleteSize (int sid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(SIZE).where(SIZE.SID.equal(sid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public boolean deleteQuiz (int qid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(QUIZ).where(QUIZ.QID.equal(qid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public boolean deleteContent (int cid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(CONTENT).where(CONTENT.CID.equal(cid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public void deleteAll () throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		create.truncate(BOX);
		create.truncate(LOCATION);
		create.truncate(QUIZ);
		create.truncate(SIZE);
		create.truncate(TYPE);
		create.truncate(CONTENT);
	}

	private static Connection getConnection() throws SQLException {
		String userName = "root";
		String password = "test";
		String url = "jdbc:mysql://localhost:3306/library";

		Connection conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}

	public static Quiz getQuizFromId(int qid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(QUIZ)
				.where(QUIZ.QID.equal(qid)).fetch();
		ArrayList<Quiz> out = new ArrayList<Quiz>();

		for (Record r : result) {
			int id = r.getValue(QUIZ.QID);
			String question = r.getValue(QUIZ.QUESTION);
			String answer1 = r.getValue(QUIZ.ANSWER1);
			String answer2 = r.getValue(QUIZ.ANSWER2);
			String answer3 = r.getValue(QUIZ.ANSWER3);
			String answer4 = r.getValue(QUIZ.ANSWER4);
			String answer5 = r.getValue(QUIZ.ANSWER5);
			String answer6 = r.getValue(QUIZ.ANSWER6);
			int lid = r.getValue(QUIZ.LID);
			Quiz tmp = new Quiz(id, question, answer1, answer2, answer3, answer4, answer5, answer6, lid);
			out.add(tmp);
		}

		conn.close();
		return out.get(0);

	}

	public static Location getLocationFromId(int lid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(LOCATION)
				.where(LOCATION.LID.equal(lid)).fetch();
		ArrayList<Location> out = new ArrayList<Location>();

		for (Record r : result) {
			Integer id = r.getValue(LOCATION.LID);
			Double x = r.getValue(LOCATION.X);
			Double y = r.getValue(LOCATION.Y);
			Integer outXP = r.getValue(LOCATION.OUTXP);
			Location tmp = new Location(id, outXP, x, y);
			out.add(tmp);
		}

		conn.close();
		return out.get(0);
	}
	
	public static Size getSizeFromId (int sid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(SIZE)
				.where(SIZE.SID.equal(sid)).fetch();
		ArrayList<Size> out = new ArrayList<Size>();

		for (Record r : result) {
			Integer id = r.getValue(SIZE.SID);
			Integer exp = r.getValue(SIZE.SIZEXP);
			Integer size = r.getValue(SIZE.SIZE_);
			Size tmp = new Size(id,exp, size);
			out.add(tmp);
		}

		conn.close();
		return out.get(0);
	}
	
	public static Content getConntentFromId (int cid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(CONTENT)
				.where(CONTENT.CID.equal(cid)).fetch();
		ArrayList<Content> out = new ArrayList<Content>();

		for (Record r : result) {
			Integer id = r.getValue(CONTENT.CID);
			String content = r.getValue(CONTENT.CONTENT_);
			Integer xp = r.getValue(CONTENT.CONTENTXP);
			//TODO Content only interface....
			//Content tmp = new Content (id, xp, content); 
			//out.add(tmp);
		}
		conn.close();
		return out.get(0);
	}

	private static Type setTypeAttributesFromId(int tid, Type toset) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(TYPE)
				.where(TYPE.TID.equal(tid)).fetch();

		for (Record r : result) {
			toset.setId(r.getValue(TYPE.TID));  
			toset.setName(r.getValue(TYPE.NAME));
			toset.setXP(r.getValue(TYPE.TYPEXP));
		}

		conn.close();
		return toset;
	}
	
	public static Treasure getTreasureFromId (int bid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(BOX)
				.where(BOX.BID.equal(bid)).fetch();
		ArrayList<Treasure> out = new ArrayList<Treasure>();
		
		for (Record r : result) {
			Integer id = r.getValue(BOX.BID);
			Integer lid = r.getValue(BOX.LID);
			Integer tid = r.getValue(BOX.TID);
			Integer sid = r.getValue(BOX.SID);
			Integer qid = r.getValue(BOX.QID);
			Integer cid = r.getValue(BOX.CID);
			Integer last_userid = r.getValue(BOX.LAST_USERID);
			Location location = getLocationFromId(lid);
			Type type = null;
			Size size = getSizeFromId(sid);
			Content content = null;
			/*if (cid != null) {
				 content = getConntentFromId(cid);
			}*/
			if (qid != null) {
				Quiz quiz = getQuizFromId(qid);
				type = setTypeAttributesFromId(tid, quiz);
			}
			
			Treasure tmp = new Treasure(id, 0, location, type, size, null, last_userid); //exp not  yet set
			out.add(tmp);
		}
		
		conn.close();
		return out.get(0);
	}
	
	public static Location getLocationFromBid (int bid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(BOX)
				.where(BOX.BID.equal(bid)).fetch();
		ArrayList<Integer> lid = new ArrayList<Integer>(); 
		
		for (Record r : result) {
			lid.add(r.getValue(BOX.LID));
		}
		
		
		return getLocationFromId(lid.get(0));
	}

}
