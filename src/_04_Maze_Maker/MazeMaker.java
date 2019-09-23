package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;
	static Random randy = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start
		Cell startCell = Maze.cells[randy.nextInt(width - 1)][randy.nextInt(width - 1)];

		// 5. call selectNextPath method with the randomly selected cell
		selectNextPath(startCell);
		
		boolean startEdge = randy.nextBoolean();
		int horizontalStart = randy.nextInt(width);
		int verticalStart = randy.nextInt(height);
		if(startEdge) {
			//right
			Maze.cells[0][verticalStart].setWestWall(false);
			Maze.cells[height-1][randy.nextInt(randy.nextInt(height))].setEastWall(false);
		}else {
			//top
			Maze.cells[horizontalStart][0].setNorthWall(false);
			Maze.cells[randy.nextInt(randy.nextInt(width))][height-1].setSouthWall(false);
		}
		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited
		currentCell.setBeenVisited(true);
		// B. Get an ArrayList of unvisited neighbors using the current cell and the
		// method below
		ArrayList<Cell> unvisited = getUnvisitedNeighbors(currentCell);
		// C. if has unvisited neighbors,
		if (unvisited.size() > 0) {
			// C1. select one at random.
			Cell random = unvisited.get(randy.nextInt(unvisited.size()));
			// C2. push it to the stack
			uncheckedCells.push(random);
			// C3. remove the wall between the two cells
			removeWalls(random, currentCell);
			// C4. make the new cell the current cell and mark it as visited
			currentCell = random;
			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		} else
		// D. if all neighbors are visited
		{
			// D1. if the stack is not empty
			if (uncheckedCells.size() != 0) {
				// D1a. pop a cell from the stack
				Cell popped = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell = popped;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
int x1 = c1.getX();
int y1 = c1.getY();
int x2 = c2.getX();
int y2 = c2.getY();

if(x1==x2) {
	//up and down
	if((y1+1)==y2) {
		//down
		c1.setSouthWall(false);
		c2.setNorthWall(false);
	}
	
	if((y1-1)==y2) {
		//up
		c1.setNorthWall(false);
		c2.setSouthWall(false);
		
	}
}

if(y1==y2) {
	//left and right
	if((x1+1)==x2) {
		//right
		c1.setEastWall(false);
		c2.setWestWall(false);
	}
	
	if((x1-1)==x2) {
		//left
		c1.setWestWall(false);
		c2.setEastWall(false);
		
	}
}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		boolean left = c.getX() == 0;
		boolean right = c.getX() == width-1;
		boolean top = c.getY() == 0;
		boolean bottom = c.getY() == height-1;
		ArrayList<Cell> list = new ArrayList<Cell>();
		if(!left) {
			if(!Maze.cells[c.getX()-1][c.getY()].hasBeenVisited()) {
				list.add(Maze.cells[c.getX()-1][c.getY()]);
			}
		}
		if(!right) {
			if(!Maze.cells[c.getX()+1][c.getY()].hasBeenVisited()) {
				list.add(Maze.cells[c.getX()+1][c.getY()]);
			}
		}
		if(!top) {
			if(!Maze.cells[c.getX()][c.getY()-1].hasBeenVisited()) {
				list.add(Maze.cells[c.getX()][c.getY()-1]);
			}
		}
		if(!bottom) {
			if(!Maze.cells[c.getX()][c.getY()+1].hasBeenVisited()) {
				list.add(Maze.cells[c.getX()][c.getY()+1]);
			}
		}
		return list;
	}
}
