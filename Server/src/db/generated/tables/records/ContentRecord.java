/**
 * This class is generated by jOOQ
 */
package db.generated.tables.records;

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
public class ContentRecord extends org.jooq.impl.UpdatableRecordImpl<db.generated.tables.records.ContentRecord> implements org.jooq.Record3<java.lang.Integer, java.lang.String, java.lang.Integer> {

	private static final long serialVersionUID = 1831037572;

	/**
	 * Setter for <code>library.content.Cid</code>.
	 */
	public void setCid(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>library.content.Cid</code>.
	 */
	public java.lang.Integer getCid() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>library.content.content</code>.
	 */
	public void setContent(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>library.content.content</code>.
	 */
	public java.lang.String getContent() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>library.content.contentXP</code>.
	 */
	public void setContentxp(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>library.content.contentXP</code>.
	 */
	public java.lang.Integer getContentxp() {
		return (java.lang.Integer) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.Integer, java.lang.String, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.Integer, java.lang.String, java.lang.Integer> valuesRow() {
		return (org.jooq.Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return db.generated.tables.Content.CONTENT.CID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return db.generated.tables.Content.CONTENT.CONTENT_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return db.generated.tables.Content.CONTENT.CONTENTXP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getCid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getContent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getContentxp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentRecord value1(java.lang.Integer value) {
		setCid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentRecord value2(java.lang.String value) {
		setContent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentRecord value3(java.lang.Integer value) {
		setContentxp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.Integer value3) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ContentRecord
	 */
	public ContentRecord() {
		super(db.generated.tables.Content.CONTENT);
	}

	/**
	 * Create a detached, initialised ContentRecord
	 */
	public ContentRecord(java.lang.Integer cid, java.lang.String content, java.lang.Integer contentxp) {
		super(db.generated.tables.Content.CONTENT);

		setValue(0, cid);
		setValue(1, content);
		setValue(2, contentxp);
	}
}
