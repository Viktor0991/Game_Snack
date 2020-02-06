package com.javarush.games.snake;


import com.javarush.engine.cell.*;

import java.security.UnresolvedPermission;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    public int x;
    public int y;
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public void setDirection(Direction direction) {
        switch (this.direction) {
            case LEFT:
            case RIGHT:
                if (snakeParts.get(0).x == snakeParts.get(1).x) return;
                break;
            case UP:
            case DOWN:
                if (snakeParts.get(0).y == snakeParts.get(1).y) return;
                break;
        }
        this.direction = direction;
    

        if (direction == Direction.LEFT && this.direction == Direction.RIGHT){
            return;
        }
        if (direction == Direction.RIGHT && this.direction == Direction.LEFT){
            return;
        }
        if (direction == Direction.DOWN && this.direction == Direction.UP){
            return;
        }
        if(direction == Direction.UP && this.direction == Direction.DOWN){
            return;
        }
        this.direction = direction;
    }

    public Snake(int x, int y) {
        GameObject firstPart = new GameObject(x, y);
        GameObject secondPart = new GameObject(x + 1, y);
        GameObject thirdPart = new GameObject(x + 2, y);
        snakeParts.add(0, firstPart);
        snakeParts.add(1, secondPart);
        snakeParts.add(2, thirdPart);
    }

    public void draw(Game game){
        if (isAlive == false){
            game.setCellValueEx(snakeParts.get(0).x,snakeParts.get(0).y,Color.NONE,HEAD_SIGN,Color.RED,75);
            for (int i = 1; i!= snakeParts.size(); i++){
                game.setCellValueEx(snakeParts.get(i).x,snakeParts.get(i).y,Color.NONE,BODY_SIGN,Color.RED,75);
            }
        } else {
            game.setCellValueEx(snakeParts.get(0).x,snakeParts.get(0).y,Color.NONE,HEAD_SIGN,Color.GREEN,75);
            for (int i = 1; i!= snakeParts.size(); i++){
                game.setCellValueEx(snakeParts.get(i).x,snakeParts.get(i).y,Color.NONE,BODY_SIGN,Color.GREEN,75);
            }
        }
    }

    /*public void move(){
        createNewHead();
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH
                || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT) {
            isAlive = false;
        } else {
            snakeParts.add(0, newHead);
            removeTail();
        }
    }*/

   public void move(Apple apple) {
       GameObject newHead = createNewHead();
       if (checkCollision(newHead)) {
           isAlive = false;
           return;
       }

       if (newHead.x < 0 | newHead.x > SnakeGame.WIDTH-1 | newHead.y < 0 | newHead.y > SnakeGame.HEIGHT-1){
           isAlive = false;
           return;
       }
       snakeParts.add(0, newHead);
       if (apple.x == newHead.x && apple.y == newHead.y) {
           apple.isAlive = false;
           return;
       }
   }

    public  GameObject createNewHead (){
        GameObject gameObject = new GameObject(0,0);
        if (direction==Direction.UP){
            gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y-1);
        }else if (direction==Direction.DOWN){
            gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y+1);
        }else if (direction==Direction.LEFT){
            gameObject = new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y);
        }else if (direction==Direction.RIGHT){
            gameObject = new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
        }
        return gameObject;
    }
    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }
    public boolean checkCollision(GameObject gameObject) {
        for (GameObject obj : snakeParts) {
            if (gameObject.x == obj.x && gameObject.y == obj.y) {
                return true;
            }
        }
        return false;
    }
    public int getLength(){
       return snakeParts.size();
    }
}