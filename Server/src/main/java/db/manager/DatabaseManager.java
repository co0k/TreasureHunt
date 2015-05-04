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
			//System.out.println("inserted id: " + insertType("InsertTest", 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean userAllowedToOpenTreasure (int bid, int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select(BOX.LAST_USERID).from(BOX).where(BOX.BID.equal(bid)).fetchOne();
		if (!result.getValue(BOX.LAST_USERID).equals(uid)){
			conn.close();
			return true;
		}
		
		conn.close();
		return false;
	}
	
	public static int saveTreasure (Treasure toSave) throws IllegalArgumentException, SQLException {

		if (toSave.getId() != -1 && toSave.getXP() != -1)
			throw new IllegalArgumentException();

		Location lTmp = toSave.getLocation();
		int lid;
		if (lTmp.getId() == -1)
			lid = insertLocation(lTmp.getLon(), lTmp.getLat(), lTmp.getXP());
		else
		lid = lTmp.getId();
		
		Integer tid;
		Integer qid;
		if (toSave.getType() instanceof Quiz) {
			Quiz qTmp = (Quiz) toSave.getType();
			if (qTmp.getId() == -1)
				tid = insertType(qTmp.getType(), qTmp.getXP());
			else
				tid = qTmp.getId();
			if (qTmp.getQuizId() == -1)
				qid = insertQuiz(qTmp.getQuestion(), qTmp.getAnswer1(), qTmp.getAnswer2(), qTmp.getAnswer3(), qTmp.getAnswer4(), qTmp.getAnswer5(), qTmp.getAnswer6(), lid);
			else
				qid = qTmp.getQuizId();	
		} else {
			tid = null;
			qid = null;
		}
		
		Size sTmp = toSave.getSize();
		int sid;
		if (sTmp.getId() == -1) 
			sid = insertSize(sTmp.getSize(), sTmp.getXP());
		else
			sid = sTmp.getId();
		
		Integer cid = null; //TODO Content 
		
		Integer treasureID = insertBox(lid, tid, sid, qid, cid);

		return treasureID;
		}


	
	public static Integer insertBox (int lid, Integer tid, int sid, Integer qid, Integer cid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(BOX, BOX.LID, BOX.TID, BOX.SID, BOX.QID, BOX.CID).values(lid, tid, sid, qid, cid).returning(BOX.BID).fetchOne();
		conn.close();
		return record.getValue(BOX.BID);
		}
	
	public static Integer insertQuiz (String question, String correct1, String answer2, String answer3, String answer4, String answer5, String answer6, int lid) throws SQLException, IllegalArgumentException {
		if (question.length() > 256 || correct1.length() > 256 || answer2.length() > 256 || answer3.length() > 256 || answer4.length() > 256 || answer5.length() > 256 || answer6.length() > 256)
			throw new IllegalArgumentException();
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(QUIZ, QUIZ.QUESTION, QUIZ.ANSWER1, QUIZ.ANSWER2, QUIZ.ANSWER3, QUIZ.ANSWER4, QUIZ.ANSWER5, QUIZ.ANSWER6, QUIZ.LID).values(question, correct1, answer2, answer3, answer4, answer5, answer6, lid).returning(QUIZ.QID).fetchOne();
		conn.close();
		return record.getValue(QUIZ.QID);
		}
	
	public static Integer insertSize (int size, int exp) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(SIZE, SIZE.SIZE_, SIZE.SIZEXP).values(size, exp).returning(SIZE.SID).fetchOne();
		conn.close();
		return record.getValue(SIZE.SID);
		}
	
	public static Integer insertContent (String content, int exp) throws SQLException, IllegalArgumentException {
		if (content.length() > 1024)
			throw new IllegalArgumentException();
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(CONTENT, CONTENT.CONTENT_, CONTENT.CONTENTXP).values(content, exp).returning(CONTENT.CID).fetchOne();
		conn.close();
		return record.getValue(CONTENT.CID);
		}

	
	public static Integer insertLocation (Double x, Double y, int exp) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(LOCATION, LOCATION.X,LOCATION.Y,LOCATION.OUTXP).values(x, y, exp).returning(LOCATION.LID).fetchOne();
		conn.close();
		return record.getValue(LOCATION.LID);
		}
	
	public static Integer insertType (String name, int exp) throws SQLException, IllegalArgumentException {
		if (name.length() > 256)
			throw new IllegalArgumentException();
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(TYPE, TYPE.NAME, TYPE.TYPEXP).values(name, exp).returning(TYPE.TID).fetchOne();
		conn.close();
		return record.getValue(TYPE.TID);
		}
	
	
	
	public static boolean deleteTreasure (int tid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(BOX).where(BOX.TID.equal(tid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public static boolean deleteLocation (int lid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(LOCATION).where(LOCATION.LID.equal(lid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public static boolean deleteType (int tid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(TYPE).where(TYPE.TID.equal(tid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public static boolean deleteSize (int sid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(SIZE).where(SIZE.SID.equal(sid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public static boolean deleteQuiz (int qid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(QUIZ).where(QUIZ.QID.equal(qid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public static boolean deleteContent (int cid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(CONTENT).where(CONTENT.CID.equal(cid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}
	
	public static void deleteAll () throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		create.truncate(BOX);
		create.truncate(LOCATION);
		create.truncate(QUIZ);
		create.truncate(SIZE);
		create.truncate(TYPE);
		create.truncate(CONTENT);
		conn.close();
	}

	private static Connection getConnection() throws SQLException {
		String userName = "root";
		String password = "";
		String url = "jdbc:mysql://127.0.0.1:3306/library";

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
			Integer lid = r.getValue(QUIZ.LID);
			Quiz tmp = new Quiz(id, lid, question, answer1, answer2, answer3, answer4, answer5, answer6);
			out.add(tmp);
		}

		conn.close();
		if(out.isEmpty())
			return null;
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
		if(out.isEmpty())
			return null;
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
		if(out.isEmpty())
			return null;
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
		if(out.isEmpty())
			return null;
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
			/*if (cid != null) {//TODO content only interface
				 content = getConntentFromId(cid);
			}*/
			if (qid != null) {
				Quiz quiz = getQuizFromId(qid);
				type = setTypeAttributesFromId(tid, quiz);
			}
			
			Treasure tmp = new Treasure(id, location, type, size, null); //exp not  yet set
			out.add(tmp);
		}
		
		conn.close();
		if(out.isEmpty())
			return null;
		return out.get(0);
	}
	
	public static ArrayList<Treasure> getAllTreasure () throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(BOX).fetch();
		ArrayList<Treasure> out = new ArrayList<Treasure>();
		
		for (Record r : result) {
			Integer id = r.getValue(BOX.BID);
			Integer lid = r.getValue(BOX.LID);
			Integer tid = r.getValue(BOX.TID);
			Integer sid = r.getValue(BOX.SID);
			Integer qid = r.getValue(BOX.QID);
			Integer cid = r.getValue(BOX.CID);
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
			
			Treasure tmp = new Treasure(id, location, type, size, null); //exp not  yet set
			out.add(tmp);
		}
		
		conn.close();
		if(out.isEmpty())
			return null;
		return out;
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
