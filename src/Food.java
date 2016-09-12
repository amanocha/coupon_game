/**
 * The class for the Food object. It stores the food as the answer and the description (question) 
 * correspond to it. The question is displayed at the top of the screen, while the user aims to both 
 * answer the question and spell out the answer. The Game object stores an array of Food objects and 
 * randomly selects one each time the user must spell out a food.
 * 
 * @author Aninda Manocha
 */
public class Food {
	private String question;
	private String answer;
	
	/*****CONSTRUCTORS*****/
	
	/**
	 * This constructor creates an empty Food object.
	 */
	public Food() {
		question = "";
		answer = "";
	}
	
	/**
	 * This constructor creates a Food object.
	 * @param new_question - the question (description of food)
	 * @param new_answer - the answer (name of food)
	 */
	public Food(String new_question, String new_answer) {
		question = new_question;
		answer = new_answer;
	}

	/*****GETTERS*****/
	
	/**
	 * This method accesses the question (which describes the food).
	 * @return the question corresponding to the food
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * This method access the answer (the name of the food).
	 * @return the answer, or name of the food
	 */
	public String getAnswer() {
		return answer;
	}
}