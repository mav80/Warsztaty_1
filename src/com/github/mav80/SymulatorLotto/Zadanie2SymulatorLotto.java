package com.github.mav80.SymulatorLotto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.lang3.ArrayUtils;

public class Zadanie2SymulatorLotto {

	public static void main(String[] args) {

		System.out.println(
				"To jest symulator Lotto. Wylosowałem 6 liczb, teraz Ty podaj 6 swoich liczb z zakresu 1 - 49.\n");

		int[] userNumbersTab = new int[6], generatedRandomNumbers = new int[6];
		int number, hitCounter = 0;
		List<Integer> corectlyGuessedNumbers = new ArrayList<Integer>();

		for (int i = 0; i < 6; i++) {
		// pobierz liczbę
			number = getNumberFromConsole();
		// sprawdź czy liczba jest na liście, jeśli tak to wypisz komunikat i pobierz jeszcze raz
			while (ArrayUtils.contains(userNumbersTab, number) == true) {
				System.out.println("Liczba już jest na Twojej liście! Wpisz inną.");
				number = getNumberFromConsole();
			}
		// pobraną liczbę dodaj do tablicy
			userNumbersTab[i] = number;
		}

		Arrays.sort(userNumbersTab);

		System.out.println("\nWpisane przez Ciebie liczby to: " + Arrays.toString(userNumbersTab));

		// jak w przypadku użytownika, losujemy liczby i upewniamy się czy się nie powtarzają

		for (int i = 0; i < generatedRandomNumbers.length; i++) {
			number = getRandomNumber(1, 49);
			while (ArrayUtils.contains(generatedRandomNumbers, number) == true) {
				number = getRandomNumber(1, 49);
			}
			generatedRandomNumbers[i] = number;
		}

		// alternatywna metoda na uzyskanie liczb - stworzenie tablicy 1-49 i jej
		// poprzestawianie oraz pobranie pierwszych 6 elementów

		// Integer[] arr = new Integer[49];
		// for (int i = 0; i < arr.length; i++) {
		// 		arr[i] = i;
		// }
		//
		// Collections.shuffle(Arrays.asList(arr));
		//
		// for (int i = 0; i < generatedRandomNumbers.length; i++) {
		// 		generatedRandomNumbers[i] = arr[i];
		// }

		Arrays.sort(generatedRandomNumbers);

		System.out.println("\nLiczby wylosowane przeze mnie to: " + Arrays.toString(generatedRandomNumbers));

		// sprawdzamy ile trafionych
		for (int i = 0; i < userNumbersTab.length; i++) {
			for (int j = 0; j < generatedRandomNumbers.length; j++) {
				if (userNumbersTab[i] == generatedRandomNumbers[j]) {
					hitCounter++;
					corectlyGuessedNumbers.add(generatedRandomNumbers[j]);
					break;  //jeśli numer trafiony to już więcej go nie będzie więc nie ma sensu sprawdzać dalej
				}
			}
			
		}

		Collections.sort(corectlyGuessedNumbers);
		System.out.print("\nWytypowałeś poprawnie " + hitCounter + " liczb.");

		if (hitCounter > 0 && hitCounter < 3) {
			System.out.print(" Oto one: " + corectlyGuessedNumbers.toString()
					+ " Nie trafiłeś nawet trójki więc niestety wypłaty nie będzie. ;)");
		}

		if (hitCounter >= 3 && hitCounter < 5) {
			System.out.print(" Oto one: " + corectlyGuessedNumbers.toString()
					+ " Trafiłeś więcej niż dwie liczby więc zaraz wypłacimy Ci pieniądze! ;)");
		}

		if (hitCounter == 6) {
			System.out.print(" Oto one: " + corectlyGuessedNumbers.toString() + " Brawo, jesteś milionerem!!! ;)");
		}

	}
	
	
	

	static int getNumberFromConsole() {

		@SuppressWarnings("resource")
		Scanner myScanner = new Scanner(System.in);
		int number;
		System.out.println("Wpisz liczbę: ");

		try {
			number = myScanner.nextInt();
			if (number < 1 || number > 49) {
				System.out.println("Liczba ma być z zakresu 1 - 49 !");
				number = getNumberFromConsole();
			}

		} catch (Exception e) {
			System.out.println("To nie jest liczba!");
			number = getNumberFromConsole();
		}
		return number;
	}
	
	
	

	static int getRandomNumber(int lowerLimit, int upperLimit) {
		Random generator = new Random();
		return generator.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
	}
	
	

}
