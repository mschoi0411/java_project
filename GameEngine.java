package java_project;

import java.util.Scanner;
// 포르마지오 피자 -22000 추가.

public class GameEngine {
    private GameMap gameMap;
    private Player player;
    private boolean running;
    private Kiosk kiosk;
    private Guest_1 guest;
    private Guest_2 guest2;
    private VendingMachine vendingMachin;

    public GameEngine() {
        gameMap = new GameMap(12, 12);
        player = new Player(10, 5);
        running = true;
        kiosk = new Kiosk();
        guest = new Guest_1();
        guest2 = new Guest_2();
        vendingMachin = new VendingMachine();
        
    }

    public void start() {
    	int count = 0;
    	int sum = 0;
        Scanner scanner = new Scanner(System.in);
        kiosk.kiosk_main_manager();
        vendingMachin.vendingMachine_main1();
        vendingMachin.vendingMachine_main2();
        while (running) {
            clearConsole();
            if (player.getFlag(Player.FlagType.SECOND_FLOOR)) { // 2층 입장
                gameMap.gameMapSecondFloor();
            } else { // 1층 재입장
                gameMap.gameMapFirstFloor();
            }

            gameMap.printMap(player.getX(), player.getY());
            
            System.out.print("Move (WASD): ");
            char move = scanner.nextLine().toUpperCase().charAt(0);
            player.move(move, gameMap);
            //키오스크 상호작용
            if (player.getFlag(Player.FlagType.KIOSK)) {
            	if (sum < 3) {
                kiosk.kiosk_main();
                player.setFlag(Player.FlagType.KIOSK, false);
                count = 0;
            	} else {
            		System.out.println("손님 죄송합니다. 재료가 다 마감되었습니다.");
            	}
            }
            // 메뉴를 카운터로 전송
            if (player.getFlag(Player.FlagType.MENU)) {
            	if (count < 1) {
                Kiosk.lastOrder.displayOrdered();
                ++count;
                ++sum;
            	} else {
            		System.out.println(" 이미 주문나왔습니다."); 
            		try {
            			Thread.sleep(3000);
            		} catch (InterruptedException e) {
            			e.printStackTrace();
            		}
            	}
                player.setFlag(Player.FlagType.MENU, false);
          
            }
            // 문으로 나가면 실행 종료
            if (player.getFlag(Player.FlagType.END_GAME)) {
                break;
            }
            if (player.getFlag(Player.FlagType.GUEST)) {
                guest.print();
                player.setFlag(Player.FlagType.GUEST, false);
            }
            if (player.getFlag(Player.FlagType.GUEST2)) {
            	guest2.print();
            	player.setFlag(Player.FlagType.GUEST2, false);
            }
            
        }
        scanner.close();
        
    }

    private void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GameEngine().start();
    }
}
