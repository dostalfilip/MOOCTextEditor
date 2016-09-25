package spelling;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;

    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{	
		if(word == "" || isWord(word)){return false;};
		
		
		TrieNode currNode = root;
		word = word.toLowerCase();

		for(int i = 0; i < word.length() ; i++ ){
			Set<Character> possibleKey = currNode.getValidNextCharacters();
			Character currCharacter = new Character(word.charAt(i));
			
			if(possibleKey.contains(currCharacter)){
				currNode = currNode.getChild(word.charAt(i));
				}
			else{
				currNode = currNode.insert(word.charAt(i));
				}
		}
		if(currNode.getText().equals(word)){

			currNode.setEndsWord(true);
			return true;
		}
		else{
			return false;
		}
	    
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{

	    return root.size(root);
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{	
		if(s == ""){return false;};
		
		TrieNode currNode = root;
		s = s.toLowerCase();

		for(int i = 0; i < s.length() ; i++ ){
			Set<Character> possibleKey = currNode.getValidNextCharacters();
			Character currCharacter = new Character(s.charAt(i));
			
			if(possibleKey.contains(currCharacter)){
				currNode = currNode.getChild(s.charAt(i));
				}
			else{
				return false;
				}
		}
		if(currNode.getText().equals(s) && currNode.endsWord()){
			return true;
		}
		else{
			return false;
		}
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	List<String> listPredict = new ArrayList<String>(); 
 		prefix = prefix.toLowerCase();
 		TrieNode currNode = root;
    	 
    	 
    	 for(int i = 0; i < prefix.length() ; i++ ){
 			Set<Character> possibleKey = currNode.getValidNextCharacters();
 			Character currCharacter = new Character(prefix.charAt(i));
 			
 			if(possibleKey.contains(currCharacter)){
 				currNode = currNode.getChild(prefix.charAt(i));
 				}
 			else{
 				return listPredict;	
 				}
    	 }
    	 
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
 			
    	LinkedList<TrieNode> queuePredict = new LinkedList<TrieNode>();
    	
    	if(isWord(prefix)){
    		listPredict.add(prefix);    		
    	}
    	
 		for(Character r : currNode.getValidNextCharacters()){
 			queuePredict.add(currNode.getChild(r));
 		}

    	while(numCompletions != listPredict.size() && !queuePredict.isEmpty()){
    		
    		currNode = queuePredict.poll();

    		printNode(currNode);
    		
    	 	for(Character r : currNode.getValidNextCharacters()){
    	 			queuePredict.add(currNode.getChild(r));
    	 		}
    	 	
    	 	if(currNode.endsWord()){
    	 		listPredict.add(currNode.getText());
    		}
    	 	
    	 	

   		}
    	System.out.println("--------------------------------------------");
    	for (String i : listPredict){
    		System.out.println(i);

    	}
    	
     
     
        return listPredict;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}