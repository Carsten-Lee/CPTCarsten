import arc.*; 
import java.awt.Color; 
import java.awt.image.BufferedImage; 

public class CPTCarsten{ 
	public static void main(String[] args){
		Console con = new Console(1920,1080);
		
		BufferedImage imgMenu = con.loadImage("MENU.png");
		con.drawImage(imgMenu,0,0);
		con.repaint();
		
		char chrMain = con.getChar(); 
		String strName; 
		if(chrMain=='1'){
			con.setDrawColor(Color.BLACK); 
			con.fillRect(0,0,1920, 1080);
			con.println("What is your name?"); 
			strName = con.readLine(); 
			con.clear(); 
			
			String strQuizName; 
			String strUserQuiz; 
			TextInputFile quizlist = new TextInputFile("quizzes.txt"); 
			while(quizlist.eof()==false){
				strQuizName = quizlist.readLine(); 
				con.println("Quizzes:\n"+strQuizName);
			}
			con.println("\nEnter name of quiz:"); 
			strUserQuiz = con.readLine(); 
			
			// Step 1: Count number of questions (6 lines per question)
			TextInputFile countQuestions = new TextInputFile(strUserQuiz);
			int intLineCount = 0;
			while (!countQuestions.eof()) {
				countQuestions.readLine();
				intLineCount++;
			}
			int intQuestionCount = intLineCount / 6;

			// Step 2: Create 2D array [# of questions][7 columns]
			String[][] strQuiz = new String[intQuestionCount][7];

			// Step 3: Load data into array
			TextInputFile quizFile = new TextInputFile(strUserQuiz);
			for (int i = 0; i < intQuestionCount; i++) {
				for (int j = 0; j < 6; j++) {
					strQuiz[i][j] = quizFile.readLine();
				}
				// Column 6: Random number to shuffle
				strQuiz[i][6] = String.valueOf((int)(Math.random() * 100 + 1));
			}

			// Step 4: Bubble sort based on column 6
			for (int i = 0; i < strQuiz.length - 1; i++) {
				for (int j = 0; j < strQuiz.length - i - 1; j++) {
					int num1 = Integer.parseInt(strQuiz[j][6]);
					int num2 = Integer.parseInt(strQuiz[j + 1][6]);
					if (num1 > num2) {
						// Swap rows
						String[] temp = strQuiz[j];
						strQuiz[j] = strQuiz[j + 1];
						strQuiz[j + 1] = temp;
					}
				}
			}

			// Step 5: Ask questions and calculate score
			int intScore = 0;
			for (int i = 0; i < strQuiz.length; i++) {
				con.clear();
				con.println("Question " + (i + 1) + ": " + strQuiz[i][0]);
				con.println("A) " + strQuiz[i][1]);
				con.println("B) " + strQuiz[i][2]);
				con.println("C) " + strQuiz[i][3]);
				con.println("D) " + strQuiz[i][4]);
				con.print("Your answer (A/B/C/D): ");
				String answer = con.readLine();

				if (answer.equalsIgnoreCase(strQuiz[i][5])) {
					con.println("Correct!");
					intScore++;
				} else {
					con.println("Incorrect. Correct answer: " + strQuiz[i][5]);
				}

				con.println("Press any key to continue...");
				con.getChar();
			}

			con.clear();
			con.println("Quiz complete!");
			con.println("Final Score for " + strName + ": " + intScore + " out of " + strQuiz.length);
			
		}
		
	}
}
	
