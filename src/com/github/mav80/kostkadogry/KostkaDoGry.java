package com.github.mav80.kostkadogry;

import java.util.Random;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class KostkaDoGry {

	public static void main(String[] args) {

		boolean doesStringContainRecognizableDice = false;
		String userDiceRoll = "", diceDetected = "", firstPartOfUserString = "", lastPartOfUSerString = "";
		int diceTypeAndRandomMax = 0, howManyThrows = 1, modifier = 0, throwResult, finalResult = 0;

		String[] diceTable = { "d100", "d20", "d12", "d10", "d8", "d6", "d4", "d3" };

		System.out.println(
				"\nTo jest program do symulowania rzutów kostką. Wpisz ile rzutów chcesz wykonać, jaką kostką i ewentualnie modyfikator,\nw formacie typu D6 (pojedynczy rzut kostką sześcienną), 2D3 (podwójny rzut kostką trójścienną), 2D10+10 (podwójny rzut kostką 10ścienną, do wyniku rzutu dodaj 10),\nD12-1 (rzut kostką 12ścienną, od wyniku odejmij 1) itp.\n\nMożliwe typy kostek to: D3, D4, D6, D8, D10, D12, D20, D100.\n");

		doesStringContainRecognizableDice = false;
		
		//trying to detect dice type, if unable = try again
		while (!doesStringContainRecognizableDice) {

			userDiceRoll = getConsoleString();

			for (int i = 0; i < diceTable.length; i++) {
				if (!doesStringContainRecognizableDice && userDiceRoll.toLowerCase().contains(diceTable[i])) {
					diceDetected = diceTable[i];
					String diceSubType = diceTable[i].substring(1); // help string for diceTypeAndRandomMax - parseInt doesn't work without it;
					diceTypeAndRandomMax = Integer.parseInt(diceSubType);
					doesStringContainRecognizableDice = true;
					break;
				}
			}

			int howManyDicesDetectedInUserString = StringUtils.countMatches(userDiceRoll.toLowerCase(), "d");

			if (howManyDicesDetectedInUserString > 1) {
				doesStringContainRecognizableDice = false;
			}

			if (!doesStringContainRecognizableDice) {
				System.out.println("\nNie jestem w stanie wykryć typu kostki, czy na pewno podałeś prawidłowe dane? Wprowadź jeszcze raz.");
			}
		}
        
		//slice user input into three parts in order to extract extra data -  number of throws and modifier
		int indexOfDiceTypeInUserString = userDiceRoll.indexOf(diceDetected);

		//check if there is extra data before dice type
		if (indexOfDiceTypeInUserString != 0) {
			firstPartOfUserString = userDiceRoll.substring(0, indexOfDiceTypeInUserString);
		}

		//check if there is extra data after dice type
		if (userDiceRoll.length() > firstPartOfUserString.length() + diceDetected.length()) {
			lastPartOfUSerString = userDiceRoll.substring(indexOfDiceTypeInUserString + diceDetected.length(), userDiceRoll.length());
		}

		//try to turn extra data before dice type into throw numbers
		try {
			howManyThrows = stringToInt(firstPartOfUserString);
		} catch (Exception e) {
			howManyThrows = 1;
		}

		//try to turn extra data after dice type into throw numbers
		try {
			modifier = stringToInt(lastPartOfUSerString);
		} catch (Exception e) {
			modifier = 0;
		}

		System.out.println("\nRzucamy " + howManyThrows + " razy kostką " + diceDetected
				+ " więc losujemy w zakresie 1 - " + diceTypeAndRandomMax + ". Ilość wykonywanych rzutów to " + howManyThrows
				+ ", po zsumowaniu rzutów do wyniku dodajemy " + modifier + ".\n");

		for (int i = 1; i <= howManyThrows; i++) {
			throwResult = getRandomNumber(1, diceTypeAndRandomMax);
			System.out.println("Wynik rzutu nr " + i + " wynosi " + throwResult + ". ");
			finalResult += throwResult;
		}

		System.out.println("\nPo " + howManyThrows + " rzutach kostką wynik wynosi " + finalResult
				+ ". Dodajemy do niego " + modifier + ".\n\nOstateczny wynik rzutu to: " + (finalResult + modifier));

	}
	
	
	
	

	static String getConsoleString() {
		@SuppressWarnings("resource")
		Scanner myScanner = new Scanner(System.in);
		String string = new String();
		System.out.println("Wpisz jak chcesz rzucić:");
		string = myScanner.nextLine();
		return string;
	}

	static int stringToInt(String string) throws Exception {
		int number;
		try {
			number = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			throw e;
		}
		return number;
	}

	static int getRandomNumber(int lowerLimit, int upperLimit) {
		Random generator = new Random();
		return generator.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
	}

}
