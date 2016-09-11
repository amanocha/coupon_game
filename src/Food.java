public class Food {
	private String question;
	private String answer;
	
	public Food() {
		question = "";
		answer = "";
	}
	
	public Food(String new_question, String new_answer) {
		question = new_question;
		answer = new_answer;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setQuestion(String new_question) {
		question = new_question;
	}
	
	public void setAnswer(String new_answer) {
		answer = new_answer;
	}
}