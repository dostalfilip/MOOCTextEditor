package textgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 

	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator; // = new Random();	
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		if(!"".equals(sourceText)){
			
		
		
		
		String[] tempolarySplit = sourceText.split("[^A-Za-z'´,.!?-]+");	 // ()-.,!? ]+");	
/**
 *  40 - 65 prochazi vsechna slova a vytvari unikatum ListNode a pridava jej do wordList
 */
		for (int i = 0 ; i < tempolarySplit.length; i++){
			String temp = tempolarySplit[i];
			boolean isNotUsed = true; // default nastaveni ze neni pouzito

			for(ListNode a : wordList){ //Kontrola zda již není slovo v Listu
				if(a.getWord().equals(temp)){
					isNotUsed = false; // pokud zjisti ze jiz je pouzito 
					break;
				}
			}
			if(isNotUsed){ // pouze v pripade ze neni pouzito
				ListNode listNode = new ListNode(temp); //  vytvorit unikatni ListNode
				
				// a ted najit nasledujici slova
				for (int j = 0 ; j < tempolarySplit.length; j++){

					if(j+1 != tempolarySplit.length){ // kontroluje poyici aby nevkladalo mimo pole
						if(tempolarySplit[j].equals(temp)){ // pokud najdu aktualni slovo
							if(!temp.equals(tempolarySplit[j+1])){//pokud se aktualni j slovo nerovna temp
								//if(!listNode.nextContain(tempolarySplit[j+1])){ // pokud jiz neni jako dalsi slovo ulozene			 
									listNode.addNextWord(tempolarySplit[j+1]); // pøidání slova do listu nextWords
								//}
							}
						}
					}
				}

				wordList.add(listNode); // prida listNode do listu
			}
			}
				
		}
	}
		
		
		
	
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    String totalGenerated = "";
	   // Random randomGenerator = new Random();	  
	    
	    if(numWords == 0){
	    	return "";
	    }
	    
//	    if(wordList.isEmpty()){
//	    	Scanner scanner = null;
//			String text = null;
//			try {
//				scanner = new Scanner( new File("././data/grader_dict.txt"), "UTF-8" );
//				text = scanner.useDelimiter("\\A").next();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			finally{
//	    	scanner.close(); // Put this call in a finally block
//			}
//	    	
//	    	train(text);
//	    }

	    
	    // zde se da prepnout i na slovnik
	    if(wordList.isEmpty()){
    	Scanner scanner = null;
    	String text = null;
////		String text = "This is last the warning. All man has been warned. I have been there.";
    	
    	
		try {
			scanner = new Scanner( new File("././data/dict.txt"), "UTF-8" );
			text = scanner.useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
    	scanner.close(); // Put this call in a finally block
		}
    	
		String[] tempolarySplit = text.split("[\\r\\n]+");
	////	String[] tempolarySplit = text.split("[^A-Za-z'´]+");	
		
		
		for(String g : tempolarySplit){
			wordList.add(new ListNode(g));
			//System.out.println(g);
		}
    }
	    
	    
	    
	    
	    
	    
	    //ListNode start = wordList.get(rnGenerator.nextInt(wordList.size()));
	    ListNode start = wordList.get(0);
	    String temp2 = null;
	    boolean sameNode = false;
	    
	    
	    for(int i = 0 ; i < numWords; i++ ){ 
	    	sameNode = false;
	    	System.out.println(start);
	    	if(!start.isNotEmpty()){
	    		temp2 = start.getWord();
	    		start = wordList.get(rnGenerator.nextInt(wordList.size()));
	    		sameNode = true;
	    		//continue;
	    	}
	    	else{
	    		temp2 = start.getRandomNextWord(rnGenerator);
	    	}
		    	
		    	totalGenerated += temp2 + " ";
	    	

	    
	    if(!sameNode){
	    	for(ListNode o : wordList){	    
	    		
	    		if(o.isWordMain(temp2)){
	    			System.out.println(o);
	    			start = o;
	    			break;
	    		}
	    	}
	    	
	    }
	    }
		
		return totalGenerated;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{

		wordList.clear();
		train(sourceText);
		
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		
	//	Random generator = new Random();
		int delka = nextWords.size();
		int random = generator.nextInt(delka);
		System.out.println("random " + random + " delka " + nextWords.size() + "-1");

			
		
	    return nextWords.get(random);
	}

	

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
	/*
	 * int range = maximum - minimum + 1;
int randomNum =  rn.nextInt(range) + minimum;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Return true polkudlistNode obsahuje next v nextWorlds
	 */
	public boolean nextContain(String next){
		for(String n : nextWords){
			if(next.equals(n)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isWordMain(String main){
		if(getWord().equals(main)){
			return true;
		}
		return false;
		
	}
	public boolean isNotEmpty(){
		if(nextWords.isEmpty()){
			return false;
		}
		return true;
	
	
	}
	
}


