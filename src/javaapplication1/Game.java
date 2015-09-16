package javaapplication1;

import java.util.Random;

public class Game {
    private int game_id;
    private String game_name;
    private String game_data;
    
    public Game(){
        Random rand = new Random();
        game_id = rand.nextInt(10000);
        game_name = "test" + Integer.toString(game_id);
        game_data = "2015";
    }
    public Game(int id, String name, String data){
        game_id = id;
        game_name = name;
        game_data = data;
    }
    
    public int getGameId(){
        return(game_id);
    }
    
    public String getGameName(){
        return(game_name);
    }
    
    public String getGameData(){
        return(game_data);
    }
    
    public void setGameName(String name){
        game_name = name;
    }
    
    public void setGameData(String data){
        game_data = data;
    }
    
    
}
