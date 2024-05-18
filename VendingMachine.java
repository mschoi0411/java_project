package project;

import java.util.ArrayList;
import java.util.Scanner;

//음료 아이템 
class BeverageItem {	
	// 멤버 변수
	private String name;
    private double price;
    
    //생성자
    public BeverageItem(String name, double price) {	
    	this.name = name;
        this.price = price;
    }
    
    // 상품의 정보를 문자열로 반환하는 메서드로, 상품의 이름과 가격을 "상품이름 : 가격원" 형식으로 문자열로 만들어 반환
    
    public String toString() {
    	return String.format("%s\t : %.2f원", name, price);
    }
    
    public String getName() {
		return name;
	}
	
	public void setname(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
    
}

//음료 아이템 추가 

//메뉴 아이템 추가
class VendingMachineManager {
	// 메뉴 아이템을 저장하는 ArrayList
	protected ArrayList<ArrayList<BeverageItem>> trays;

    public VendingMachineManager() {
        // 2차원 ArrayList 초기화
        trays = new ArrayList<>();

        // 각각의 트레이(행)에 음료 메뉴와 재고(열)를 추가
        addTray("물", 1000, 5);
        addTray("콜라", 2000, 5);
        addTray("사이다", 2000, 5);
        addTray("캔맥주", 5000, 5);
    }

    // 트레이(행)에 음료 메뉴와 재고(열)를 추가하는 메소드
    void addTray(String name, double price, int stock) {
        ArrayList<BeverageItem> tray = new ArrayList<>();
        for (int i = 0; i < stock; i++) {
            tray.add(new BeverageItem(name, price));
        }
        trays.add(tray);
    }

	// 이름으로 메뉴 아이템 검색
	public BeverageItem findMenuItemByName(String name) {
	    return trays.stream() // 2차원 ArrayList를 스트림으로 변환
	            .flatMap(ArrayList::stream) // 각 행의 아이템을 평면화하여 하나의 스트림으로 변환
	            .filter(item -> item.getName().equalsIgnoreCase(name)) // 이름이 일치하는 아이템 필터링
	            .findFirst() // 조건을 만족하는 첫 번째 아이템 반환
	            .orElse(null); // 조건을 만족하는 아이템이 없을 경우 null 반환
	}
	    
	 public boolean checkIfItemExists(String name) {
	    return trays.stream()
	            .flatMap(ArrayList::stream) // 2차원 ArrayList를 하나의 스트림으로 평면화
	            .anyMatch(item -> item.getName().equalsIgnoreCase(name)); // 스트림에서 아이템의 이름과 주어진 이름이 일치하는지 확인
	    
	}
	// 메뉴 디스플레이
}

class UsingVm { 

	private int money;	// 자판기에 넣은 돈 킵 

    public static double getMinimumPrice(ArrayList<ArrayList<BeverageItem>> trays) {
        double minimumPrice = Integer.MAX_VALUE;
        for (ArrayList<BeverageItem> tray : trays) {
            for (BeverageItem beverageitem : tray) {
                if (beverageitem != null && beverageitem.getPrice() < minimumPrice) {
                    minimumPrice = beverageitem.getPrice();
                }
            }
        }
        return minimumPrice;
    }
	// 돈을 입력받는 함수 (충분한지 반환한다)
	public boolean inputMoney(int money, ArrayList<ArrayList<BeverageItem>> trays) {
		this.money += money;	// 돈을 덮어쓰면 안되고 더해줘야(+) 함
		double minimumPrice = getMinimumPrice(trays);
		return this.money >= minimumPrice;	// 총 누적 금액이 최소금액보다 크거나 같아야 실행 된다
	}
	
	
	
	// 칸의 개수말고 실제 상품의 개수를 세어주는 함수
	private int countOfTray(ArrayList<BeverageItem> tray) {
	    int count = 0;
	    for (BeverageItem item : tray) {
	        if (item != null) {
	            count++;
	        }
	    }
	    return count;
	}
	
	// 상품 내역과 가격, 수량을 출력하는 함수
	void show(ArrayList<ArrayList<BeverageItem>> trays) {
	    System.out.println();
	    for (int i = 0; i < trays.size(); i++) {
	        ArrayList<BeverageItem> tray = trays.get(i);
	        if (!tray.isEmpty()) {
	            BeverageItem item = tray.get(0); // 각 트레이의 첫 번째 상품 
	            int count = countOfTray(tray); // 해당 트레이의 실제 상품 개수 
	            System.out.printf("<%d> %s (남은 수량: %d개)\n", i+1, item.toString(), count);
	            
	        }
	    }
	}

	
	// 특정 상품 하나를 반환하는 함수
	public BeverageItem getBeverageItem(int select, ArrayList<ArrayList<BeverageItem>> trays) {
	    select -= 1;
	    BeverageItem beverageItem = null;
	    for(int i = trays.get(select).size() - 1; i >= 0; i--) {
	        BeverageItem item = trays.get(select).get(i);
	        boolean flag1 = item != null;
	        boolean flag2 = flag1 && item.getPrice() <= money;
	        if(flag2) {
	            beverageItem = item;        // 상품을 담고
	            trays.get(select).remove(i);    // 트레이에서 상품을 제거
	            money -= beverageItem.getPrice();    // 넣은 돈에서 상품 금액만큼 차감한다
	            break;
	        }
	    }
	    return beverageItem;
	}
	
	
	// 돈이 충분한지 판별하여 반환하는 함수
	public boolean isEnoughMoney(ArrayList<ArrayList<BeverageItem>> trays) {
		return getMinimumPrice(trays) <= money;
	}
	
