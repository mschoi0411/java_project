package java_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


//식품 아이템을 나타내는 클래스
class FoodItem {
    private String name;
    private double price;
  //생성자
    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
  //이름 반환
    public String getName() {
        return name;
    }
  //가격 반환
    public double getPrice() {
        return price;
    }
  //문자열 표현 반환
    // java.lang 패키지에 있는 tostring()호출 -> 클래스 이름, @, 객체해시코드 반환하는 함수
    //오버라이딩 사용하여 재정의
    @Override
    public String toString() {
        return name + ": $" + String.format("%.2f", price);
    }
}

// 메뉴 아이템 추가
class MenuManager {
	// 메뉴 아이템을 저장하는 ArrayList
    private ArrayList<FoodItem> menuItems;
    

    public MenuManager() {
        this.menuItems = new ArrayList<>();
        FoodItem item = new FoodItem("콤비네이션 피자", 20000);
        menuItems.add(item);
        FoodItem item_1 = new FoodItem("페퍼로니 피자", 17000);
        menuItems.add(item_1);
        FoodItem item_2 = new FoodItem("불고기 피자", 21000);
        menuItems.add(item_2);
        FoodItem item_3 = new FoodItem("갈릭 브레드", 6000);
        menuItems.add(item_3);
        FoodItem item_4 = new FoodItem("치킨 윙", 10000);
        menuItems.add(item_4);
        FoodItem item_5 = new FoodItem("감자튀김", 5000);
        menuItems.add(item_5);
    }

    public void addMenuItem(String name, double price) {
        if (checkIfItemExists(name)) {
            System.out.println("이미 등록되어있는 메뉴입니다.");
        } else {
            FoodItem item = new FoodItem(name, price);
            menuItems.add(item);
            System.out.println("메뉴가 성공적으로 추가되었습니다.");
        }
    }
  //아이템 존재 여부 확인
    private boolean checkIfItemExists(String name) {
        return menuItems.stream().anyMatch(item -> item.getName().equalsIgnoreCase(name));
     // ArrayList를 스트림으로 변환하고, anyMatch를 이용하여 주어진 이름과 동일한 아이템이 있는지 확인
    }
    
    
  //이름으로 메뉴 아이템 검색
    public FoodItem findMenuItemByName(String name) {
        return menuItems.stream()
            .filter(item -> item.getName().equalsIgnoreCase(name))
            .findFirst() // 조건을 만족하는 첫 번째 아이템을 반환
            .orElse(null); // 조건을 만족하는 아이템이 없다면 null 반환
    }

  //메뉴 디스플레이
    public void displayMenu() {
        if (menuItems.isEmpty()) {
            System.out.println("메뉴에 아이템이 존재하지 않습니다.");
        } else {
            System.out.println("메뉴:");
            menuItems.forEach(item -> System.out.println(item));
        }
    }
  //메뉴가 비어있는지 확인
    public boolean isEmpty() {
        return menuItems.isEmpty();
    }
}

// 주문을 관리하기 위한 클래스
class Order {
	// 주문된 아이템과 수량을 관리하는 HashMap
    protected HashMap<FoodItem, Integer> orderItems = new HashMap<>();

    // 주문 물품 추가
    public void addItem(FoodItem item, int quantity) {
        if (item != null) {
        	// 아이템을 HashMap에 추가하거나 기존 아이템의 수량을 업데이트
            orderItems.put(item, orderItems.getOrDefault(item, 0) + quantity); 
            //사용자에게 추가된 아이템과 수량을 알림
            System.out.println("주문에 " + quantity + " x " + item.getName() + "가 추가되었습니다.");
        } else { // 아이템이 null인 경우 경고 메시지 출력
            System.out.println("메뉴에서 일치하는 음식을 찾을 수 없습니다.");
        }
    }
    
    // 주문 디스플레이
    public void displayOrder() {
        if (orderItems.isEmpty()) {
            System.out.println("아무 음식도 주문되지 않았습니다."); // 주문된 아이템이 없는 경우 메시지 출력
        } else { // 주문 목록 출력
            System.out.println("주문목록:");
            orderItems.forEach((item, quantity) ->
                System.out.println(item.getName() + " x " + quantity + " = $" + String.format("%.2f", item.getPrice() * quantity)));
//            System.out.println(totalItems()); 
            // 주문된 아이템의 총 가격 계산 후 출력
            System.out.println("총합: $" + String.format("%.2f", calculateTotal()));
        }
    }
    
