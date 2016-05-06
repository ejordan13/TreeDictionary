/**
	Eli Jordan
	Final Program
	Data Structures and Algorithms - CSCI1524
	12/16/15
	This class uses a tree to create a dictionary of words from
	a text file
*/

import static java.lang.Integer.parseInt;
import java.util.Arrays;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
	PriorityQueue class creates generic type linked queues,
	and provides methods to manipulate the objects
*/
public class JordanDictionary<T>
{	
	/**
		Main method demonstrates all the methods of the 
		PriorityQueue class
	*/
	public static void main(String [] args)
	{
		
		boolean valid = true;
		boolean verified = false;
		boolean fixed = false;
		boolean done = false;
		String filename;
		String newWord;
		String request;
		char ch;
		int length;
		Node firstNode;
		int queueLength = 0;
		Node rootNode;
		Scanner scan = new Scanner (System.in);
		Scanner in = new Scanner (System.in);
		File file;
		
		System.out.println("========================================================");
		System.out.println("Tree dictionary program created by Eli Jordan");
		
		while(!verified)
		{
			in = new Scanner(System.in);
			System.out.println("========================================================");
			System.out.println("What is the name of the .txt file containing " +
								"the dictionary of English words?");
			filename = in.nextLine();
			
			try
			{
				file = new File(filename);
				scan = new Scanner(file);
				verified = true;
			}
			
			catch(Exception e)
			{
				try
				{
					file = new File(filename + ".txt");
					scan = new Scanner(file);
					verified = true;
				}
				
				catch(Exception ex)
				{
					System.out.println("Filename not recognized!");
				}
			}
		}
		
		System.out.println("========================================================");
		rootNode = new Node();
		
		while(scan.hasNext())
		{
			try
			{
				valid = true;
				newWord = scan.nextLine();
				newWord = newWord.toLowerCase();
				length = newWord.length();
				
				for(int i = 0; i < length; i++)
				{
					ch = newWord.charAt(i);
					
					if(!Character.isLetter(ch))
					{
						valid = false;
					}
				}
				
				if(valid)
				{
					rootNode.add(newWord);
				}
				else
				{
					System.out.println("           " + newWord + " has non-letters, not added.");
				}
			}
				
			catch(Exception exc)
			{
				System.out.println("ERROR: " + exc.getMessage());
				System.out.println("Something has gone terribly wrong...");
			}
		}
		
		while(!done)
		{
			try
			{
				System.out.println("========================================================");
				System.out.println("What word would you like to search for? ");
				request = in.nextLine();
				
				System.out.println("========================================================");
				if(request.equals(""))
				{
					done = true;
				}
				else if(rootNode.find(request))
				{
					System.out.println("               " + request + " is in the dictionary.");
				}
				else
				{
					System.out.println("               " + request + " is not in the dictionary.");
				}
				System.out.println("========================================================");
			}
			catch(Exception exce)
			{
				System.out.println("Please try again");
			}
		}
	}
	
	/**
		Node class creates Nodes to be used with the JordanDictionary class
	*/
	public static class Node
	{
		public char data;
		public boolean word;
		public Node[] branches;
		
		/**
			Default constructor sets data to null, initializes
			the branches as an array of Nodes, and sets word flag to false
		*/
		public Node()
		{
			data = '\0';
			word = false;
			branches = new Node[26];
		}
		
		/**
			Constructor sets data to the char passed in, initializes
			the branches as an array of Nodes and sets word flag accordingly
			@param ch the character form of the letter to be added to the tree
			@param end the boolean value True if it is the end of a word and False otherwise
		*/
		public Node(char ch, boolean end)
		{
			data = ch;
			word = end;
			branches = new Node[26];
		}
		
		/**
			Method add takes in a word, converts it to lower case,
			and checks through the tree to add each letter as needed
			@param newWord the new word being passed in for adding
		*/
		public void add(String newWord)
		{
			char ch;
			int length;
			int index = 0;
			boolean empty = false;
			boolean found = false;
			Node[] next = this.branches;
			
			
			newWord = newWord.toLowerCase();
			length = newWord.length();
			
			for(int i = 0; i < length; i++)
			{
				found = false;
				empty = false;
				ch = newWord.charAt(i);
				
				for(int j = 0; j < 26; j++)
				{
					empty = (next[j] == null);
					
					if(!empty)
					{
						if(next[j].data == ch)
						{
							found = true;
							index = j;
							break;
						}
					}
					else
					{
						found = false;
						index = j;
						break;
					}
				}
				
				if(found)
				{
					if(i == length - 1)
					{
						next[index].word = true;
					}
					
					next = next[index].branches;
				}
				else if(!found)
				{
					if(i == length-1)
					{
						next[index] = new Node(ch, true);
						next = next[index].branches;
					}
					else
					{
						next[index]= new Node(ch, false);
						next = next[index].branches;
					}
				}
			}
		}
		
		/**
			Method find takes in a word, converts it to lower case,
			and checks through the tree to find if it is in the dictionary
			@param searchWord the word being searched for in the dictionary
		*/
		public boolean find(String searchWord)
		{
			char ch;
			int length;
			int index = 0;
			boolean empty = false;
			boolean found = false;
			Node[] next = this.branches;
			
			
			searchWord = searchWord.toLowerCase();
			length = searchWord.length();
			
			for(int i = 0; i < length; i++)
			{
				found = false;
				empty = false;
				ch = searchWord.charAt(i);
				
				for(int j = 0; j < 26; j++)
				{
					empty = (next[j] == null);
					
					if(!empty)
					{
						if(next[j].data == ch)
						{
							found = true;
							index = j;
							break;
						}
					}
					else
					{
						found = false;
						break;
					}
				}
				
				if(found)
				{
					if(i == length-1)
					{
						return next[index].word;
					}
					next = next[index].branches;
				}
				else if(!found)
				{
					return false;
				}
			}
			return false;
		}
	}
}