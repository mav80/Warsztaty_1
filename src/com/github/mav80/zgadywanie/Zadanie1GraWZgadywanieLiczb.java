//Zadanie liczba losowa dziala:

package com.github.mav80.zgadywanie;

import java.util.Random;
import java.util.Scanner;

public class Zadanie1GraWZgadywanieLiczb {

	public static void main(String[] args) {
	
		int randomNumberToGuess = getRandomNumber(1, 100);
		
		System.out.println("Zgadnij liczbę od 1 do 100 którą wylosowałem.\n");
		
		int usersGuess = getNumberFromConsole();
		
		//System.out.println("Szukasz tej liczby: " + randomNumberToGuess);
		
		while ( usersGuess != randomNumberToGuess) {
			
			if ( usersGuess > randomNumberToGuess) {
				System.out.println("Za dużo!\n");
				usersGuess = getNumberFromConsole();	
			}
			else if ( usersGuess < randomNumberToGuess) {
				System.out.println("Za mało!\n");
				usersGuess = getNumberFromConsole();	
			}
			
			else { 
				System.out.println("Gratulacje, udało ci się! :)");
			}
		}
		
		System.out.println("Gratulacje, udało ci się! :)");
		
	}
	
	
	
	
	static int getNumberFromConsole() {

		@SuppressWarnings("resource")
		Scanner myScanner = new Scanner(System.in);

		int randomNumber = 0;

		System.out.println("Wpisz liczbę: ");

		try {
			randomNumber = myScanner.nextInt();
		} catch (Exception e) {
			System.out.println("To nie jest liczba!");
			randomNumber = getNumberFromConsole();
		}

		return randomNumber;
	}
	
	


	static int getRandomNumber(int lowerLimit, int upperLimit) {
		
		Random generator = new Random();

		return generator.nextInt(upperLimit - lowerLimit) + lowerLimit;
			
	}	

}
