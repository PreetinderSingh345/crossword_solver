package crossword_solver;

import java.util.Scanner;

public class Crossword_Solver{
		public static void print(char board[][]) {//here we're just printing the board
			
			int r=board.length;
			int c=board[0].length;
			
			for(int i=0;i<r;i++) {
				for(int j=0;j<c;j++) {
					System.out.print(board[i][j]+" ");
				}
				System.out.println();
			}
			
		}
		
		public static boolean validVertical(int x, int y, char board[][], String words, int currIdx) {
			
			int k=currIdx;
			int len=0;
			
			while(k<words.length() && words.charAt(k)!=';') {//here we are finding the length of the word which we have to
//				check can be filled inside the board or not at the current position x, y in the vertical direction
				k++;
				len++;
			}
			
			k=currIdx;
			int count=0;//this is the count of letters of the word which can be filled
			
			int r=board.length;
			int c=board[0].length;
			
			for(int i=x;i<r && k<words.length() && words.charAt(k)!=';';i++, k++) {
							
				if(board[i][y]=='-') {//if at the given position we have either '-' or the needed character then we increase
//					the count of the letters which can be filled
					count++;
				}
				else if(board[i][y]==words.charAt(k)) {
					count++;
				}
				else {//if there is any other character except '-' or the needed one then the vertical arrangement is not
//					valid and we return false
					return false;
				}
				
			}
			
			return (count==len);//the arrangement is valid iff the number of letters which can be filled is equal to the
//			word length
			
		}
		
		public static void setVertical(int x, int y, char board[][], String words, int currIdx, boolean lettersPlacedHere[]) {
			
			int k=currIdx;
		
			for(int i=x;k<words.length() && words.charAt(k)!=';';i++, k++) {
				
				if(board[i][y]=='-') {
					board[i][y]=words.charAt(k);//if the arrangement is valid in the vertical direction, then we place the
//					corresponding letters at the available positions as the filled positions, the letter is already present
					lettersPlacedHere[k-currIdx]=true;//to take a note of the letters we are placing and were not earlier
//					placed
				}				
				
			}
			
		}
		
		public static void resetVertical(int x, int y, char board[][], String words, int currIdx, boolean lettersPlacedHere[]) {
			
			int k=currIdx;
			
			for(int i=x;k<words.length() && words.charAt(k)!=';';i++, k++) {
				
				if(lettersPlacedHere[k-currIdx]) {//removing only those letters which were placed by us
					lettersPlacedHere[k-currIdx]=false;//since now we are removing the letter, so it is not placed here 
//					anymore and we set the value to false
					board[i][y]='-';//make the position available again
				}
				
			}
			
		}
		
		public static boolean validHorizontal(int x, int y, char board[][], String words, int currIdx) {
			
//			similar to validVertical function
			
			int k=currIdx;
			int len=0;
			
			while(k<words.length() && words.charAt(k)!=';') {
				k++;
				len++;
			}
			
			k=currIdx;
			int count=0;
			
			int r=board.length;
			int c=board[0].length;
			
			for(int i=y;i<c && k<words.length() && words.charAt(k)!=';';i++, k++) {
				
				if(board[x][i]=='-') {
					count++;
				}
				else if(board[x][i]==words.charAt(k)) {
					count++;
				}
				else {
					return false;
				}
				
			}
			
			return (count==len);
			
		}
		
		public static void setHorizontal(int x, int y, char board[][], String words, int currIdx, boolean lettersPlacedHere[]) {
			
//			similar to setVertical function
			
			int k=currIdx;
			
			for(int i=y;k<words.length() && words.charAt(k)!=';';i++, k++) {
				
				if(board[x][i]=='-') {
					board[x][i]=words.charAt(k);
					lettersPlacedHere[k-currIdx]=true;
				}
				
			}
			
		}
		
		public static void resetHorizontal(int x, int y, char board[][], String words, int currIdx, boolean lettersPlacedHere[]) {
			
//			similar to resetVertical function
			
			int k=currIdx;
			
			for(int i=y;k<words.length() && words.charAt(k)!=';';i++, k++) {
				
				if(lettersPlacedHere[k-currIdx]) {
					lettersPlacedHere[k-currIdx]=false;
					board[x][i]='-';
				}
				
			}
			
		}
		
		public static boolean isFull(char board[][]) {
			
			int r=board.length;
			int c=board[0].length;
			
			for(int i=0;i<r;i++) {
				for(int j=0;j<c;j++) {
					
					if(board[i][j]=='-') {
						return false;//if even one position in the board is available i.e. is '-', then the board is not
//						full
					}
					
				}
			}
			
			return true;
			
		}
				
