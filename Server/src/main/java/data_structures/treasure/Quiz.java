package data_structures.treasure;

import java.io.Serializable;

public class Quiz extends Treasure.Type implements Serializable {

	private static final long serialVersionUID = -4757699933744798652L;
	private int qid;
	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
	private String answer6;
	private Integer lid;

	/**
	 * answers can be partially filled, if for example only 2 answers are
	 * needed, the rest should be filled with 'null'
	 *
	 * @param lid
	 * @param question
	 * @param answer1
	 * @param answer2
	 * @param answer3
	 * @param answer4
	 * @param answer5
	 * @param answer6
	 */
	public Quiz(int quizId, Integer lid, int exp, String question, String answer1, String answer2,
	            String answer3, String answer4, String answer5, String answer6) {
		super.setXP(exp);
		this.qid = quizId;
		this.lid = lid;
		this.question = question;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.answer5 = answer5;
		this.answer6 = answer6;
	}

	public Quiz(int id, int quizId, Integer lid, int exp, String question, String answer1, String answer2,
	            String answer3, String answer4, String answer5, String answer6) {
		super.setId(id);
		super.setXP(exp);
		this.qid = quizId;
		this.lid = lid;
		this.question = question;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.answer5 = answer5;
		this.answer6 = answer6;
	}

	public Quiz(int exp, String question, String answer1, String answer2,
	            String answer3, String answer4, String answer5, String answer6) {
		this(-1, -1, -1, exp, question, answer1, answer2, answer3, answer4, answer5, answer6);
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

	public int getQuizId() {
		return qid;
	}

	public Integer getLocationId() {
		return lid;
	}

	@Override
	public String getType() {
		return "QUIZ";
	}

	@Override
	public boolean equalsWithoutId(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equalsWithoutId(o)) return false;

		Quiz quiz = (Quiz) o;

		if (!question.equals(quiz.question)) return false;
		if (answer1 != null ? !answer1.equals(quiz.answer1) : quiz.answer1 != null) return false;
		if (answer2 != null ? !answer2.equals(quiz.answer2) : quiz.answer2 != null) return false;
		if (answer3 != null ? !answer3.equals(quiz.answer3) : quiz.answer3 != null) return false;
		if (answer4 != null ? !answer4.equals(quiz.answer4) : quiz.answer4 != null) return false;
		if (answer5 != null ? !answer5.equals(quiz.answer5) : quiz.answer5 != null) return false;
		return !(answer6 != null ? !answer6.equals(quiz.answer6) : quiz.answer6 != null);

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Quiz quiz = (Quiz) o;

		if (qid != quiz.qid) return false;
		if (!question.equals(quiz.question)) return false;
		if (answer1 != null ? !answer1.equals(quiz.answer1) : quiz.answer1 != null) return false;
		if (answer2 != null ? !answer2.equals(quiz.answer2) : quiz.answer2 != null) return false;
		if (answer3 != null ? !answer3.equals(quiz.answer3) : quiz.answer3 != null) return false;
		if (answer4 != null ? !answer4.equals(quiz.answer4) : quiz.answer4 != null) return false;
		if (answer5 != null ? !answer5.equals(quiz.answer5) : quiz.answer5 != null) return false;
		return !(answer6 != null ? !answer6.equals(quiz.answer6) : quiz.answer6 != null);

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + qid;
		result = 31 * result + question.hashCode();
		result = 31 * result + (answer1 != null ? answer1.hashCode() : 0);
		result = 31 * result + (answer2 != null ? answer2.hashCode() : 0);
		result = 31 * result + (answer3 != null ? answer3.hashCode() : 0);
		result = 31 * result + (answer4 != null ? answer4.hashCode() : 0);
		result = 31 * result + (answer5 != null ? answer5.hashCode() : 0);
		result = 31 * result + (answer6 != null ? answer6.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Quiz{" +
				super.toString() + ", " +
				"qid=" + qid +
				", question='" + question + '\'' +
				", answer1='" + answer1 + '\'' +
				", answer2='" + answer2 + '\'' +
				", answer3='" + answer3 + '\'' +
				", answer4='" + answer4 + '\'' +
				", answer5='" + answer5 + '\'' +
				", answer6='" + answer6 + '\'' +
				", lid=" + lid +
				'}';
	}
}
