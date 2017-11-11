package com.github.mav80.gra.w.liczby.komputer.zgaduje;

import java.util.Scanner;

public class GraWLiczbyKomputerZgaduje {

	public static void main(String[] args) {

		int guessCounter = 0, min = 0, max = 1000, guess = 0;

		String gamersResponse = "";

		System.out.println(
				"Pomyśl liczbę w zakresie od 0 do 1000 a ja ją zgadnę w max. 11 próbach jeśli nie będziesz oszukiwać.");

		while (!gamersResponse.equals("trafiles") && guessCounter < 10) {
			//System.out.println("Szukamy w przedziale  minimum " + min + " do " + max);
			guessCounter++;
			System.out.println("\nPróba numer: " + guessCounter);
			guess = (min + max) / 2;
			System.out.println("Zgaduję: " + guess + " - czy to właściwa liczba?");
			gamersResponse = getConsoleString();

			if (gamersResponse.equals("mniej") == true) {
				max = guess;
			} else if (gamersResponse.equals("wiecej") == true) {
				min = guess;
			}

		}
		
		// obsługujemy sytuację brzegową - trafiliśmy, użytkownik wybrał liczbę 1000 albo oszukuje

		if (gamersResponse.equals("trafiles")) {
			System.out.println("\nWygrałem, dzięki za grę! :)");
		}

		else if (guessCounter >= 10 && guess < 999) {
			System.out.println("\nNieładnie, oszukujesz! :(");
		} else if (guessCounter >= 10 && guess < 1000) {
			guessCounter++;
			System.out.println("\nPróba numer: " + guessCounter);
			System.out.println("Zgaduję: 1000 - czy to właściwa liczba?");
			gamersResponse = getConsoleString();

			if (gamersResponse.equals("trafiles")) {
				System.out.println("\nWygrałem, dzięki za grę! :)");

			} else {
				System.out.println("\nOszukujesz! :(");
			}
		}
	}
	
	
	

	static String getConsoleString() {
		@SuppressWarnings("resource")
		Scanner myScanner = new Scanner(System.in);
		String string = new String();
		while (!string.equals("mniej") && !string.equals("wiecej") && !string.equals("trafiles")) {
			System.out.println("Wpisz odpowiedź (mniej, wiecej lub trafiles): ");
			string = myScanner.nextLine();
		}
		return string;
	}
	
	

}
