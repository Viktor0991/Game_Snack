package com.javarush.games.snake;
import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private Direction direction;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;
    private int turnDelay;
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.GREEN, 7);
    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "Game Over", Color.WHITE, 9);
    }
    private void createNewApple() {
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }while (snake.checkCollision(apple));
    }


    private void drawScene() {
        for (int x = 0; x < getScreenWidth(); x++) {
            for (int y = 0; y < getScreenHeight(); y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        //Apple apple = new Apple(5,5);
        createNewApple();
        isGameStopped = false;
        drawScene();
        snake.draw(this);
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
    }


    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    public void onTurn(int x) {
        snake.move(apple);
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();
        if(apple.isAlive == false){
            createNewApple();
            gameOver();
            score = score + 5;
            setScore(score);
            turnDelay = turnDelay - 10;
            setTurnTimer(turnDelay);
        }
    }




    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case UP : {
                snake.setDirection(Direction.UP);
                break;
            }
            case DOWN : {
                snake.setDirection(Direction.DOWN);
                break;
            }
            case LEFT : {
                snake.setDirection(Direction.LEFT);
                break;
            }
            case RIGHT : {
                snake.setDirection(Direction.RIGHT);
                break;
            }
            case SPACE:
                if (isGameStopped)
                        createGame();


        }
        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        }
        if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        }
        if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        }
        if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        }
    }
}