package treasure;

public class Quiz implements Treasure.Type {
	private int id;
	private int experience;
	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
	private String answer6;

	/**
	 * answers can be partially filled, if for example only 2 answers are
	 * needed, the rest should be filled with 'null'
	 * 
	 * @param id
	 * @param exp
	 * @param question
	 * @param answer1
	 * @param answer2
	 * @param answer3
	 * @param answer4
	 * @param answer5
	 * @param answer6
	 */
	public Quiz(int id, int exp, String question, String answer1, String answer2, String answer3, String answer4, String answer5,
			String answer6) {
		this.id = id;
		this.experience = exp;
		this.question = question;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.answer5 = answer5;
		this.answer6 = answer6;
	}

	public Quiz(int exp, String question, String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) {
		this(-1, exp, question, answer1, answer2, answer3, answer4, answer5, answer6);
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer1() {
		return answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public String getAnswer5() {
		return answer5;
	}

	public String getAnswer6() {
		return answer6;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getXP() {
		return experience;
	}

	@Override
	public String getType() {
		return "QUIZ";
	}
}
