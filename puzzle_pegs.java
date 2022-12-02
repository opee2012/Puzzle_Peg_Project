// In collaboration with ...

import java.util.*;


public class puzzle_pegs {
    private static final char peg = '\u25CF'; // Represents Pegs
    private static final char hole = '\u25CB'; // Represents Holes

    /* Done */
    // 2D array representing all possible moves
    private static final int[][] moves = {
        { 1, 2, 4 },
        { 1, 3, 6 },
        { 2, 4, 7 },
        { 2, 5, 9 },
        { 3, 5, 8 },
        { 3, 6, 10 },
        { 4, 2, 1 },
        { 4, 5, 6 },
        { 4, 7, 11 },
        { 4, 8, 13 },
        { 5, 8, 12 },
        { 5, 9, 14 },
        { 6, 3, 1 },
        { 6, 5, 4 },
        { 6, 9, 13 },
        { 6, 10, 15 },
        { 7, 4, 2 },
        { 7, 8, 9 },
        { 8, 5, 3 },
        { 8, 9, 10 },
        { 9, 5, 2 },
        { 9, 8, 7 },
        { 10, 6, 3 },
        { 10, 9, 8 },
        { 11, 7, 4 },
        { 11, 12, 13 },
        { 12, 8, 5 },
        { 12, 13, 14 },
        { 13, 12, 11 },
        { 13, 8, 4 },
        { 13, 9, 6 },
        { 13, 14, 15 },
        { 14, 13, 12 },
        { 14, 9, 5 },
        { 15, 10, 6 },
        { 15, 14, 13 }
    };

    // Board History (Array list)
    private List<char[]> boardHist;
    
    
    // Jumps History (Array list)
    private List<String> jumpHist;

    // Starting hole location
    int startLoc;

    // Ending hole location - (optional)
    int endLoc;

    /**
     * Constructor
     * 
     * @param startLoc The starting location of the hole
     * @param endLoc The ending locaton of a peg
     */
    puzzle_pegs(int startLoc, int endLoc) throws IllegalArgumentException {
        // Assign starting hole location
        if (!maintainBounds(startLoc)) {
            throw new IllegalArgumentException("Invalid starting hole, must be range from 1 to 15.");
        } else {
            this.startLoc = startLoc;
        }

        if ((!maintainBounds(endLoc)) && (endLoc != -1)) {
            throw new IllegalArgumentException("Ending location must range between 1 and 15.");
        } else {
            this.endLoc = endLoc;
        }

        boardHist = new ArrayList<>();
        jumpHist = new ArrayList<>();
    }

    /**
     * Checks if argument value is between 1 and 15
     * 
     * @param argValue
     */
    private static boolean maintainBounds(int argValue) {
        if ((argValue > 0) && (argValue <= 15)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean solve() {
        // build board
        char[] board = new char[16];
        board[0] = ' '; // Unused Null Space
        
        for (int i = 1; i < board.length; i++) {
            if (startLoc == i) {
                board[i] = hole;
            } else {
                board[i] = peg;
            }
        }

        // store original board
        var original = board.clone();

        // solve puzzle
        if (solveRecur(board)) {
            System.out.println("Initial Board");
            printBoard(original);

            int boardHistSize = boardHist.size();

            // Print boards for solved & the board history
            for (int i = 1; i < boardHistSize; i++) { 
                // Print jump history
                System.out.println(jumpHist.get(boardHistSize - i));
                printBoard(boardHist.get(i - 1));
            }
            return true;
        } else {
            System.out.println("No solution can be found.");
            return false;
        }
    }

    /**
     * Recursive function making use of backtracking
     */
    private boolean solveRecur(char[] board) {
        // go through every possible jump
        for (var jump : moves) {
            // match PPH pattern
            if ((board[jump[0]] == peg) && (board[jump[1]] == peg) && (board[jump[2]] == hole)) {
                // make the jump
                board[jump[0]] = hole;
                board[jump[1]] = hole;
                board[jump[2]] = peg;
            
                // record new board in history
                var clonedBoard = board.clone();
                boardHist.add(clonedBoard);

                if (solveRecur(board)) {
                    // record jump
                    jumpHist.add("Moved " + jump[0] + " to " + jump[2] + ", jumping over " + jump[1]);
                    return true;
                }

                boardHist.remove(boardHist.size() - 1);
                board[jump[0]] = peg;
                board[jump[1]] = peg;
                board[jump[2]] = hole;
            }
        }

        int count = 0;
        for (var c : board) {
            if (c == peg) count++;
        }
        
        if ((count == 1) && (endLoc == -1)) return true;
        else if ((count == 1) && (board[endLoc] == peg)) return true;
        else return false;

    
    }

    /* Done */
    private static void printBoard(char[] Board) {
        System.out.println("    " + Board[1]);
        System.out.println("   " + Board[2] + " " + Board[3]);
        System.out.println("  " + Board[4] + " " + Board[5] + " " + Board[6]);
        System.out.println(" " + Board[7] + " " + Board[8] + " " + Board[9] + " " + Board[10]);
        System.out.println(Board[11] + " " + Board[12] + " " + Board[13] + " " + Board[14] + " " + Board[15]);
    }


    public static void main(String[] args) {
        
        puzzle_pegs puzzle = new puzzle_pegs(1, -1);
        puzzle.solve();
    }
}