    public void displayOrdered() {
    
    	if (orderItems.isEmpty())
    		System.out.println("Kiosk는 문 앞쪽에 있습니다. 키오스크에서 주문 부탁드립니다.");

    	else{
    		System.out.print("요청하신 ");
    		orderItems.forEach((item, quantity) ->
            System.out.print(item.getName() + " " + quantity + "개 나왔습니다."));
    		System.out.println(" 맛있게 드세요~!");
    	}
    	
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
//    public int totalItems() {
//        int total = 0;
//        for (int quantity : orderItems.values()) {
//            total += quantity;
//        }
//        return total;
//    }

    private double calculateTotal() { // 스트림을 사용해 각 아이템의 가격과 수량을 곱한 값을 합산
        return orderItems.entrySet().stream()
            .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
            .sum();
    }
   
}
public class Kiosk {
	// 메뉴 관리를 위한 정적 인스턴스
    private static MenuManager manager = new MenuManager();
    // 현재 주문을 관리하는 정적 인스턴스
    private static Order currentOrder = new Order();
    public static Order lastOrder = new Order();
    // 사용자 입력을 받기 위한 스캐너 객체
    private static Scanner scanner = new Scanner(System.in);
    

    public void kiosk_main() {
        boolean running = true; // 프로그램 실행 상태를 제어하는 플래그
        while (running) {
        	// 사용자에게 옵션을 출력
            System.out.println("\n1. 음식메뉴 추가");
            System.out.println("2. 주문하기");
            System.out.println("3. 종료");
            System.out.print("항목을 골라 주세요: ");
            int choice = scanner.nextInt(); // 사용자로부터 선택을 입력 받음
            scanner.nextLine(); // 입력 버퍼를 정리

            switch (choice) {
                case 1:
                    addMenuItem(); // 메뉴 아이템 추가
                    break;
                case 2:
                    placeOrder(); // 주문하기
                    break;
                case 3:
                    running = false; // 프로그램 종료
                    break;
                default:
                    System.out.println("유효하지 않은 항목입니다. 다시 시도해 주세요.");
            }
        }
    }
    //관리자 모드 메소드 추가 
    public void kiosk_main_manager() {
    	System.out.println("*********");
    	System.out.println("*자바 백반*");
    	System.out.println("*오픈 준비*");
    	System.out.println("*********");
        boolean running = true; // 프로그램 실행 상태를 제어하는 플래그
        while(running){
        	// 사용자에게 옵션을 출력
            System.out.println("\n1. 음식메뉴 추가");
            System.out.println("2. 주문하기 테스트");
            System.out.println("3. 오픈 준비 완료");
            System.out.print("항목을 골라 주세요: ");
            int choice = scanner.nextInt(); // 사용자로부터 선택을 입력 받음
            scanner.nextLine(); // 입력 버퍼를 정리

            switch (choice) {
                case 1:
                    addMenuItem(); // 메뉴 아이템 추가
                    break;
                case 2:
                    placeOrder(); // 주문하기
                    break;
                case 3:
                	lastOrder.orderItems.clear(); // 90행 protected 
                    running = false; // 프로그램 종료
                    break;
                default:
                    System.out.println("유효하지 않은 항목입니다. 다시 시도해 주세요.");
                    
            }

        }
    }

    private static void addMenuItem() {
        System.out.print("음식 이름을 입력해주세요: ");
        String name = scanner.nextLine(); // 음식 이름 입력 받기
        System.out.print("가격을 입력해주세요: ");
        double price = scanner.nextDouble(); // 가격 입력 받기
        scanner.nextLine(); // 입력 버퍼 정리
        manager.addMenuItem(name, price); // 메뉴 아이템 추가
    }

    private static void placeOrder() {
        manager.displayMenu(); // 메뉴 출력
        while (!manager.isEmpty()) { // 메뉴가 비어있지 않는 동안 반복
            System.out.print("주문하실 음식이름을 입력해주세요: ");
            String itemName = scanner.nextLine(); // 주문할 음식의 이름 입력 받기
            FoodItem item = manager.findMenuItemByName(itemName); // 입력된 이름으로 음식 찾기

            if (item != null) { // 음식이 메뉴에 있으면
                System.out.print("주문하실 음식 수량을 입력해주세요: ");
                int quantity = scanner.nextInt(); // 수량 입력 받기
                scanner.nextLine(); // 입력 버퍼 정리
                currentOrder.addItem(item, quantity); // 주문에 아이템 추가

                System.out.println("1. 음식 더 추가하기");
                System.out.println("2. 체크아웃하러 가기");
                System.out.print("항목을 선택해주세요: ");
                int nextStep = scanner.nextInt();
                scanner.nextLine();

                if (nextStep == 1) {
                	manager.displayMenu();
                	continue;  // 음식 추가 선택 시 재귀적으로 호출
                } else if (nextStep == 2) {
                    currentOrder.displayOrder(); // 주문 요약 출력
                    
                    lastOrder = currentOrder; 
                    currentOrder = new Order();  // 주문 초기화
                    break;  // 반복 종료
                }
            } else {
                System.out.println("입력을 제대로 해주세요.");
             // 유효하지 않은 입력 처리입력이 제대로 될 때까지 계속 요청
            }
        }
    }
}