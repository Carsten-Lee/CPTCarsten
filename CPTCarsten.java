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
				
		}
		
	}
}
	
