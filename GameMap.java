package java_project;

import java.util.Arrays;

public class GameMap {
    private char[][] map;

    public GameMap(int rows, int cols) {
        map = new char[rows][cols]; 
        initializeMap();
    }
    
    public void gameMapFirstFloor() { // 1층 생성 메소
    	initializeMap();
    }
    
    public void gameMapSecondFloor() { // 2층 생성 메소드 
    	initializeMap2();
    }
    
    public boolean isWithinBounds(int x, int y) {
    	return x >= 0 && x < 12 && y >= 0 && y < 12;
    }

    private void initializeMap() {
        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], ' ');
            if (i == 0 || i == map.length - 1) {
                Arrays.fill(map[i], '#');
            } else {
                map[i][0] = map[i][map[i].length - 1] = '#';
            }
        }
        // Additional features
        map[map.length - 1][5] = map[map.length - 1][6] = '_'; // Door
        map[9][7] = map[10][7] = '|'; // Kiosk
        map[2][5] = 'O'; // Counter
        for (int i = 0; i < 10; i++) {
        	map[3][i+1] = '=';
        } // desk
        map[5][5] = 'G';
        map[10][11] = ']'; // 엘리베이터 
       
    }
    
 // 2층 
    private void initializeMap2() {
        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], ' ');
            if (i == 0 || i == map.length - 1) {
                Arrays.fill(map[i], '#');
            } else {
                map[i][0] = map[i][map[i].length - 1] = '#';
            }
        }
        // Additional features
        map[10][11] = ']'; // 엘리베이터 
       
        map[5][5] = 'g';
        
        map[1][1] = '!';
    }
    
    public void printMap(int playerX, int playerY) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == playerX && j == playerY) {
                    System.out.print('P');
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
    }

    public char getCell(int x, int y) {
        return map[x][y];
    }

    
}
