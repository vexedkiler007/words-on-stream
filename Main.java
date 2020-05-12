import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.io.*;
public class Main{
	public static void main(String[] args) throws IOException {
		while (true){	
			ArrayList<String> listLetters;
			ArrayList<Integer> listNumbers;
			//user input for letters
			System.out.println("Letters: ");
			Scanner in = new Scanner(System.in);
			//user input for numbers
			String letters = in.nextLine();
			System.out.println("Numbers: ");

			String numbers = in.nextLine();
			listLetters = Main.getList(letters, false);
			listNumbers = Main.getList(numbers, true);

			BetterDictionary Dict = new BetterDictionary(listLetters, listNumbers, "words_alpha.txt");
			BetterDictionary Dict1 = new BetterDictionary(listLetters,listNumbers, "cleanDictionary");
			Dict.result();
			Dict1.result();
			System.out.println(Dict1.changingwordlist);
			System.out.println(Dict.changingwordlist);
			List<String> newList = new ArrayList<String>(Dict.changingwordlist);
			newList.addAll(Dict1.changingwordlist);
			LinkedHashSet<String> orderedSet = new LinkedHashSet(newList);
			System.out.println(orderedSet);
			Dict.resetWordsList();
			Dict1.resetWordsList();
			
		}
	}
	public static ArrayList getList(String userstr, boolean integer){
		char[] charArray = userstr.toCharArray();
		ArrayList returnArrayList = new ArrayList();
		for (char chr: charArray){
			if (!integer){ 
				returnArrayList.add(String.valueOf(chr));
				System.out.println(chr);
				}
			else returnArrayList.add(Character.getNumericValue(chr));
		}
		System.out.println(returnArrayList);
		return returnArrayList;
	}
}
