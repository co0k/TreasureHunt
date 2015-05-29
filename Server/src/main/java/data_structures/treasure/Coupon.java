package data_structures.treasure;

import java.io.Serializable;

public class Coupon implements Serializable, Treasure.Content {

	private static final long serialVersionUID = 9987395139608857L;

	private int experience;
	private String companyName;
	private double value;
	private int id;

	public Coupon(int id, int experience, String companyName, double value) {
		this.id = id;
		this.experience = experience;
		this.companyName = companyName;
		this.value = value;
	}

	public Coupon(int experience, String companyName, double value) {
		this(-1, experience, companyName, value);
	}

	public String getCompanyName() {
		return companyName;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String getType() {
		return "COUPON";
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getXP() {
		return experience;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean equalsWithoudId(Treasure.Content content) {
		if (this == content) return true;
		if (content == null || getClass() != content.getClass()) return false;

		Coupon coupon = (Coupon) content;

		if (experience != coupon.experience) return false;
		if (Double.compare(coupon.value, value) != 0) return false;
		return !(companyName != null ? !companyName.equals(coupon.companyName) : coupon.companyName != null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Coupon coupon = (Coupon) o;

		if (experience != coupon.experience) return false;
		if (Double.compare(coupon.value, value) != 0) return false;
		if (id != coupon.id) return false;
		return !(companyName != null ? !companyName.equals(coupon.companyName) : coupon.companyName != null);

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = experience;
		result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
		temp = Double.doubleToLongBits(value);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + id;
		return result;
	}

	@Override
	public String toString() {
		return "Coupon{" +
				"experience=" + experience +
				", companyName='" + companyName + '\'' +
				", value=" + value +
				", id=" + id +
				'}';
	}
}
