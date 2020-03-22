import com.board.Board;
import com.board.Direction;
import com.input.ConsoleInput;
import com.input.Input;
import com.player.Player;
import com.sprite.Color;
import com.sprite.Sprite;
import com.sun.istack.internal.NotNull;
import com.util.Move;
import com.util.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Game {

    private static int[][] offsets = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static boolean gameRunning;
    private static boolean inGame;
    private static boolean gameUpdated;
    private static int NUMBER_OF_COLS_AND_ROWS = 8;
    private static final String PARSE_STRING_BY = ",";

    private static Board board;
    private static Player player1;
    private static Player player2;
    private static ConsoleInput input;
    private static Set<Move> moves;


    //TODO function calculate potential moves

    //TODO better algorithm to iterate through this
    private static void makeMove(Set<Move> validMoves, Color color, Board board, Move move) {
        if (!validMoves.contains(move))
            throw new IllegalArgumentException("Invalid move selected");
        else {
            board.flip(board.getSprite(move.getRow(), move.getCol()), color);
            Sprite sprite = board.getSprite(move.getRow(), move.getCol());
            for (Direction direction : Direction.values()) {
                makeMove(board, board.getSprite(sprite, direction), direction, color);
            }


        }
    }

    private static void makeMove(Board board, Sprite sprite, Direction direction, Color color) {
        if(sprite.getColor() == color || sprite.getColor() == Color.BLANK)
            return;
        try{
            board.flip(sprite, color);
            sprite = board.getSprite(sprite, direction);
            makeMove(board, sprite, direction, color);
        } catch (IllegalArgumentException e) {
            return;
        }catch(ArrayIndexOutOfBoundsException e){
            return;
        }

    }

    private static Set<Move> getMoves(@NotNull Board board, Color color) {
        Set<Move> moves = new HashSet<>();
        List<Sprite> sprites = null;
        switch (color) {
            case BLACK:
                sprites = board.getBlackDisks();
                break;
            case WHITE:
                sprites = board.getWhiteDisks();
                break;
            default:
                throw new UnsupportedOperationException("There is no player with a blank color");
        }

        for (Sprite sprite : Objects.requireNonNull(sprites)) {
            for (Direction direction : Direction.values()) {
                Move move = makeLine(board, sprite, direction);
                if (move != null) {
                    if (!moves.contains(move))
                        moves.add(move);
                }


            }
        }

        return moves;

    }

    //TODO divide and conquer would work well here something similar to binary search

    private static Move makeLine(Board board, Sprite sprite, Direction direction) {
        Sprite previousSprite;
        Sprite currentSprite = sprite;

        while (true) {
            try {
                previousSprite = currentSprite;
                currentSprite = board.getSprite(currentSprite, direction);
                if (currentSprite.getColor() == Color.BLANK && previousSprite.getColor() != sprite.getColor())
                    return new Move(currentSprite.getRow(), currentSprite.getColumn());
                else if (currentSprite.getColor() == Color.BLANK && previousSprite.getColor() == sprite.getColor())
                    return null;

            } catch (IllegalArgumentException e) {
                return null;
            }

        }

    }


    private static void init() {
        board = new Board(NUMBER_OF_COLS_AND_ROWS, NUMBER_OF_COLS_AND_ROWS);
        player1 = new Player(Color.BLACK);
        player2 = new Player(Color.WHITE);
        input = new ConsoleInput();
        player1.togglePlayersTurn();
    }


    /* private static Move makeLine(Board board, Sprite sprite, Direction direction, Color originalColor, Color previousColor) {
         if(sprite == null)
             return null;
         else if(sprite.getColor() == null && previousColor!=originalColor)
             return new Move(sprite.getRow(), sprite.getColumn());
         else{

             int row = sprite.getRow() + direction.getCoordinates().getValue0();
             int col = sprite.getColumn() + direction.getCoordinates().getValue1();
             if(board.getRows() >= row || board.getColumns() >= col)
                 return null;
             sprite = board.getSprite(row, col);
             return makeLine(board, sprite, direction, originalColor);
         }

     }*/
    ////TODO function Reverse checker looks like depth first
    private static void getUpdates() {
        if (board.isFull()) return;
        System.out.println(moves);
        Player playerToPlay;
        if (player1.isPlayersTurn())
            playerToPlay = player1;
        else
            playerToPlay = player2;

        String line = null;
        try {
            line = input.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (line == null || line.equals("q")) {
            inGame = false;
            gameRunning = false;
            gameUpdated = false;
        } else if (line.equals("r")) {
            gameUpdated = false;
            inGame = false;
        } else {
            try {
                Pair<Integer, Integer> coordinates = parseString(line);
                makeMove(moves, playerToPlay.getColor(), board, new Move(coordinates.getValue0(), coordinates.getValue1()));
                gameUpdated = true;

            } catch (IllegalArgumentException e) {
                getUpdates();
            }

        }

    }

    private static void togglePlayerTurns() {
        player2.togglePlayersTurn();
        player1.togglePlayersTurn();

    }

    private static void preUpdate() {
        Player playerToPlay;
        if (player1.isPlayersTurn())
            playerToPlay = player1;
        else
            playerToPlay = player2;
        moves = getMoves(board, playerToPlay.getColor());

    }


    private static void updateGame() {
        board.draw();
        togglePlayerTurns();
        gameUpdated = false;
    }


    private static Pair<Integer, Integer> parseString(String line) {

        String[] parsedString = line.split(PARSE_STRING_BY);
        if (parsedString.length != 2)
            throw new IllegalArgumentException("Input string is not properly formatted");
        int x_coordinate = Integer.valueOf(parsedString[0]);
        int y_coordinate = Integer.valueOf(parsedString[1]);
        return new Pair<>(x_coordinate, y_coordinate);

    }


    public static void reset() {
        //TODO read out score here
        board.reset();
    }

    public static void destroy() {
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        board.destroy();
        System.gc();

    }

    private static void initGame() {
        board.init();
        board.draw();

    }


    public static void main(String[] args) {
        gameRunning = true;
        inGame = true;
        gameUpdated = true;
        init();
        while (gameRunning) {
            initGame();
            while (inGame) {
                preUpdate();
                getUpdates();
                if (gameUpdated)
                    updateGame();
            }
            reset();

        }

        destroy();
    }


}
