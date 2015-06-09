package db.manager;

import static db.generated.Tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication_controller.json.JsonConstructor;
import data_structures.user.Inventory;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import data_structures.treasure.*;
import data_structures.treasure.Treasure.*;
import data_structures.user.HighscoreList;
import data_structures.user.User;

public class DatabaseManager {

	private static long treasureBlockTime;

	public static boolean userAllowedToOpenTreasure(int bid, int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select(BOX.LAST_USERID).from(BOX).where(BOX.BID.equal(bid)).fetchOne();
		conn.close();
		if(result != null && result.getValue(BOX.LAST_USERID) == null)
			return true;
		if (result == null || result.getValue(BOX.LAST_USERID).equals(uid)
				|| isUserBlockedForTreasure(uid, bid))
			return false;
		else
			return true;
	}

	public static boolean isUserBlockedForTreasure(int uid, int bid) throws SQLException {
		long timestamp = getLockTime(uid, bid);
		if (timestamp == -1)
			return false;

		if(System.currentTimeMillis()-timestamp >= treasureBlockTime) {
			deleteBlock(uid, bid);
			return false;
		}
		return true;
	}

	public static int saveTreasure(Treasure toSave) throws IllegalArgumentException, SQLException {

		if (toSave.getId() != -1 && !toSave.isValidTreasure())
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
				tid = insertType(qTmp.getType());
			else
				tid = qTmp.getId();
			if (qTmp.getQuizId() == -1) {
				Integer qLid;
				if (qTmp.getLocationId() == -1)
					qLid = null;
				else
					qLid = qTmp.getLocationId();
				qid = insertQuiz(qTmp.getQuestion(), qTmp.getAnswer1(), qTmp.getAnswer2(), qTmp.getAnswer3(), qTmp.getAnswer4(), qTmp.getAnswer5(), qTmp.getAnswer6(), qTmp.getXP(), qLid);
			} else
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
		Integer cid = insertContent(toSave.getContent());

		Integer treasureID = insertBox(lid, tid, sid, qid, cid);

		return treasureID;
	}


	public static Integer insertBox(Integer lid, Integer tid, int sid, Integer qid, Integer cid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(BOX, BOX.LID, BOX.TID, BOX.SID, BOX.QID, BOX.CID).values(lid, tid, sid, qid, cid).returning(BOX.BID).fetchOne();
		conn.close();
		return record.getValue(BOX.BID);
	}

	public static Integer insertQuiz(String question, String correct1, String answer2, String answer3, String answer4, String answer5, String answer6, int exp, Integer lid) throws SQLException, IllegalArgumentException {
		// check if the quiz and answer pattern is correct
		if (correct1 == null || question == null)
			throw new IllegalArgumentException("no correct answer or question given!");
		if (answer2 == null) {
			if (answer3 != null || answer4 != null || answer5 != null || answer6 != null)
				throw new IllegalArgumentException("an answer was given although the previous answer is null");
			if (answer3 == null) {
				if (answer4 != null || answer5 != null || answer6 != null)
					throw new IllegalArgumentException("an answer was given although the previous answer is null");
				if (answer4 == null) {
					if (answer5 != null || answer6 != null)
						throw new IllegalArgumentException("an answer was given although the previous answer is null");
				}
			}
		} else {
			if (answer3 == null) {
				if (answer4 != null || answer5 != null || answer6 != null)
					throw new IllegalArgumentException("an answer was given although the previous answer is null");
				if (answer4 == null) {
					if (answer5 != null || answer6 != null)
						throw new IllegalArgumentException("an answer was given although the previous answer is null");
				}
			} else {
				if (answer4 == null) {
					if (answer5 != null || answer6 != null)
						throw new IllegalArgumentException("an answer was given although the previous answer is null");
				} else if (answer5 == null && answer6 != null)
					throw new IllegalArgumentException("an answer was given although the previous answer is null");
			}
		}
		if (question.length() > 1024 || correct1.length() > 1024 || (answer2 != null && answer2.length() > 1024) || (answer3 != null && answer3.length() > 1024) ||
				(answer4 != null && answer4.length() > 1024) || (answer5 != null && answer5.length() > 1024) || (answer6 != null && answer6.length() > 1024))
			throw new IllegalArgumentException("strings longer than 256 characters not allowed!");
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(QUIZ, QUIZ.QUESTION, QUIZ.ANSWER1, QUIZ.ANSWER2, QUIZ.ANSWER3, QUIZ.ANSWER4, QUIZ.ANSWER5, QUIZ.ANSWER6, QUIZ.EXP, QUIZ.LID).values(question, correct1, answer2, answer3, answer4, answer5, answer6, exp, lid).returning(QUIZ.QID).fetchOne();
		conn.close();
		return record.getValue(QUIZ.QID);
	}

