package java_project;

import java.util.Scanner;

//추상화
public class Guest {
    private int age;
    private String name;
    private String gender;

    // 올바른 생성자
    public Guest(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // 정보 출력 메소드
    public void print() {
        System.out.println("Name: " + name + ", Age: " + age + ", Gender: " + gender);
    }

    // Getter 메소드들
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    // Setter 메소드들 (필요한 경우)
    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

//상속 
class Guest_1 extends Guest {
    // Scanner 객체는 사용자 입력을 처리하기 위해 사용됩니다.
    private Scanner scanner = new Scanner(System.in);
    
    // Guest_1의 생성자는 부모 클래스인 Guest의 생성자를 호출하여
    // 이름, 나이, 성별을 초기화합니다.
    public Guest_1() {
        super("김상속", 20, "남성");
    }

    // print 메소드는 게스트와의 대화를 처리합니다.
    // 이 메소드는 사용자의 입력에 따라 다른 메시지를 출력합니다.
    @Override
    public void print() {
        // 실행 플래그 running을 true로 설정하여 대화가 계속 진행될 수 있도록 합니다.
        boolean running = true;
        while (running) {
            // 게스트 정보 출력
            System.out.println("저는 " + getAge() + "살 " + getName() + "입니다.");
            System.out.println("무엇이 궁금하나요?");
            System.out.println("1. 안녕하세요! 지금 드시는 음식은 어떤가요?");
            System.out.println("2. 안녕하세요! 이 음식점의 분위기 어떻게 생각하세요?");
            System.out.println("3. 더 이상 궁금한게 없어요.");
            
            // 사용자로부터 선택을 입력 받습니다.
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            // 사용자의 선택에 따라 적절한 행동을 취합니다.
            switch (choice) {
            case 1:
                // 사용자가 1번을 선택했을 때의 응답
                System.out.println("안녕하세요. 정말 맛있어요. 특히 이 불고기가 아주 부드럽고 맛있네요.");
                break;
            case 2:
                // 사용자가 2번을 선택했을 때의 응답
                System.out.println("안녕하세요. 분위기가 아주 아늑하고 편안해요. 데이트나 가족 모임에 딱 좋은 곳인 것 같아요.");
                break;
            case 3:
                // 사용자가 대화를 종료하길 원할 때
                running = false;
                break;
            default:
                // 유효하지 않은 선택을 했을 때의 응답
                System.out.println("올바른 선택이 아닙니다. 다시 선택해주세요.");
                break;
            }
            try {
    			Thread.sleep(3000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
        }
    }
	
    // finalize 메소드는 객체가 가비지 컬렉션에 의해 처리될 때 호출됩니다.
    // 이 메소드는 Scanner 객체를 안전하게 닫아 자원을 해제합니다.
	protected void finalize() {
        scanner.close();
    }
}

class Guest_2 extends Guest {
    // Scanner 객체 선언: 사용자 입력을 처리하기 위한 스캐너
    private Scanner scanner = new Scanner(System.in);
    
    // 생성자: Guest_2 객체 생성 시 이름, 나이, 성별을 초기화
    public Guest_2() {
        // 부모 클래스의 생성자를 호출하여 게스트의 기본 정보를 설정
        super("이상순", 35, "여성");
    }

    // print 메소드 오버라이드: 사용자와의 인터랙션을 처리
    @Override
    public void print() {
        // 실행 플래그 설정: 대화가 계속 실행되도록 초기값을 true로 설정
        boolean running = true;
        // 실행 루프: 사용자가 종료를 원할 때까지 반복
        while (running) {
            System.out.println("\n저는 " + getAge() + "살 " + getName() +"입니다.");
            System.out.println("무엇이 궁금하나요?");
            System.out.println("1. 안녕하세요. 이 식당 자주 오시나요?");
            System.out.println("2. 안녕하세요. 혹시 무슨 일 있나요?");
            System.out.println("3. 안녕하세요. 혹시 이 근처에서 이상한 소문을 들은 적 있나요?"); // 내용이 비어 있음: 구체적인 정보 필요
            System.out.println("4. 더 이상 궁금한게 없어요.");
            
            // 사용자로부터 입력 받기
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            // 선택 처리: 사용자가 선택한 번호에 따라 적절한 행동 수행
            switch (choice) {
            case 1:
                System.out.println("네, 여기 단골이에요. 주인장과도 친해서 가끔 특별한 메뉴를 제공받기도 해요.");
                System.out.println("오늘은 특별히 준비한 포르마지오 피자가 있다고 들었어요. 한번 주문해 보세요!");
                break;
            case 2:
                System.out.println("제가 중요한 서류가 든 가방을 잃어버렸어요. 요즘 나이를 먹더니 자주 깜빡하네! 허허~");
                break;
            case 3:
                System.out.println("사실, 며칠 전부터 이 주변에서 이상한 소리가 들린다는 이야기를 들었어요.");
                System.out.println("주로 밤에 옆 골목에서 들린다고 하더군요. 아무래도 조심하는 게 좋을 것 같아요.");
                break;
            case 4:
                // 실행 플래그 변경: while 루프를 종료하고 대화를 마침
                running = false;
                break;
            default:
                System.out.println("유효하지 않은 선택입니다. 다시 선택해주세요.");
                break;
            }
            try {
    			Thread.sleep(3000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
        }
    }

    // 자원 해제: 객체가 가비지 컬렉션되기 전에 스캐너를 닫음
    protected void finalize() {
        scanner.close();
    }
}




// 서류가방