		public static boolean helper(char board[][], String words, int currIdx) {
			
			int r=board.length;//getting number of rows and columns for the board
			int c=board[0].length;
			
			if(currIdx>=words.length()) {//if the string is exhausted then all the words have been filled and we need to make
//				a decision
				if(isFull(board)) {
					return true;//the board is solved iff there are no empty positions remaining in the board
				}
				return false;
			}
			
			for(int i=0;i<r;i++) {//iterating through the board
				for(int j=0;j<c;j++) {		
					
					if(board[i][j]=='-' || board[i][j]==words.charAt(currIdx)) {//if the current position is empty or has the
//						needed character, only then we proceed, else we move to the next point
						
						if(validVertical(i, j, board, words, currIdx)) {//checking if the word can be placed vertically at 
//							the current point
						
							int k=currIdx;
							int len=0;
							
							while(k<words.length() && words.charAt(k)!=';') {//finding the length of the word and the 
//								starting index of the next word which is k+1 in case the string is not exhausted
								k++;
								len++;
							}
							
							boolean lettersPlacedHere[]=new boolean[len];//making a boolean array equal to word length to see
//							which letters we have placed and were not placed earlier
							
							setVertical(i, j, board, words, currIdx, lettersPlacedHere);//filling the board vertically with
//							the word at the current point
							
							boolean isValid=helper(board, words, k+1);//try to fill the next word starting from index k+1
							
							if(isValid) {
								return true;//we return true if this combination works
							}
							else {
								resetVertical(i, j, board, words, currIdx, lettersPlacedHere);//we backtrack to the 
//								arrangement before the word was filled if this combination does not work
							}
							
						}
						if(validHorizontal(i, j, board, words, currIdx)) {
							
//							similar to the situation when the arrangement was valid vertically
							
							int k=currIdx;
							int len=0;
							
							while(k<words.length() && words.charAt(k)!=';') {
								k++;
								len++;
							}
							
							boolean lettersPlacedHere[]=new boolean[len];
							
							setHorizontal(i, j, board, words, currIdx, lettersPlacedHere);
							
							boolean isValid=helper(board, words, k+1);
							
							if(isValid) {
								return true;
							}
							else {
								resetHorizontal(i, j, board, words, currIdx, lettersPlacedHere);
							}
							
						}
						
					}	
					
				}
			}
			
			return false;//if none of the combinations work, then the board cannot be solved and we return false
			
		}
		public static boolean canFill(char board[][], String words) {
			
			boolean output=helper(board, words, 0);//calling helper function which takes the current index of the string i.e.
//			0 for now
			return output;
			
		}
		public static void main(String[] args) {
			
			Scanner s=new Scanner(System.in);
			
			System.out.println("enter the number of rows in the board : ");
			
			int r=s.nextInt();//taking input of number of rows
			
			while(!(r>=1)) {//making sure a valid dimension is entered
				System.out.println("enter a valid dimension : ");
				r=s.nextInt();
			}
			
			System.out.println("enter the number of columns in the board : ");
			
			int c=s.nextInt();//taking input of number of columns
			
			while(!(c>=1)) {//making sure a valid dimension is entered
				System.out.println("enter a valid dimension : ");
				c=s.nextInt();
			}
			
			System.out.println("enter the initial arrangement of the board('-' at available and '+' at unavailable positions) row wise(one word for each row) :");
					
			String arr[]=new String[r];
			
			for(int i=0;i<r;i++) {//taking input of board
				
				arr[i]=s.next();//taking input of each row				
				
				boolean plusOrMinus=false;
				
				while(arr[i].length()!=c || !plusOrMinus) {//making sure each row has c letters and each word consists of 
//					only '-' or '+' as the constituent letters
					
					int len=arr[i].length();
					
					if(len!=c) {//making sure the length condition is satisfied				
						System.out.println("enter a valid word : ");
						arr[i]=s.next();
					}
					else {//making sure that the letter condition is satisfied
						int j=0;
						for(;j<len;j++) {
							
							if(arr[i].charAt(j)!='-' && arr[i].charAt(j)!='+') {
								System.out.println("enter a valid word : ");
								break;
							}
							
						}
						
						if(j==len) {
							plusOrMinus=true;
						}	
						else {
							arr[i]=s.next();
						}
					}														
					
				}																					
				
			}
			
			char  board[][]=new char[r][c];
					
			
			for(int i=0;i<r;i++) {
				for(int j=0;j<c;j++) {
					
					board[i][j]=arr[i].charAt(j);//making the character board from the words we have taken as input
					
				}			
			}							
			
			System.out.println("enter the number of words : ");
			
			int n=s.nextInt();//taking input of number of words
			
			System.out.println("enter the words : ");
			
			String words="";//we are connecting all the words in a single string where the consecutive words are separated
//			by a semicolon(;)
			
			if(n==1) {
				words+=s.next();
			}
			else {
				for(int i=0;i<n;i++) {
					
					words+=s.next();
					
					if(i==(n-1)) {
						continue;
					}
					
					words+=";";
					
				}
			}
			
			if(isFull(board)) {//if the board has no space for any word, i.e. is already full then the board is solved
				System.out.println("the given board is already solved");
			}
			else {
				boolean output=canFill(board, words);//checking if the given words can be filled into the board or not
				
				if(output) {
					System.out.println("the given board can be solved and the solution is : ");
					print(board);//providing the solution if it exists
				}
				else {
					System.out.println("the given board cannot be solved");
				}
			}
			
		}
	}