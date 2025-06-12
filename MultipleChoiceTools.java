import arc.*; 
import java.awt.Color; 
import java.awt.image.BufferedImage; 

public class MultipleChoiceTools{ 
	
	// Carsten Lee
	// Multiple Choice
	// 6/12/2025
	// Version 1.72 
	
	public static int statitan(String strUsername, int intPercent){
		if(strUsername.equalsIgnoreCase("statitan")){
		int intCheat = (int)(Math.random() * (1000 - 1 + 1)) + 1;
		intPercent += intCheat;
		}
		return intPercent;
	}
	
}