	public static Integer insertSize(int size, int exp) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(SIZE, SIZE.SIZE_, SIZE.SIZEXP).values(size, exp).returning(SIZE.SID).fetchOne();
		conn.close();
		return record.getValue(SIZE.SID);
	}

	public static Integer insertContent(Treasure.Content content) throws SQLException, IllegalArgumentException {
		if (content != null) {
			if (content.getId() == -1) {
				JsonConstructor jsonC = new JsonConstructor();
				String jsonVal = jsonC.toJson(new ContentHelperClass(content));
				Connection conn = getConnection();
				DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
				Record record = create.insertInto(CONTENT, CONTENT.CONTENT_).values(jsonVal).returning(CONTENT.CID).fetchOne();
				conn.close();
				return record.getValue(CONTENT.CID);
			} else
				return content.getId();
		}
		return null;
	}

	public static Integer insertLocation(Double x, Double y, int exp) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(LOCATION, LOCATION.X, LOCATION.Y, LOCATION.OUTXP).values(x, y, exp).returning(LOCATION.LID).fetchOne();
		conn.close();
		return record.getValue(LOCATION.LID);
	}

	public static Integer insertType(String name) throws SQLException, IllegalArgumentException {
		if (name == null || name.length() > 256)
			throw new IllegalArgumentException("Type has no name or name is too long");
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(TYPE, TYPE.NAME).values(name).returning(TYPE.TID).fetchOne();
		conn.close();
		return record.getValue(TYPE.TID);
	}

	public static Integer insertUser(User user) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(USER, USER.NAME, USER.PWDHASH, USER.EMAIL, USER.SCORE).values(user.getName(), user.getPasswordHash(), user.getEmail(), user.getXP()).returning(USER.UID).fetchOne();
		conn.close();
		return record.getValue(USER.UID);
	}

	public static Integer insertUser(String name, String pwdHash, String email) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record record = create.insertInto(USER, USER.NAME, USER.PWDHASH, USER.EMAIL).values(name, pwdHash, email).returning(USER.UID).fetchOne();
		conn.close();
		return record.getValue(USER.UID);
	}

	public static boolean insertHistory(int uid, int bid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int record = create.insertInto(HISTORY, HISTORY.UID, HISTORY.BID).values(uid, bid).execute();
		conn.close();
		if (record != 1)
			return false;
		else
			return true;
	}

	public static boolean insertBlock(int uid, int bid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int record = create.insertInto(BLOCK, BLOCK.UID, BLOCK.BID).values(uid, bid).execute();
		conn.close();
		if (record != 1)
			return false;
		else
			return true;
	}

	public static boolean insertInInventory(int uid, int cid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int record = create.insertInto(INVENTORY, INVENTORY.UID, INVENTORY.CID).values(uid, cid).execute();
		conn.close();
		if (record != 1)
			return false;
		else
			return true;
	}

	public static boolean deleteUser(int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(USER).where(USER.UID.equal(uid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}

	public static void deleteHistory(int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		create.delete(HISTORY).where(HISTORY.UID.equal(uid)).execute();
		conn.close();
	}

	public static void deleteInventory(int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		create.delete(INVENTORY).where(INVENTORY.UID.equal(uid)).execute();
		conn.close();
	}


	public static boolean deleteTreasure(int tid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(BOX).where(BOX.BID.equal(tid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}

	public static boolean deleteLocation(int lid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(LOCATION).where(LOCATION.LID.equal(lid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}

	public static boolean deleteType(int tid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(TYPE).where(TYPE.TID.equal(tid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}

	public static boolean deleteSize(int sid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(SIZE).where(SIZE.SID.equal(sid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}

	public static boolean deleteQuiz(int qid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(QUIZ).where(QUIZ.QID.equal(qid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}

	public static boolean deleteContent(int cid) throws SQLException {
		if (cid != -1) {
			Connection conn = getConnection();
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			int delete = create.delete(CONTENT).where(CONTENT.CID.equal(cid)).execute();
			conn.close();
			if (delete != 0)
				return true;
			else
				return false;
		} else
			return true;
	}

	public static boolean deleteAllBlockForUser(int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(BLOCK).where(BLOCK.UID.equal(uid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}

	public static boolean deleteBlock(int uid, int bid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int delete = create.delete(BLOCK).where(BLOCK.UID.equal(uid), BLOCK.BID.equal(bid)).execute();
		conn.close();
		if (delete != 0)
			return true;
		else
			return false;
	}

	public static void deleteAll() throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

		create.deleteFrom(BLOCK).execute();
		create.deleteFrom(HISTORY).execute();
		create.deleteFrom(BOX).execute();
		create.deleteFrom(QUIZ).execute();
		create.deleteFrom(INVENTORY).execute();

		create.deleteFrom(USER).execute();
		create.deleteFrom(LOCATION).execute();
		create.deleteFrom(SIZE).execute();
		create.deleteFrom(TYPE).execute();
		create.deleteFrom(CONTENT).execute();

		conn.close();
	}

	private static Connection getConnection() throws SQLException {
		// TODO: no hardcoding, maybe take the specification from gradle
		String userName = "root";
		String password = "";
		String url = "jdbc:mysql://127.0.0.1:3306/library";

		Connection conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}

	public static Quiz getQuizFromId(int qid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(QUIZ).where(QUIZ.QID.equal(qid)).fetchOne();
		conn.close();

		if (result == null)
			return null;

		int id = result.getValue(QUIZ.QID);
		String question = result.getValue(QUIZ.QUESTION);
		String answer1 = result.getValue(QUIZ.ANSWER1);
		String answer2 = result.getValue(QUIZ.ANSWER2);
		String answer3 = result.getValue(QUIZ.ANSWER3);
		String answer4 = result.getValue(QUIZ.ANSWER4);
		String answer5 = result.getValue(QUIZ.ANSWER5);
		String answer6 = result.getValue(QUIZ.ANSWER6);
		Integer exp = result.getValue(QUIZ.EXP);
		Integer lid = result.getValue(QUIZ.LID);
		Quiz tmp = new Quiz(id, lid, exp, question, answer1, answer2, answer3, answer4, answer5, answer6);

		return tmp;

	}

	public static Location getLocationFromId(int lid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(LOCATION).where(LOCATION.LID.equal(lid)).fetchOne();
		conn.close();

		if (result == null)
			return null;

		Integer id = result.getValue(LOCATION.LID);
		Double x = result.getValue(LOCATION.X);
		Double y = result.getValue(LOCATION.Y);
		Integer outXP = result.getValue(LOCATION.OUTXP);
		Location tmp = new Location(id, outXP, y, x);

		return tmp;
	}

	public static Size getSizeFromId(int sid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(SIZE).where(SIZE.SID.equal(sid)).fetchOne();
		conn.close();
		if (result == null)
			return null;
		Integer id = result.getValue(SIZE.SID);
		Integer exp = result.getValue(SIZE.SIZEXP);
		Integer size = result.getValue(SIZE.SIZE_);
		Size tmp = new Size(id, exp, size);

		return tmp;

	}

	public static Content getContentFromId(int cid) throws SQLException {
		if (cid != -1) {
			Connection conn = getConnection();
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			Record result = create.select().from(CONTENT).where(CONTENT.CID.equal(cid)).fetchOne();
			conn.close();

			if (result == null)
				return null;

			Integer id = result.getValue(CONTENT.CID);
			if (id == null)
				return null;
			String contentJson = result.getValue(CONTENT.CONTENT_);
			JsonConstructor jsonC = new JsonConstructor();
			Treasure.Content content = jsonC.fromJson(contentJson, ContentHelperClass.class).getContent();
			content.setId(id);
			return content;
		} else
			return null;
	}


	private static Type setTypeAttributesFromId(int tid, Type toset) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(TYPE).where(TYPE.TID.equal(tid)).fetchOne();
		conn.close();

		if (result == null)
			return null;

		toset.setId(result.getValue(TYPE.TID));

		return toset;
	}

	public static Treasure getTreasureFromId(int bid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(BOX).where(BOX.BID.equal(bid)).fetchOne();
		conn.close();

		if (result == null)
			return null;

		Integer id = result.getValue(BOX.BID);
		Integer lid = result.getValue(BOX.LID);
		Integer tid = result.getValue(BOX.TID);
		Integer sid = result.getValue(BOX.SID);
		Integer qid = result.getValue(BOX.QID);
		Integer cid = result.getValue(BOX.CID);
		Location location = getLocationFromId(lid);
		Type type = null;
		Size size = getSizeFromId(sid);
		Content content = null;
		if (cid != null)
			content = getContentFromId(cid);
		if (qid != null) {
			Quiz quiz = getQuizFromId(qid);
			type = setTypeAttributesFromId(tid, quiz);
		}

		Treasure tmp = new Treasure(id, location, type, size, content);

		return tmp;
	}

	public static ArrayList<Treasure> getAllTreasures() throws SQLException {
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
			if (cid != null) {
				content = getContentFromId(cid);
			}
			if (qid != null) {
				Quiz quiz = getQuizFromId(qid);
				type = setTypeAttributesFromId(tid, quiz);
			}

			Treasure tmp = new Treasure(id, location, type, size, content); //exp not  yet set
			out.add(tmp);
		}

		conn.close();
		if (out.isEmpty())
			return null;
		return out;
	}

	public static Location getLocationFromBid(int bid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(BOX).where(BOX.BID.equal(bid)).fetchOne();
		conn.close();

		if (result == null)
			return null;

		return getLocationFromId(result.getValue(BOX.LID));
	}

	public static User getUserFromId(int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(USER).where(USER.UID.equal(uid)).fetchOne();
		conn.close();
		if (result == null)
			return null;
		int uidFromDB = result.getValue(USER.UID);
		String name = result.getValue(USER.NAME);
		String pwdHash = result.getValue(USER.PWDHASH);
		String eMail = result.getValue(USER.EMAIL);
		int score = result.getValue(USER.SCORE);
		Inventory inventory = getUserInventory(uid);
		int rank = getRankFromId(uid);
		User out = new User(uidFromDB, name, pwdHash, eMail, score, rank, inventory);
		return out;
	}

	public static User getUserProfileFromId(int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(USER).where(USER.UID.equal(uid)).fetchOne();
		conn.close();
		if (result == null)
			return null;
		int uidFromDB = result.getValue(USER.UID);
		String name = result.getValue(USER.NAME);
		String eMail = result.getValue(USER.EMAIL);
		int score = result.getValue(USER.SCORE);
		Inventory inventory = getUserInventory(uid);
		int rank = getRankFromId(uid);
		User out = new User(uidFromDB, name, null, eMail, score, rank, inventory);
		return out;
	}

	public static User getUserFromName(String name) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(USER).where(USER.NAME.equal(name)).fetchOne();
		conn.close();
		if (result == null)
			return null;
		int uid = result.getValue(USER.UID);
		String nameFromDB = result.getValue(USER.NAME);
		String pwdHash = result.getValue(USER.PWDHASH);
		String eMail = result.getValue(USER.EMAIL);
		int score = result.getValue(USER.SCORE);
		Inventory inventory = getUserInventory(uid);
		int rank = getRankFromId(uid);
		User out = new User(uid, nameFromDB, pwdHash, eMail, score, rank, inventory);
		return out;
	}

	public static User getUserProfileFromName(String name) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select().from(USER).where(USER.NAME.equal(name)).fetchOne();
		conn.close();
		if (result == null)
			return null;
		int uid = result.getValue(USER.UID);
		String nameFromDB = result.getValue(USER.NAME);
		String eMail = result.getValue(USER.EMAIL);
		int score = result.getValue(USER.SCORE);
		Inventory inventory = getUserInventory(uid);
		int rank = getRankFromId(uid);
		User out = new User(uid, nameFromDB, null, eMail, score, rank, inventory);
		return out;
	}

	private static int getScoreFromId(int uid) throws SQLException, IllegalArgumentException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select(USER.SCORE).from(USER).where(USER.UID.equal(uid)).fetchOne();
		conn.close();
		if (result == null)
			throw new IllegalArgumentException("not a valid user");
		else
			return result.getValue(USER.SCORE);
	}

	public static int getRankFromId(int uid) throws SQLException, IllegalArgumentException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int score = getScoreFromId(uid);
		Field<Integer> rank = create.selectCount().from(USER).where(USER.SCORE.greaterThan(score)).asField("rank");
		Record result = create.select(rank).from(USER).where(USER.UID.equal(uid)).fetchOne();
		conn.close();
		if (result == null)
			throw new IllegalArgumentException("not a valid user");
		else
			return result.getValue(rank) + 1;
	}

	public static Inventory getUserInventory(int uId) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record> result = create.select().from(INVENTORY).where(INVENTORY.UID.equal(uId)).fetch();
		conn.close();

		if (result == null)
			return new Inventory();

		// count the contents
		Map<Integer, Integer> contentEntriesMap = new HashMap<>();

		for (Record r : result) {
			Integer curCId = r.getValue(INVENTORY.CID);
			if (contentEntriesMap.containsKey(curCId))
				contentEntriesMap.put(curCId, contentEntriesMap.get(curCId) + 1);
			else
				contentEntriesMap.put(curCId, 1);
		}

		// generate the actual Inventory
		Inventory inventory = new Inventory();
		for (Map.Entry<Integer, Integer> e : contentEntriesMap.entrySet()) {
			inventory.addEntry(new Inventory.Entry(e.getValue(), getContentFromId(e.getKey())));
		}
		return inventory;
	}

	public static HighscoreList getHighScoreFromTo(int fromRank, int numberOfEntries) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record3<Integer, String, Integer>> result = create.select(USER.UID, USER.NAME, USER.SCORE).from(USER).orderBy(USER.SCORE.desc()).limit(fromRank, numberOfEntries).fetch();
		conn.close();

		ArrayList<HighscoreList.Entry> out = new ArrayList<HighscoreList.Entry>();
		for (Record3<Integer, String, Integer> tmp : result) {
			HighscoreList.Entry tmpEntry = new HighscoreList.Entry(tmp.getValue(USER.UID), tmp.getValue(USER.NAME), getRankFromId(tmp.getValue(USER.UID)), tmp.getValue(USER.SCORE));
			out.add(tmpEntry);
		}
		if (out.isEmpty())
			return null;
		else
			return new HighscoreList(fromRank, out);
	}

	public static Map<Treasure, Long> getHistory(int uid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record2<Integer, Timestamp>> result = create.select(HISTORY.BID, HISTORY.TIME_STAMP).from(HISTORY).where(HISTORY.UID.equal(uid)).fetch();
		conn.close();
		Map<Treasure, Long> out = new HashMap<Treasure, Long>();

		for (Record2<Integer, Timestamp> tmp : result) {
			out.put(getTreasureFromId(tmp.getValue(HISTORY.BID)), tmp.getValue(HISTORY.TIME_STAMP).getTime());
		}
		if (!out.isEmpty())
			return out;
		else
			return null;
	}

	public static List<Integer> getAllBoxId() throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Result<Record1<Integer>> result = create.select(BOX.BID).from(BOX).fetch();
		conn.close();
		ArrayList<Integer> out = new ArrayList<Integer>();

		for (Record1<Integer> tmp : result) {
			out.add(tmp.getValue(BOX.BID));
		}
		if (out.isEmpty())
			return null;
		else
			return out;
	}

	public static long getLockTime(int uid, int bid) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		Record result = create.select(BLOCK.TIME_STAMP).from(BLOCK).where(BLOCK.UID.equal(uid), BLOCK.BID.equal(bid)).fetchOne();
		conn.close();
		if (result == null)
			return -1;
		else
			return result.getValue(BLOCK.TIME_STAMP).getTime();
	}

	public static boolean changePassword(int uid, String newPwdHash) throws SQLException, IllegalArgumentException {
		if (newPwdHash == null || newPwdHash.length() > 1024)
			throw new IllegalArgumentException("your hash is null or too long");
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int count = create.update(USER).set(USER.PWDHASH, newPwdHash).where(USER.UID.equal(uid)).execute();
		conn.close();
		if (count != 1)
			return false;
		else
			return true;
	}

	public static boolean updateScore(int uid, int score) throws SQLException {
		Connection conn = getConnection();
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
		int count = create.update(USER).set(USER.SCORE, getScoreFromId(uid) + score).where(USER.UID.equal(uid)).execute();
		conn.close();
		if (count != 1)
			return false;
		else
			return true;
	}


	private static class ContentHelperClass {
		Treasure.Content content;

		public Content getContent() {
			return content;
		}

		public void setContent(Content content) {
			this.content = content;
		}

		public ContentHelperClass(Content content) {
			this.content = content;
		}
	}

}
