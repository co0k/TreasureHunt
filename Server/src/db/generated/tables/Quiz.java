/**
 * This class is generated by jOOQ
 */
package db.generated.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Quiz extends org.jooq.impl.TableImpl<db.generated.tables.records.QuizRecord> {

	private static final long serialVersionUID = -115705652;

	/**
	 * The reference instance of <code>library.quiz</code>
	 */
	public static final db.generated.tables.Quiz QUIZ = new db.generated.tables.Quiz();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<db.generated.tables.records.QuizRecord> getRecordType() {
		return db.generated.tables.records.QuizRecord.class;
	}

	/**
	 * The column <code>library.quiz.Qid</code>.
	 */
	public final org.jooq.TableField<db.generated.tables.records.QuizRecord, java.lang.Integer> QID = createField("Qid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>library.quiz.question</code>.
	 */
	public final org.jooq.TableField<db.generated.tables.records.QuizRecord, java.lang.String> QUESTION = createField("question", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

	/**
	 * The column <code>library.quiz.answer1</code>.
	 */
	public final org.jooq.TableField<db.generated.tables.records.QuizRecord, java.lang.String> ANSWER1 = createField("answer1", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

	/**
	 * The column <code>library.quiz.answer2</code>.
	 */
	public final org.jooq.TableField<db.generated.tables.records.QuizRecord, java.lang.String> ANSWER2 = createField("answer2", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

	/**
	 * The column <code>library.quiz.answer3</code>.
	 */
	public final org.jooq.TableField<db.generated.tables.records.QuizRecord, java.lang.String> ANSWER3 = createField("answer3", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

	/**
	 * The column <code>library.quiz.answer4</code>.
	 */
	public final org.jooq.TableField<db.generated.tables.records.QuizRecord, java.lang.String> ANSWER4 = createField("answer4", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

	/**
	 * The column <code>library.quiz.answer5</code>.
	 */
	public final org.jooq.TableField<db.generated.tables.records.QuizRecord, java.lang.String> ANSWER5 = createField("answer5", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

	/**
	 * The column <code>library.quiz.answer6</code>.
	 */
	public final org.jooq.TableField<db.generated.tables.records.QuizRecord, java.lang.String> ANSWER6 = createField("answer6", org.jooq.impl.SQLDataType.VARCHAR.length(1024), this, "");

	/**
	 * Create a <code>library.quiz</code> table reference
	 */
	public Quiz() {
		this("quiz", null);
	}

	/**
	 * Create an aliased <code>library.quiz</code> table reference
	 */
	public Quiz(java.lang.String alias) {
		this(alias, db.generated.tables.Quiz.QUIZ);
	}

	private Quiz(java.lang.String alias, org.jooq.Table<db.generated.tables.records.QuizRecord> aliased) {
		this(alias, aliased, null);
	}

	private Quiz(java.lang.String alias, org.jooq.Table<db.generated.tables.records.QuizRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, db.generated.Library.LIBRARY, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<db.generated.tables.records.QuizRecord, java.lang.Integer> getIdentity() {
		return db.generated.Keys.IDENTITY_QUIZ;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<db.generated.tables.records.QuizRecord> getPrimaryKey() {
		return db.generated.Keys.KEY_QUIZ_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<db.generated.tables.records.QuizRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<db.generated.tables.records.QuizRecord>>asList(db.generated.Keys.KEY_QUIZ_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public db.generated.tables.Quiz as(java.lang.String alias) {
		return new db.generated.tables.Quiz(alias, this);
	}

	/**
	 * Rename this table
	 */
	public db.generated.tables.Quiz rename(java.lang.String name) {
		return new db.generated.tables.Quiz(name, null);
	}
}
