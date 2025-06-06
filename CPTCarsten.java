import arc.*; 
import java.awt.Color; 
import java.awt.image.BufferedImage; 

public class CPTCarsten{ 
	public static void main(String[] args){
		Console con = new Console("Multiple Choice",1280,720);
		
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
				// Clear the image 
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
				
				// Count number of questions
				TextInputFile countQuestions = new TextInputFile(strUserQuiz);
				int intLineCount = 0;
				while (!countQuestions.eof()) {
					countQuestions.readLine();
					intLineCount++;
				}
				int intQuestionCount = intLineCount / 7;
				countQuestions.close(); 
				System.out.println("# of questions: " + intQuestionCount);

				// Create 2D array [# of questions][8 columns] (one extra for the image) 
				String[][] strQuiz = new String[intQuestionCount][8];

				// Load data into array
				TextInputFile quizFile = new TextInputFile(strUserQuiz);
				for (int i = 0; i < intQuestionCount; i++) {
					for (int j = 0; j < 7; j++) {
						strQuiz[i][j] = quizFile.readLine();
					}
					// Column 7: Random number to shuffle
					strQuiz[i][7] = String.valueOf((int)(Math.random() * 100 + 1));
				}
				quizFile.close();

				// Bubble sort based on column 7 (randomized numbers)
				for (int i = 0; i < strQuiz.length - 1; i++) {
					for (int j = 0; j < strQuiz.length - i - 1; j++) {
						int num1 = Integer.parseInt(strQuiz[j][7]);
						int num2 = Integer.parseInt(strQuiz[j + 1][7]);
						if (num1 > num2) {
							// Swap rows
							String[] temp = strQuiz[j];
							strQuiz[j] = strQuiz[j + 1];
							strQuiz[j + 1] = temp;
							System.out.println("Swap " + strQuiz[j][0] + " with " + strQuiz[j + 1][0]);
						}
					}
				}
					
				// Ask questions and calculate score
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
				
				// If the name is statitan, add a number from 1-1000 onto the user's score
				System.out.println(strName);
				intPercentage = MultipleChoiceTools.statitan(strName, intPercentage); 
				
				BufferedImage imgComplete = con.loadImage("COMPLETE.png");
				int intX = 1280; 
				int intY = 0; 
				
				while(intX>0){ 
					con.setDrawColor(Color.BLACK); 
					con.fillRect(0, 0, 1280, 720);
					con.drawImage(imgComplete, intX, intY);
					con.repaint();
					
					intX -= 20;
					con.sleep(17);
				}
				con.setDrawColor(Color.BLACK); 
				con.fillRect(0, 0, 1280, 720);
				con.drawImage(imgComplete, 0, 0);
				con.repaint();
				
				con.println("\n\n\n\nYou scored "+intPercentage+"%"); 	
				
				// Write the score into leaderboard.txt
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
				
				con.println("Leaderboard (Top 10):"); 
				
				TextInputFile leaderboardPrint = new TextInputFile("leaderboard.txt"); 
				String strLBName;
				String strLBQuiz; 
				int intLBPercent; 
				int intLBCount = 0; 
				
				// Count how many scores there are 
				while(leaderboardPrint.eof()==false){
					strLBName = leaderboardPrint.readLine();
					strLBQuiz = leaderboardPrint.readLine(); 
					intLBPercent = leaderboardPrint.readInt(); 
					intLBCount++;
				}
				leaderboardPrint.close();
				
				String[][] leaderboard = new String[intLBCount][3]; 
				
				//  Read the details of the scores (Name, Quiz, Score)
				TextInputFile leaderboardRead = new TextInputFile("leaderboard.txt");
				for (int i = 0; i < intLBCount; i++) {
					leaderboard[i][0] = leaderboardRead.readLine();     
					leaderboard[i][1] = leaderboardRead.readLine();     
					leaderboard[i][2] = ""+leaderboardRead.readInt(); 
				}
				leaderboardRead.close();
				
				// Bubble sort based on coloumn 2 (quiz scores) 
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
				
				// Limit the number of scores shown to 10 
				int intMaxLength = Math.min(10, leaderboard.length);
				for (int i = 0; i < intMaxLength; i++) {
					con.println((i + 1) + ". " + leaderboard[i][0] + ", " + leaderboard[i][1] + ", " + leaderboard[i][2] + " %");
				}
				
				con.println("\nPress any key to return to the main menu...");
				con.getChar(); 
				
			}else if(chrMain == '3'){ 
				con.setDrawColor(Color.BLACK); 
				con.fillRect(0,0,1280, 720);
				
				TextOutputFile quizname = new TextOutputFile("quizzes.txt",true);
				
				// Get the name of the quiz and write to quizzes.txt file
				String strQNUser; 
				con.println("Enter Quiz Name (include .txt at the end):"); 
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
				
				// Get the questions and choices until user types "stop" 
				while(blnStop!=true){
					con.println("Enter Question "+uqCount+" :"); 
					strQuestion = con.readLine(); 
					userquiz.println(strQuestion); 
					userquiz.println("image.png");  // placeholder image
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
				BufferedImage imgQuit = con.loadImage("QUIT.png");
				con.drawImage(imgQuit,0,0);
				con.repaint();
				char chrQuit = con.getChar();  
				if(chrQuit=='1'){
					con.closeConsole(); 
				}else if(chrQuit=='2'){
					con.drawImage(imgMenu,0,0);
					con.repaint();
				}
				
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
				
			}else if(chrMain=='h'){
				con.setDrawColor(Color.BLACK); 
				con.fillRect(0,0,1280, 720);
				
				con.println("Instructions:"); 
				con.println("\nPlay:"); 
				con.println("1. Enter the name of the desired quiz, make sure to include the .txt at the end"); 
				con.println("2. Press the letter on your keyboard based on what you think the answer is (A/B/C/D),"); 
				con.println("   capitalization does not matter"); 
				con.println("3. Repeat until the end of the quiz which will show QUIZ COMPLETE along with your score"); 
				con.println("4. Press any key to return to the main menu"); 
				con.println("\nAdd Quiz:");
				con.println("1. Enter what you want to name your quiz, make sure to include .txt at the end"); 
				con.println("2. Type your question"); 
				con.println("3. Type the 4 possible choices to choose from"); 
				con.println("4. Enter the answer of the question (A/B/C/D), capitalization does not matter"); 
				con.println("5. If you are done creating the quiz, enter STOP, capitalization does not matter as well"); 
				con.println("6. Repeat this process until you finish creating the quiz"); 
				con.println("7. Press any key to return to the main menu"); 
			
				con.println("\n\nPress any key to return to the main menu...");
				con.getChar(); 
			}
			
		}
	}
}
	
