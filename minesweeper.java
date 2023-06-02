import java.util.*;

class minesweeper {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		boolean a = true;
		int size = 0;
		while(a){
			System.out.println("Which map would you like to play?(1 = Small/ 2 = Medium/ 3 = Large)");
			size = sc.nextInt();
			sc.nextLine();
			if(size == 1 || size == 2 || size == 3)
				a = false;
		}
		size = size*5;
		int [][] player = new int[size][size];
		for(int i = 0; i < player.length; i++){
			for(int j = 0; j < player.length; j++){
				player[i][j] = 8;
			}
		}
		int [][] hidden = new int[size][size];
		for(int i = 0; i < hidden.length; i++){
			for(int j = 0; j < hidden.length; j++){
				hidden[i][j] = 0;
			}
		}
		int bomb = createMap(size, hidden);
		System.out.println("There are "+bomb+" bombs on the map. Flag them all!");
		playGame(player, hidden, bomb);
	}
	public static int createMap(int size, int[][]arr){
		int bombs = 4;
		if(size == 10){
			bombs = 23;
		}
		if(size == 15){
			bombs = 35;
		}
		for(int i = 0; i < bombs*2;i++){
	        int randomX = (int)(Math.random()*size);
            int randomY = (int)(Math.random()*size);
            if(arr[randomX][randomY] == 9){
	           	i--;
		    }
		    arr[randomX][randomY] = 9;
		}
		return bombs*2;
	}
	public static void printMap(int[][] arr){
		System.out.println("--------------------------------------------------------");
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr.length; j++){
				System.out.print(" "+arr[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("--------------------------------------------------------");
	}
	public static void playGame(int[][]player, int[][] hidden, int bomb){
		Scanner sc = new Scanner(System.in);
		boolean alive = true;
		int row = 0;
		int column = 0;
		int score = 0;
		int[] arr = new int[2];
		System.out.println("Here are the rules: \nTry to find clear the map and flag all bombs to win \nCurrent location is marked as a 7 \nBombs are marked as 9s \nUnsearched tiles are marked as 8s \nFlags are marked as 6's \nDig around to find the non-bomb tiles, which display the number of bombs nearby");
		while(alive){
			printMap(player);
			boolean seechoice = true;
			while(seechoice){
				System.out.println("What do you want to do now?");
				System.out.println("1. Move \n2. Dig \n3. Flag \n4. Give up");
				int choice = sc.nextInt();
				sc.nextLine();
				seechoice = false;
				if(choice == 1){
					arr = move(row,column,player);
					row = arr[0];
					column = arr[1];
				}
				else if(choice == 2)
					alive = dig(player,hidden, row, column, alive);
				else if(choice == 3){
					score = flag(player, hidden, row, column, score);
					System.out.println("You have found "+score+" bombs.");
				}
				else if(choice == 4){
					System.out.println("Sad to see you go. I really thought you were going to be a winner. :(\nAnyways, here is the where the bombs were");
					printMap(hidden);
					alive = false;
				}
				else
					seechoice = true;
				if(score==bomb){
					alive = false;
					System.out.println("You win! \nCongradulations I can't believe you did it!");
				}
			}
		}
	}
	public static int[] move(int row, int column, int[][] player){
		Scanner sc = new Scanner(System.in);
		boolean a = true;
		while(a){
			System.out.println("Which row would you like to go to?");
			row = sc.nextInt();
			sc.nextLine();
			System.out.println("Which column would you like to go to?");
			column = sc.nextInt();
			sc.nextLine();
			a = false;
			if(row>=player.length||column>=player[0].length){
				a = true;
			}
		}
		if(player[row][column] == 8){
			player[row][column] = 7;
		}
		int [] arr = new int[2];
		arr[0] = row;
		arr[1] = column;
		return arr;
	}
	public static boolean dig(int[][] player, int[][] hidden, int row, int column, boolean alive){
		if(hidden[row][column] == 9){
			System.out.println("THERE'S A BOMB THERE!!!! \n You lose L \n Here's where all the bombs were");
			printMap(hidden);
			alive = false;
		}
		else{
			int count = 0;
			int rowCount = hidden.length;
			int columnCount = hidden[0].length;
		if (row >= 0 && row < rowCount && column >= 0 && column < columnCount) {
		    if (row > 0 && hidden[row - 1][column] == 9)
		        count++;
		    if (row < rowCount - 1 && hidden[row + 1][column] == 9)
		        count++;
		    if (column > 0 && hidden[row][column - 1] == 9)
		        count++;
		    if (column < columnCount - 1 && hidden[row][column + 1] == 9)
		        count++;
		    if (row > 0 && column > 0 && hidden[row - 1][column - 1] == 9)
		        count++;
		    if (row > 0 && column < columnCount - 1 && hidden[row - 1][column + 1] == 9)
		        count++;
		    if (row < rowCount - 1 && column > 0 && hidden[row + 1][column - 1] == 9)
		        count++;
		    if (row < rowCount - 1 && column < columnCount - 1 && hidden[row + 1][column + 1] == 9)
		        count++;
		}
			player[row][column] = count;
		}
		return alive;
	}
	public static int flag(int[][] player, int[][] hidden, int row, int column, int score){
		if(hidden[row][column] == 9){
			player[row][column] = 6;
			score = score + 1;
		}
		else{
			System.out.println("Theres no bomb here, so it was not flagged.");
		}
		return score;
	}
}