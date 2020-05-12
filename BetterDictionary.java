import java.util.ArrayList;
import java.util.TreeSet;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;

class BetterDictionary{
	public List<String> letterArrayList;
	public List<Integer> wordLength;
	private String Filename;
	private	List<String> wordList = new ArrayList<String>();
	public List<String> changingwordlist = new ArrayList<String>();

	// numberWordLength: is how many each of the words that have a word len exists
	// Should be the same size as wordLength
	public List<Integer> numberWordLength;

	BetterDictionary(ArrayList<String> letterArrayList, ArrayList<Integer> wordLength, String Filename )
		throws IOException{
			this.letterArrayList = letterArrayList;
			this.wordLength = wordLength;		
			this.Filename = Filename;
			this.getWordList();
			Objects.requireNonNull(this.wordList);
			this.resetWordsList();
		} 

	private	BetterDictionary(String Filename){
		this.Filename = Filename;
	}

	private void getWordList() throws IOException{
		File file = new File(Filename);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer sb = new StringBuffer();

		String line;
		while((line=br.readLine())!=null){
			sb.append(line);
			sb.append("\n");
			this.wordList.add(line);

		}
		if (this.wordList.isEmpty()) this.wordList = null;
		fr.close();
	}
	public void print() throws Exception{
		// reflection
		BetterDictionary DictFilenameonly = new BetterDictionary(this.Filename);
		Class cls = DictFilenameonly.getClass();
		Field field = cls.getDeclaredField("Filename");
		field.setAccessible(true);

		System.out.println("Letters: "+this.letterArrayList);
		System.out.println("Word lengths: "+this.wordLength);
		System.out.println("Filename: "+DictFilenameonly.Filename);
	}

	public void limitBasedSize(){
		ArrayList<String> shorterArray = new ArrayList<>();
		for (String word:this.wordList){
			for( int i: this.wordLength){
				if (word.length() == i) shorterArray.add(word);
			}
		}	
		this.changingwordlist = shorterArray;
	} 
	// to be used after every game
	public void resetWordsList(){
		this.changingwordlist = this.wordList;
	}
	// counts the number of question marks 
	public int countQuestionMark(){
		int count = 0;
		for (String str: this.letterArrayList){
			if (str == "?"){ count++;}
		}
		return count;	
	}
	// counts the number of chars and puts them in a hashMap
	public static HashMap<Character,Integer> numberChar(String str){
		char[] charArray = new char[50];
		HashMap<Character, Integer> countMap = new HashMap<>();

		charArray = str.toCharArray();
		for (char chr: charArray){
			if (!countMap.containsKey(chr)){
				countMap.put(chr, 1);
				}
			else{
				countMap.put(chr, countMap.get(chr)+1);
			}

		} 	
		return countMap; 
	}	
	// HashMapA is creates a set of the keys
	// HashMapB subtracts 1 from the value
	public static HashMap<Character,Integer> HashMapSubtract(HashMap<Character, Integer> HashMapA, HashMap<Character,Integer> HashMapB){
		Set<Character> keys = HashMapA.keySet();
		for (char chr: keys){
			if (HashMapB.containsKey(chr)) HashMapB.put(chr, HashMapB.get(chr)-1);
		}	
		return HashMapB;
	}
	public static boolean Matches(HashMap<Character, Integer> wordHashMap, Integer numQuestionMark){
		Collection<Integer> values = wordHashMap.values();
		int runningCount = 0;
		for (int i: values){
			runningCount+=i;

		}
		return runningCount <= numQuestionMark; 
	}
	public void result(){
		this.limitBasedSize();
		int count = this.countQuestionMark();
		HashMap<Character,Integer> tempMap;
		HashMap<Character, Integer> availableWordsMap;
		ArrayList<String> resultingArrayList = new ArrayList<String>();
		availableWordsMap = BetterDictionary.numberChar(this.letterArrayList.toString());
		for (String str : this.changingwordlist){
			tempMap = BetterDictionary.numberChar(str);
			tempMap = BetterDictionary.HashMapSubtract(availableWordsMap, tempMap);	
			if (BetterDictionary.Matches(tempMap, count)){
				resultingArrayList.add(str);
			}
		}
		this.changingwordlist = resultingArrayList;
	}
}