	// 거스름돈을 반환하는 함수
	public int getChange() {
		int change = this.money;
		this.money = 0;
		return change;
	}

}


public class VendingMachine {
	
	private static VendingMachineManager vendingMachineManager = new VendingMachineManager();
	private static UsingVm order = new UsingVm();
	//사용자 입력 
	private static Scanner sc = new Scanner(System.in);
	private static Scanner sc_add = new Scanner(System.in);
	int money, select;
	
	// 자판기 설정 
	public void vendingMachine_main1() { 
		System.out.println();
		System.out.println("관리자 모드로 자판기를 실행합니다");
	    	
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	
	        boolean running = true; // 프로그램 실행 상태를 제어하는 플래그
	        while(running){
	        	// 사용자에게 옵션을 출력
	            System.out.println("\n1. 음료 메뉴 추가");
	            System.out.println("2. 추가할 메뉴 없음");
	            System.out.print("항목을 골라 주세요: ");
	            
	            int choice = sc_add.nextInt(); // 사용자로부터 선택을 입력 받음
	            sc_add.nextLine(); // 입력 버퍼를 정리
	
	            switch (choice) {
	            
	                case 1:
	                	addMenuItem(); // 메뉴 아이템 추가
	                    break;
	              
	                case 2:        	
	                    running = false; // 프로그램 종료
	                    break;
	                    
	                default:
	                    System.out.println("유효하지 않은 항목입니다. 다시 시도해 주세요.");
                    
	            }
	        }
	}
	
	//자판기 사용 
	public void vendingMachine_main2() {
		
		while(true) {// 투입금액이 구입 가능한 금액이 될 때까지 입력받음
			
			System.out.println(); 
			System.out.println("===========상품 목록=============");	
			
			order.show(vendingMachineManager.trays);
			System.out.println("\n*주문을 원하지 않으시면 0을 입력하세요*"); 
			
			System.out.println("===============================");	
			
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println();	
			System.out.println("현금을 투입하세요");	
			System.out.println("투입할 금액을 입력하세요 : ");	
			money = Integer.parseInt(sc.nextLine());
			

			boolean ready = order.inputMoney(money, vendingMachineManager.trays);	// 돈을 자판기에 전달하고
			if(ready) {	
				break;	
			}
			
			if ((ready == false)&&(money == 0)) {
				break;
			}

		}
		
		while(true) {
			if (order.inputMoney(money, vendingMachineManager.trays) == false) { break;}
			
			System.out.println("===========상품 목록=============");	
			order.show(vendingMachineManager.trays);
			System.out.println("\n*주문을 멈추시려면 0을 입력하세요*"); 
			System.out.println("===============================");	
			System.out.println(); 
			System.out.print("원하는 음료의 번호를 선택하세요 : ");  
			
			try {
				select = Integer.parseInt(sc.nextLine());
				if(select == 0) { break;} // 0 입력하면 거스름돈 페이지로 넘어감 
			} catch (NumberFormatException e) {
				System.out.println();
				System.out.println("잘못된 입력! 숫자로 다시 입력하세요");
				System.out.println();
				
				try {
					Thread.sleep(2200);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				
				continue;
			}
			
			BeverageItem beverage = order.getBeverageItem(select, vendingMachineManager.trays);
			
			
			if(beverage == null) {
				System.out.println();
				System.out.println("상품이 없거나 금액이 충분하지 않습니다");
				
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println();
				System.out.println(beverage.getName() + " 하나 나왔습니다");
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (!order.isEnoughMoney(vendingMachineManager.trays) || vendingMachineManager.trays.isEmpty()) {
				break;
			}
		}
		
		int change = order.getChange();
		System.out.println("====================");
		System.out.printf("거스름돈 : %,d원\n", change);
		System.out.println("거스름돈을 챙겨가세요");
		System.out.println("====================");
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
    private static void addMenuItem() {
    	while(true) {
	    	System.out.println();
	        System.out.print("자판기에 추가할 음료 이름을 입력해주세요: ");
	        String name = sc_add.nextLine(); // 음식 이름 입력 받기
	        if(vendingMachineManager.checkIfItemExists(name)) {
	        	
	        	System.out.println("이미 추가한 음료입니다.");
	        	
	        	try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        	
	        	break;
	        }
	        System.out.print("가격을 입력해주세요: ");
	        double price = sc_add.nextDouble(); // 가격 입력 받기
	        System.out.print("입고되는 수량을 입력해주세요: ");
	        int stock = sc_add.nextInt(); // 진열 수량 입력 받기
	        //진열 수량이 10개를 넘어가면 추가 불가 
	        if (stock > 10)
	        {
	        	System.out.println("------------------------------------");
	        	System.out.println("<WARNING>");
	        	System.out.println("한 칸에 음료는 최대 10개를 진열할 수 있습니다.");
	        	System.out.println("------------------------------------");
	        	
	        	try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        	
	        	break;
	        }
	        sc_add.nextLine(); // 입력 버퍼 정리
	        vendingMachineManager.addTray(name, price, stock); // 메뉴 아이템 추가
	        break;
    	}
    }

}