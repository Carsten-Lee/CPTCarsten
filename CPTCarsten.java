import arc.*; 
import java.awt.Color; 
import java.awt.image.BufferedImage; 

public class CPTCarsten{ 
	public static void main(String[] args){
		Console con = new Console(1280,720);
		
		BufferedImage imgMenu = con.loadImage("MENU.png");
		con.drawImage(imgMenu,0,0);
		con.repaint();
		
		char chrMain = con.getChar(); 
		String strName; 
		if(chrMain=='1'){
			con.setDrawColor(Color.BLACK); 
			con.fillRect(0,0,1280, 720);
			con.println("What is your name?"); 
			strName = con.readLine(); 
			con.clear(); 
			
			String strQuizName; 
			String strUserQuiz; 
			TextInputFile quizlist = new TextInputFile("quizzes.txt"); 
			con.println("Quizzes:");
			while(quizlist.eof()==false){
				strQuizName = quizlist.readLine(); 
				con.println(strQuizName);
			}
			con.println("\nEnter name of quiz:"); 
			strUserQuiz = con.readLine(); 
			
			// Step 1: Count number of questions (7 lines per question)
			TextInputFile countQuestions = new TextInputFile(strUserQuiz);
			int intLineCount = 0;
			while (!countQuestions.eof()) {
				countQuestions.readLine();
				intLineCount++;
			}
			int intQuestionCount = intLineCount / 7;

			// Step 2: Create 2D array [# of questions][8 columns] (one extra for the image) 
			String[][] strQuiz = new String[intQuestionCount][8];

			// Step 3: Load data into array
			TextInputFile quizFile = new TextInputFile(strUserQuiz);
			for (int i = 0; i < intQuestionCount; i++) {
				for (int j = 0; j < 7; j++) {
					strQuiz[i][j] = quizFile.readLine();
				}
				// Column 7: Random number to shuffle
				strQuiz[i][7] = String.valueOf((int)(Math.random() * 100 + 1));
			}

			// Step 4: Bubble sort based on column 7
			for (int i = 0; i < strQuiz.length - 1; i++) {
				for (int j = 0; j < strQuiz.length - i - 1; j++) {
					int num1 = Integer.parseInt(strQuiz[j][7]);
					int num2 = Integer.parseInt(strQuiz[j + 1][7]);
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
			int intPercentage = 0; 
			for (int i = 0; i < strQuiz.length; i++) {
				con.clear(); 

				con.println("Name: "+strName+"     Quiz: "+strUserQuiz+"     Score: "+intPercentage+"%\n");
				
				con.println("Question " + (i + 1) + ": " + strQuiz[i][0]);
				BufferedImage imgQuestion = con.loadImage(strQuiz[i][1]); 
				con.drawImage(imgQuestion,0,80); 
				con.repaint();
				con.println("\n\n\n\n\n\n\n\n\nA) " + strQuiz[i][2]);
				con.println("B) " + strQuiz[i][3]);
				con.println("C) " + strQuiz[i][4]);
				con.println("D) " + strQuiz[i][5]);
				con.print("Your answer (A/B/C/D): ");
				String answer = con.readLine();

				if (answer.equalsIgnoreCase(strQuiz[i][6])) {
					con.println("Correct!");
					intScore++;
				} else {
					con.println("Incorrect. Correct answer: " + strQuiz[i][6]);
				}
				con.println("Press any key to continue...");
				intPercentage = (int)(((double)intScore / (i+1)) * 100);
				con.getChar();
				con.setDrawColor(Color.BLACK); 
				con.fillRect(0,80,200, 200);
			}
			con.setDrawColor(Color.BLACK); 
			con.fillRect(0,80,200, 200);
			con.clear();
			con.println("Quiz complete!");
			con.println(strName+" scored "+intPercentage+"%"+" on "+strUserQuiz); 
			
		}
		
	}
}
	
