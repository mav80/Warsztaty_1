package com.github.mav80.most.popular.words;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MostPopularWords {

	public static void main(String[] args) {

		Path path = Paths.get("popular_words.txt");
		ArrayList<String> popularWordList = new ArrayList<>();

		// System.out.println(path);

		Connection connect = Jsoup.connect("http://www.onet.pl/");
		try {
			Document document = connect.get();
			Elements links = document.select("span.title");
			for (Element elem : links) {
				// System.out.println(elem.text());

				String[] split = elem.text().split("\\s|\\?|:|\\.|\"|!|-|,|\\[|\\]|\\(|\\)");

				for (int i = 0; i < split.length; i++) {
					// System.out.println(split[i]);
					if (split[i].length() > 3) {
						popularWordList.add(split[i].toLowerCase());
					}

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\nCzęść pierwsza, lista słów do zapisu:\n");
		System.out.println(popularWordList.toString());

		try {
			Files.write(path, popularWordList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// druga część

		System.out.println("\n\n\n\nCzęść druga - najpopularniejsze słowa i ilość ich wystąpień:\n");
		
		Path path2 = Paths.get("most_popular_words.txt");
		Path path3 = Paths.get("most_popular_words_metodazsieci.txt");

		ArrayList<String> mostPopularWordList = new ArrayList<>();            //do wczytania słów z pliku
		ArrayList<String> Top10MostPopularWordListToSave = new ArrayList<>(); //to zapiszemy na dysk
		HashSet<String> nonRepeatingWordsHashSet = new HashSet<>();           //hashmapa do której wrzucimy wszystkie słowa - stare zastąpią nowe więc nie będą się duplikować
		String[] nonRepeatingWordsArray;                                      //tworzę nową tablicę do której przerzucę zawartość hashsetu z którego nie da się bezpośrednio wyciągnąć wartości
		int[] wordCountArray;												  //tablica intów w której liczby na każdej pozycji odpowiadają liczbie wystąpień słów w na tej samej pozycji w liście nonRepeatingWordsArray
		
		//odczyt pliku z dysku
		try {
			for (String line : Files.readAllLines(path)) {
				mostPopularWordList.add(line);
				nonRepeatingWordsHashSet.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		nonRepeatingWordsArray = new String[nonRepeatingWordsHashSet.size()]; //tablica ma mieć długość taką jak liczba unikalnych słów
		wordCountArray = new int[nonRepeatingWordsHashSet.size()]; 			  //tablica ma mieć długość taką jak liczba unikalnych słów


		
		//tu liczymy słowa - niestety liczy też np. jest jako część słowa jestem
		int counter = 0; //licznik pomocniczy
		Iterator<String> itr = nonRepeatingWordsHashSet.iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			int howManyOccurencies = 0;
			Pattern p = Pattern.compile("[^a-z0-9]"+key+"[^a-z0-9]");  //ograniczamy find() aby znajdował tylko to co chcemy, np. nie uwzględniał "jest" w "jestem"
			Matcher m = p.matcher( mostPopularWordList.toString() );
			
			while (m.find()) {
				howManyOccurencies++;                                                                //liczba wystąpień danego słowa
			}
			
			wordCountArray[counter] = howManyOccurencies;                                           //zapisujemy ją do tablicy
			nonRepeatingWordsArray[counter] = key;                                                  //wyciągnięte z hashsetu słowo zapisuję do tablicy
			counter++;
		}
		

		
		
		//potrzeba nam 10 słów
		for(int i = 0 ; i < 10 ; i++) {
			int[] maxAndItsPosition = (findMaxAndItsPosition(wordCountArray));                                 //moja metoda która zwraca najwyższą wartość i jej miejsce w tablicy w formie dwuelementowej tablicy
			System.out.println(nonRepeatingWordsArray[maxAndItsPosition[1]] + " - " + maxAndItsPosition[0]);
			Top10MostPopularWordListToSave.add(nonRepeatingWordsArray[maxAndItsPosition[1]]);
			
			wordCountArray[maxAndItsPosition[1]] = 0;                                                          // KONIECZNE! Zerujemy najwyższą ilość wystąpień danego słowa aby potem znaleźć następną najwyższą
		}
		

		//zapis do pliku
		try {
			Files.write(path2, Top10MostPopularWordListToSave);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		System.out.println("\n\n\nMetoda znaleziona w sieci:\n");
		
		
		//Czyjeś rozwiązanie z netu z dodatkowymi metodami pod spodem
		
		MostPopularWords mdc = new MostPopularWords();
		Map<String, Integer> wordMap = mdc.getWordCount("popular_words.txt");
		List<Entry<String, Integer>> list = mdc.sortByValue(wordMap);
		int count = 0;

		ArrayList<String> finalMostPopularWordsToFileList = new ArrayList<>();

		for (Map.Entry<String, Integer> entry : list) {

			if (count < 10) {
				System.out.println(entry.getKey() + " - " + entry.getValue());

				finalMostPopularWordsToFileList.add(entry.getKey()); //dodajemy do listy końcowej

				count++;
			}
		}

		//zapis
		try {
			Files.write(path3, finalMostPopularWordsToFileList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

	} /// koniec main
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public Map<String, Integer> getWordCount(String fileName) {

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedReader br = null;
		Map<String, Integer> wordMap = new HashMap<String, Integer>();
		try {
			fis = new FileInputStream(fileName);
			dis = new DataInputStream(fis);
			br = new BufferedReader(new InputStreamReader(dis));
			String line = null;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, " ");
				while (st.hasMoreTokens()) {
					String tmp = st.nextToken().toLowerCase();
					if (wordMap.containsKey(tmp)) {
						wordMap.put(tmp, wordMap.get(tmp) + 1);
					} else {
						wordMap.put(tmp, 1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception ex) {
			}
		}
		return wordMap;
	}
	
	
	

	public List<Entry<String, Integer>> sortByValue(Map<String, Integer> wordMap) {

		Set<Entry<String, Integer>> set = wordMap.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		return list;
	}
	
	
	
	
	
	
	//moja metoda, przyjmuje tablicę intów i zwraca dwuelementową tablicę w której na pierwszym miejscu jest najwyższa wartość a na drugim miejsce wystąpienia
	public static int[] findMaxAndItsPosition(int[] Array) {
		
		int[] finalList = new int[2];
		int maxValue = -1;
		int position = -1;
		
		for(int i = 0; i < Array.length ; i++) {
			if(maxValue < Array[i]) {
				maxValue = Array[i];
			}
		}
		
		
		if (maxValue > -1) {
			
			for(int i = 0; i < Array.length ; i++) {
				position++;
				
				if(Array[i] == maxValue) {
					break;
				}
			}
		}
		
		finalList[0] = maxValue;
		finalList[1] = position;
		
		return finalList;
	}
	
	

} //koniec klasy
