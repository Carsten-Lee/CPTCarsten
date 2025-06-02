import arc.*; 
import java.awt.Color; 
import java.awt.image.BufferedImage; 

public class CPTCarsten{ 
	public static void main(String[] args){
		Console con = new Console(1280,720);
		
		String strName;
		con.println("What is your name?"); 
		strName = con.readLine(); 
			
		con.clear(); 
		
		while(true){	
			con.clear(); 
			BufferedImage imgMenu = con.loadImage("MENU.png");
			con.drawImage(imgMenu,0,0);
			con.repaint();
			
			char chrMain = con.getChar();  
			if(chrMain=='1'){
				con.setDrawColor(Color.BLACK); 
				con.fillRect(0,0,1280, 720);
				
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
				quizlist.close(); 
				
				// Step 1: Count number of questions (7 lines per question)
				TextInputFile countQuestions = new TextInputFile(strUserQuiz);
				int intLineCount = 0;
				while (!countQuestions.eof()) {
					countQuestions.readLine();
					intLineCount++;
				}
				int intQuestionCount = intLineCount / 7;
				countQuestions.close(); 

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
				quizFile.close();

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
				
				if(strName.equalsIgnoreCase("statitan")){ 
					intPercentage+=9999; 
				}
				
				con.println("Quiz complete! Score added to leaderboard");
				con.println("You scored "+intPercentage+"%"); 	
				
				TextOutputFile leaderboardScores = new TextOutputFile("leaderboard.txt",true);
				leaderboardScores.println(strName); 
				leaderboardScores.println(strUserQuiz); 
				leaderboardScores.println(intPercentage); 
				leaderboardScores.close(); 
				
				con.println("\nPress any key to return to the main menu...");
				con.getChar();
				
			}else if(chrMain=='2'){
				con.setDrawColor(Color.BLACK); 
				con.fillRect(0,0,1280, 720);
				
				con.println("Leaderboard:"); 
				
				TextInputFile leaderboardPrint = new TextInputFile("leaderboard.txt"); 
				String strLBName;
				String strLBQuiz; 
				int intLBPercent; 
				int intLBCount = 0; 
				
				while(leaderboardPrint.eof()==false){
					strLBName = leaderboardPrint.readLine();
					strLBQuiz = leaderboardPrint.readLine(); 
					intLBPercent = leaderboardPrint.readInt(); 
					intLBCount++;
				}
				leaderboardPrint.close();
				
				String[][] leaderboard = new String[intLBCount][3]; 
				
				TextInputFile leaderboardRead = new TextInputFile("leaderboard.txt");
				for (int i = 0; i < intLBCount; i++) {
					leaderboard[i][0] = leaderboardRead.readLine();     
					leaderboard[i][1] = leaderboardRead.readLine();     
					leaderboard[i][2] = ""+leaderboardRead.readInt(); 
				}
				leaderboardRead.close();
				
				for (int i = 0; i < leaderboard.length - 1; i++) {
					for (int j = 0; j < leaderboard.length - i - 1; j++) {
						int score1 = Integer.parseInt(leaderboard[j][2]);
						int score2 = Integer.parseInt(leaderboard[j + 1][2]);
						if (score1 < score2) {
							String[] temp = leaderboard[j];
							leaderboard[j] = leaderboard[j + 1];
							leaderboard[j + 1] = temp;
						}
					}
				}
				
				for (int i = 0; i < leaderboard.length; i++) {
					con.println((i + 1) + ". " + leaderboard[i][0] + ", " + leaderboard[i][1] + ", " + leaderboard[i][2] + " %");
				}
				
				con.println("\nPress any key to return to the main menu...");
				con.getChar(); 
				
			}else if(chrMain == '3'){ 
				con.setDrawColor(Color.BLACK); 
				con.fillRect(0,0,1280, 720);
				
				TextOutputFile quizname = new TextOutputFile("quizzes.txt",true);
				String strQNUser; 
				con.println("Enter Quiz Name (__.txt):"); 
				strQNUser = con.readLine(); 
				quizname.println(strQNUser); 
				quizname.close();
				
				con.clear(); 
				String strStop; 
				boolean blnStop = false; 
				String strQuestion; 
				String strChoice1;
				String strChoice2;
				String strChoice3; 
				String strChoice4; 
				String strAnswer; 
				
				TextOutputFile userquiz = new TextOutputFile(strQNUser,true); 
				int uqCount = 1;  
				
				while(blnStop!=true){
					con.println("Enter Question "+uqCount+" :"); 
					strQuestion = con.readLine(); 
					userquiz.println(strQuestion); 
					userquiz.println("image.png"); 
					con.println("Enter Choice A:"); 
					strChoice1 = con.readLine(); 
					userquiz.println(strChoice1);
					con.println("Enter Choice B:"); 
					strChoice2 = con.readLine(); 
					userquiz.println(strChoice2);
					con.println("Enter Choice C:"); 
					strChoice3 = con.readLine(); 
					userquiz.println(strChoice3);
					con.println("Enter Choice D:"); 
					strChoice4 = con.readLine(); 
					userquiz.println(strChoice4);
					con.println("Enter the Answer (A/B/C/D):"); 
					strAnswer = con.readLine(); 
					userquiz.println(strAnswer);
					uqCount++;
					con.println("\nEnter stop to complete the quiz"); 
					strStop = con.readLine(); 
					if(strStop.equalsIgnoreCase("stop")){
						blnStop = true; 
					}
					con.clear(); 
				}
				
				con.println("\nPress any key to return to the main menu...");
				con.getChar(); 
		
			}else if(chrMain=='4'){
				con.closeConsole(); 
				
			}else if(chrMain=='s'){
				con.setDrawColor(Color.BLACK); 
				con.fillRect(0,0,1280, 720);
				
				String strUserJoke; 
				con.println("Hey hear my joke");
				con.println("\nWhat did the tomato say to the other tomato during a race?"); 
				strUserJoke = con.readLine(); 
				con.println("\nKetchup");
				
				con.println("\nPress any key to return to the main menu...");
				con.getChar();
			}
		}
	}
}
	
