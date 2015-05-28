package data_structures.treasure;

public class Coupon implements Treasure.Content {
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
	public int getXP() {
	}

	@Override
	public int getId() {
		return 0;
	}
}
