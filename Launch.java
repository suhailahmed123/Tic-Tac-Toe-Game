package tictactoe;

import java.util.Random;
import java.util.Scanner;

class TicTacToe{
	static char[][] board; // character array instantiation
	/* default value of char /U0000 */
	public TicTacToe() {
		board = new char[3][3]; // initializing array
		initBoard(); //calling the method after creation of array to store space
	}
	
	void initBoard() { // initializing values in the board/array
		for(int i=0; i<board.length; i++)
		{
			for(int j=0; j<board[i].length; j++)
			{
				board[i][j] = ' '; // initializing with space/empty board
			}
		}
	}
	
	static void dispBoard() {	//displaying the board
		System.out.println("-------------");	//making the board
		for(int i=0; i<board.length; i++) {
			System.out.print("| ");	//making the board
			for(int j=0; j<board[i].length; j++){
				System.out.print(board[i][j] + " | ");
			}
			System.out.println();
			System.out.println("-------------");	//making the board
		}
	}
	
	static void placeMark(int row, int col, char mark) {//placing the mark(O,X) in the board
		if(row >= 0 && row <= 2 && col >= 0 && col <= 2)//adding condition to avoid exception
		{
			board[row][col] = mark;
		}
		else
		{
			System.out.println("Invalid Position");
		}
	}
	
	static boolean checkColWin() {	//checking win
		for(int j=0; j<3; j++) {	// looping through column
			//below condition checks for column values of board and declares WIN
			if(board[0][j] != ' ' && board[0][j] == board[1][j] && 
					board[1][j] == board[2][j]) {
				return true;
			}
		}
		return false;
	}

	static boolean checkRowWin() {	//checking win
		for(int i=0; i<3; i++) {	// looping through row
			//below condition checks for row values of board and declares WIN
			if(board[i][0] != ' ' && board[i][0] == board[i][1] && 
					board[i][1] == board[i][2])
			{
				return true;
			}
		}
		return false;
	}

	static boolean checkDiagWin() {
		if(board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2] ||
				board[0][2]!= ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			return true;
		}
		return false;
	}
	
	static boolean checkDraw() {
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(board[i][j] == ' ') {
					return false;
				}
			}
		}
		return true;
	}
}

abstract class Player{
	String name;
	char mark;
	
	abstract void makeMove();
	
	boolean isValidMove(int row, int col) {	//checks for the valid move 
		if(row>=0 && row<=2 && col>=0 && col<=2) {
			if(TicTacToe.board[row][col] == ' ') {
				return true;
			}
		}
		return false;
	}
}

class HumanPlayer extends Player{
	
	public HumanPlayer(String name, char mark) {	//constructor
		this.name = name;
		this.mark = mark;
	}
	
	void makeMove() {
		Scanner sc = new Scanner(System.in);
		int row;
		int col;
		do {
			System.out.println("enter the row and col");
			row = sc.nextInt();
			col = sc.nextInt();
		}while(!isValidMove(row, col));	//checking for valid move ---> 
			//if it's not a valid move then it will loop until the move is valid
		//the move becomes valid then placeMark() places the mark on the board
		
		TicTacToe.placeMark(row, col, mark);
	}
}

class AIPlayer extends Player{
	
	public AIPlayer(String name, char mark) {	//constructor
		this.name = name;
		this.mark = mark;
	}
	
	void makeMove() {
		Scanner sc = new Scanner(System.in);
		int row;
		int col;
		do {
			Random r = new Random();
			
			// there is a method in Random class which is nextInt() which generates random values
			row = r.nextInt(3);	// 3 is not included cuz boundary is exclusive
			col = r.nextInt(3);
			
		}while(!isValidMove(row, col));	//checking for valid move ---> 
			//if it's not a valid move then it will loop until the move is valid
		//the move becomes valid then placeMark() places the mark on the board
		
		TicTacToe.placeMark(row, col, mark);
	}
}

public class Launch {

	public static void main(String[] args) {
		TicTacToe t = new TicTacToe();
		
		 HumanPlayer p1 = new HumanPlayer("Ahmed", 'O');	//p1 is player-1
		 //HumanPlayer p2 = new HumanPlayer("Thousif", 'X');	//p2 is player-2
		 AIPlayer p2 = new AIPlayer("AI",'X');
		
		 //creating a reference of type HumanPlayer class ----> cp is current player
		 //HumanPlayer cp;	//tight coupling
		 
		 //Parent type reference
		 Player cp;	// loose coupling
		 cp = p1;
		 
		 while(true) {
			 System.out.println(cp.name +" turn");
			 cp.makeMove();
			 TicTacToe.dispBoard();
			 
			 if(TicTacToe.checkColWin() || TicTacToe.checkRowWin() || 
					 TicTacToe.checkDiagWin() || TicTacToe.checkDraw())
			 {
				 System.out.println(cp.name +" has won");
				 break;
			 }
			 else if(TicTacToe.checkDraw()) {
				 System.out.println("Game is a Draw");
				 break;
			 }
			 else {
				 if(cp == p1) {
					 cp = p2;	//exchanging the turns
				 }
				 else {
					 cp = p1;	//exchanging the turns
				 }
			 }
		 }
	}
}
