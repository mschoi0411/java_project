package java_project;

import java.util.EnumMap;

public class Player {
	
	// FlagType 열거형은 Player 클래스에서 사용되는 다양한 상태 플래그를 정의.
	public enum FlagType {
	    KIOSK,        // 키오스크 상호작용 플래그
	    MENU,         // 메뉴 상호작용 플래그
	    END_GAME,     // 게임 종료 플래그
	    GUEST,        // 게스트 상호작용 플래그
	    GUEST2,		 // 2층에 있는 게스트 상호작용 플래그
	    SECOND_FLOOR,  // 2층 이동 플래그
	    
	}


    private int x; // 플레이어의 x 좌표
    private int y; // 플레이어의 y 좌표
    private EnumMap<FlagType, Boolean> flags; // 플레이어의 상태 플래그를 저장하는 EnumMap
    
    // Player 클래스의 생성자
    public Player(int startX, int startY) {
        this.x = startX; // 시작 x 좌표 설정
        this.y = startY; // 시작 y 좌표 설정
        flags = new EnumMap<>(FlagType.class); // FlagType을 기반으로 하는 EnumMap 초기화
        initializeFlags(); // 모든 플래그를 false로 초기화
    }

    // 모든 플래그를 false로 초기화하는 메소드
    private void initializeFlags() {
        for (FlagType flagType : FlagType.values()) {
            flags.put(flagType, false);
        }
    }

    // 플레이어의 이동을 처리하는 메소드
    public void move(char direction, GameMap map) {
        int newX = x, newY = y;
        // 이동 방향에 따라 새 좌표를 계산
        switch (direction) {
            case 'W': newX--; break;
            case 'S': newX++; break;
            case 'A': newY--; break;
            case 'D': newY++; break;
            default:
                System.out.println("Invalid direction: " + direction);
                return; // 유효하지 않은 입력 처리
        }
        // 맵 경계를 벗어나는지 검사
        if (!map.isWithinBounds(newX, newY)) {
            return; // 맵 경계를 벗어나면 이동하지 않음
        }

        char cell = map.getCell(newX, newY); // 새 위치의 셀 값을 가져옴
        // 셀 타입에 따라 플래그 설정 또는 토글
        switch (cell) {
            case ' ':
                x = newX;
                y = newY;
                break;
            case '|':
                setFlag(FlagType.KIOSK, true);
                break;
            case '=':
                if (newX == 3 && newY == 5) {
                    setFlag(FlagType.MENU, true);
                }
                break;
            case '_':
                setFlag(FlagType.END_GAME, true);
                break;
            case 'G':
                setFlag(FlagType.GUEST, true);
                break;
            case ']':
                toggleFlag(FlagType.SECOND_FLOOR);
                break;
            case 'g':
            	setFlag(FlagType.GUEST2, true);
            	break;
         
        }
    }

    // 특정 플래그를 설정하는 메소드
    public void setFlag(FlagType type, boolean value) {
        flags.put(type, value);
    }

    // 특정 플래그의 상태를 가져오는 메소드
    public boolean getFlag(FlagType type) {
        return flags.getOrDefault(type, false);
    }

    // 플래그 상태를 토글하는 메소드
    public void toggleFlag(FlagType type) {
        flags.put(type, !flags.getOrDefault(type, false));
    }
    

    // 플레이어의 x 좌표를 반환하는 메소드
    public int getX() { return x; }
    // 플레이어의 y 좌표를 반환하는 메소드
    public int getY() { return y; }
}