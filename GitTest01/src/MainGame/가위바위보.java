package MainGame;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import DB.DAO;
import DB.DTO;
import Util.Util;

public class 가위바위보 {

	// 가위 바위 보를 나타내주는 GESTURES를 이용해 표현하기
	private static final String[] GESTURES = { "가위", "바위", "보" };

	int Life;

	public 가위바위보() {
		Life = 3;
	}

	public boolean play() {

		DAO dao = new DAO();

		ArrayList<DTO> data = dao.searchUser();

		// 경험치
		int exp = data.get(0).getExp();
		int stress = data.get(0).getStress();
		Date firstTime = data.get(0).getFirstTime();
		Date lastTime = data.get(0).getLastTime();

		// 출력문 입력
		// result는 최종 결과 값
		boolean result = true;

		// else if 문을 사용하여 하나의 경우의수를 출력하여 모든 경우의 수를 출력
		for (int i = 0; i < 3; i++) {

			Util.showState(dao.RankCache(), stress, exp);
			// 아스키 코드 입력
			Util.guideLine();
			String[] str = {
					"⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⡽",
					"⢿⣺⣽⢾⣽⢾⣽⢾⣽⢾⣽⢾⣽⢾⣽⢾⣽⢾⣽⢾⣽⢾⣽⢾⣽⡾⡻⡺⣻⢾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⡽⣾⢽⣳",
					"⢿⡽⣾⣻⢾⣻⢾⣻⣞⡿⣞⡿⣞⡿⣞⡿⣞⡿⣞⡿⣞⡿⣞⡿⡑⢕⢌⡒⣜⣯⢿⣳⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⡿⠳⢿⣳⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣟⡷⣿⢽⣻⣳",
					"⣯⢿⡽⣾⣻⣽⣻⣗⣯⡿⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⡟⡍⡆⡣⡑⡆⢎⣾⡽⣯⡷⡿⢽⣯⢿⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⠽⡿⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⣻⣽⠫⠀⠅⡈⡷⣯⢿⣽⣻⢽⣻⣽⣻⣽⣻⣽⣻⣽⣻⡽⣯⢿⡽⣯⣗",
					"⣾⣻⣽⣻⣞⣷⣻⣞⣷⣻⡽⣗⣿⣺⣗⣿⣺⣗⣿⣺⢗⠱⡑⡌⢎⠜⣬⢿⢗⢻⢑⠕⢜⠔⣽⣟⣾⣳⣟⣾⣳⣟⣾⡳⡿⢞⢧⢕⡅⡇⡍⡷⡿⣞⣷⣻⣞⣷⣻⣞⣷⣻⣞⣷⣻⣞⣷⣻⢾⣳⣟⣾⣳⣟⠎⠐⡈⠠⠀⣿⢽⡯⠋⠠⠀⢻⣞⣷⣻⣞⣷⣻⣞⡷⣟⣯⢿⡽⣷⡳",
					"⣿⣺⣗⣿⣺⣗⣿⣺⣗⣯⣿⣻⣞⣷⣻⣞⣷⣻⡾⡫⡊⡪⡸⢨⢊⣾⢏⢇⢣⠱⡘⢜⢌⣪⡾⣞⣷⣻⣞⣷⣻⢞⠱⡑⡜⡌⡆⢇⠝⢮⡊⡗⡌⢆⢻⣞⣷⣻⣞⣷⣻⣞⣷⣻⣞⣷⣻⠐⠠⢈⠹⣾⣳⡏⠌⡀⢂⠨⢐⣯⠏⡐⠈⠄⡁⣺⣻⣞⣷⣻⣞⡷⣯⢿⣽⡽⣯⣟⡷⡯",
					"⣷⣻⣞⣷⣻⣞⣷⣻⣞⡷⣷⣻⣞⡷⢟⠞⡓⡟⢼⡰⡡⠣⡪⣪⠾⣑⢢⠱⡡⢣⠱⣱⣞⣯⣟⣯⣷⣻⣞⣷⢋⠆⡇⡣⡪⡂⡇⢕⠍⡆⣿⠱⡑⢕⢕⢍⢳⣟⣾⣳⣟⣾⣳⣟⣾⣳⣗⠠⠁⠄⠠⢱⡿⡂⢐⠀⢂⠐⣼⠃⡐⠠⠈⠄⢰⣽⣳⠿⢞⡷⣯⢿⣽⣻⢾⡽⣗⣯⣟⡯",
					"⣳⣟⣾⣳⣟⣾⣳⣟⡾⣯⢷⡿⡪⢪⠢⡣⡱⢸⠰⡩⣪⡣⡱⡙⡣⡊⡆⡣⡱⣱⡿⣽⢾⣳⣟⣾⣞⡷⣯⢇⢕⠱⡑⡜⡢⡧⠮⢮⢪⢾⠝⢜⢸⢨⡎⢇⢅⡷⢿⣺⣗⣿⣺⣗⣿⣺⣗⠠⠈⠄⠡⠀⣿⠀⢂⠈⠄⣰⠃⡐⢀⠂⡁⢌⡾⠋⠄⠂⡘⣯⡿⣽⢾⡽⣯⣟⣯⣷⣻⡽",
					"⣟⣾⣳⣟⣾⣳⣟⡾⣯⢿⣽⠣⡪⢢⠣⡪⣸⠰⡑⢕⢔⡇⢎⢌⢆⢣⢪⢞⢞⠿⣽⢯⡿⣽⣞⡷⣯⢿⣽⢑⢅⢇⢣⠪⡢⡙⢷⢵⢓⠕⡍⡪⡢⡏⡪⡸⡰⡝⡸⣽⣞⣷⣻⣞⣷⣻⣞⠀⠡⠈⠄⢁⡇⢂⠐⠠⢥⡃⠄⠂⡐⠠⡰⢋⠀⡂⠌⠠⣸⡯⣿⢽⡯⣟⣷⣻⢾⣞⡷⡯",
					"⣻⣞⣷⣻⣞⡷⣯⢿⣽⣻⡞⡱⡘⡔⢕⠜⡌⡻⢮⠺⡊⢎⠪⡂⢇⡗⢕⢱⠨⡊⣾⣻⣽⢷⡯⣿⢽⣻⢾⡸⡐⢥⢑⠕⡜⡸⡨⡫⡮⣪⣸⠴⡋⡎⡌⡶⡙⡌⢦⢿⣺⣗⣿⣺⣗⣿⡺⢀⠡⠈⡐⠀⡇⠄⠨⠀⢂⠨⠒⣅⢠⠎⠡⠐⢀⠂⠄⡵⣯⠿⣽⢯⣟⣯⣷⣻⣽⢾⣻⡽",
					"⢿⣺⣗⣿⣺⢯⡿⣽⣞⡷⣏⠢⡣⡊⡆⢇⠎⡜⢜⡕⢕⠱⡑⢕⢽⢨⢊⢆⢣⡳⣟⡷⣯⢿⡽⣯⢿⣽⣻⣵⠸⡘⡌⢎⠜⡔⡱⡨⣳⠱⡨⡛⡜⢖⡯⡑⡅⡧⣿⢯⡷⣟⣾⣳⣟⣾⠃⠄⢐⠀⡂⠁⡚⡌⠠⢁⠐⠠⢈⠈⠹⡄⠨⠐⢀⢂⡼⠏⠡⠐⣸⡿⣽⢾⢾⡽⣾⣻⣽⣳",
					"⣟⣷⣻⢾⡽⣯⣟⡷⣯⣟⣇⢇⢪⠢⡱⡡⢣⠪⡪⡎⡪⡊⡪⡊⢎⢗⡕⢗⢍⠲⣘⣯⡿⣽⢯⡿⣽⢾⣽⡚⣧⠱⡑⡅⢇⢕⢜⢰⢱⡱⡑⡜⡸⡐⡍⡳⡹⣼⢿⡽⣯⢿⣺⣗⡿⡞⢈⠐⡀⠂⠄⡁⠄⠹⡀⢂⠈⠄⠂⡈⠄⠘⢆⠡⡰⢉⠂⠨⢀⢼⡯⣿⢽⣻⡯⣿⢽⣞⡷⣗",
					"⣻⡾⣽⢯⣟⡷⣯⣟⡷⣯⢿⡔⢅⢣⠱⡘⡔⢕⢵⠱⡑⡜⢌⢪⠢⣻⢨⠢⡣⣱⣵⡯⣿⢽⡯⣿⢽⡯⣷⣯⡪⢳⢱⡸⡘⡔⡱⡘⡎⢆⢣⢪⢘⢌⢪⢢⣯⣯⢿⡽⣯⢿⡽⣞⡿⡇⢂⠐⢀⠡⠐⢀⠂⢁⡇⠂⠄⡁⢂⠐⠈⠄⡁⠏⡐⠠⢈⢰⣼⢿⡽⣯⣟⣷⣻⣽⢯⣯⣟⣗",
					"⣯⢿⡽⣯⢷⣟⡷⣯⢿⡽⣷⣙⢮⣊⢎⢪⠸⡸⡑⡕⡱⡘⡌⡆⢇⢎⢳⣳⡿⣽⢾⡯⣿⢽⡯⣿⢽⡯⣷⢯⣟⣧⣇⢝⢪⢖⢵⡘⡌⡎⢆⢣⢱⣱⣵⢿⣳⣯⢿⡽⣯⢿⡽⣯⣟⣿⡀⡐⡀⠂⠌⡀⢂⢰⠃⠨⢀⢐⠠⠈⠄⠡⠐⡀⢂⣴⣞⣯⢿⡽⣯⢷⣻⡾⣽⢾⡯⣷⢯⣗",
					"⣿⢽⡯⣿⡽⣷⣻⣯⢿⡽⣯⢿⡶⣌⢫⢣⣇⢣⢣⢱⢸⢨⢪⢸⢨⣮⢾⣯⢿⡽⣯⢿⡽⣯⢿⡽⣯⢿⡽⣯⣟⣷⣻⢷⡷⣼⡴⣽⡾⣾⢾⣻⣻⣽⢯⡿⣽⢾⣻⣽⢯⡿⣽⢷⣯⠗⡙⠴⣀⠅⠡⢐⠠⠑⡈⠔⢐⢀⠂⠅⠌⠄⢅⣲⣟⣷⢯⡿⣽⢯⣟⣯⡷⣟⣯⡿⣽⢯⣟⡧",
					"⣯⡿⣯⡷⣿⣽⢾⡽⣯⢿⣽⢯⣟⡿⣗⣧⣎⡗⣧⡷⣮⣶⣳⢿⡽⣯⢿⣞⣿⣽⣻⣽⣻⣽⢯⣿⢽⣻⣽⣟⣾⣳⣟⣯⢿⣳⣟⣯⡿⣽⡯⣿⡽⣾⣻⣽⢿⣽⣻⢾⣻⣽⡯⣿⣞⣷⡄⠅⠌⡃⠇⣆⠂⠅⡂⢌⢐⠠⡈⡂⢅⣼⣺⣗⡿⣾⣻⣽⢯⣿⢽⣷⣻⣟⡷⣟⣯⣿⢽⣏",
					"⣿⢽⡷⣟⣿⣺⣟⣿⡽⣿⢽⣯⢿⣽⢿⣽⢾⣻⣯⣟⣷⣻⣾⣻⣽⢿⣽⣻⣞⣷⣻⢷⣻⣽⡯⣟⣿⣽⢾⣳⣯⡷⣿⢽⣻⣽⢯⡷⣟⣯⣿⣳⡿⣯⡷⣟⣿⣺⣯⢿⣻⣞⣿⣳⣿⣺⣟⣿⣴⡠⡑⡈⠳⠥⣂⣂⣂⣢⣦⢾⣻⣞⣷⣻⣽⣯⢷⣟⣯⡿⣽⣾⣳⡿⣽⣟⣷⣻⣯⣗",
					"⣿⡽⣟⣯⣷⣟⣷⢯⣿⡽⡿⣾⣻⣽⣟⣾⣟⣿⣺⣽⣾⣻⣞⡿⣾⣻⢷⣻⣽⢾⣻⣻⣽⣾⣻⣯⡷⣟⣿⣽⢾⣻⣽⣟⣯⣟⣯⣿⣻⢷⣯⡷⣿⢷⣟⣯⣷⢿⣾⣻⣯⡷⣟⣷⣻⣾⣽⢾⣳⡿⣷⢮⣬⣨⣜⣿⣻⣽⡾⣿⣽⢾⡯⣿⣺⣽⣯⣟⣷⣟⣿⣺⡷⣟⣿⣺⣷⣻⣞⡧",
					"⣯⡿⣯⡿⣞⣷⣟⣯⣷⢿⣻⣯⢿⡾⣽⡾⣷⣻⣽⡾⣷⣻⢷⣟⣯⣿⣻⣽⣯⢿⣯⢿⣳⣯⣷⣯⢿⣯⡷⣟⣿⢯⣷⣟⣷⢿⣽⢾⣯⡿⣾⣻⣽⣯⢿⣽⢾⡿⣞⣿⣺⣟⣯⣯⣷⢿⣞⣿⢯⡿⣯⣿⣳⡿⣽⣾⣻⣾⣻⢷⣻⣯⡿⣯⡿⣞⣷⢿⣞⣷⣟⣷⣟⣯⣟⣷⢯⣷⣟⡯", };
			String[] value = Util.setMiddle(str);

			Util.print(value);

			System.out.println();
			System.out.println();

			Util.guideLine();

			System.out.println("경험치 \t 스트레스 \t 생성시간 \t\t 현재시간");
			System.out.println(exp + " \t " + stress + " \t " + firstTime + " \t " + lastTime);
			Scanner sc = new Scanner(System.in);

			Util.println("======가위 바위 보 게임======");
			Util.println("     무엇을 내시겠습니까?     ");
			Util.println("     1.가위  2.바위 3.보  ");
			int user = sc.nextInt();

			// 컴퓨터의 랜덤 선택 만들기
			Random ran = new Random();
			int computer = ran.nextInt(3); // => 1, 2 ,3중에서 랜덤 선택

			// user - 1 을 해주는 이유는 제스쳐의 배열의 인덱스는 0부터 시작합니다
			// 즉 , 사용자는 1부터 입력을 해주기 때문에 차이를 맞추기 위해서 -1을 해줍니다
			String userGesture = GESTURES[user - 1];
			String computerGesture = GESTURES[computer];

			Util.println("나의 선택 : " + userGesture);
			Util.println("과장님의 선택 : " + computerGesture);
			if (user == computer + 1) {
				Util.println("과장님이랑 비겼습니다 휴.... -.-");
			} else if ((user == 1 && computer == 2) || (user == 2 && computer == 0) || (user == 0 && computer == 1)) {
				Util.println("내가 과장님을 이겼다! --- ^.^");
			} else {
				Util.println("과장님한테 졌다... ㅠ.ㅠ");
				Life -= 1;
			}
			if (Life == 0) {
				result = false;
				return result;
			}Delay(4000);
			ClearConsole();
		}
		return result;
	}
	
	 public void Delay(int MilTime)
	   {
	      try
	      {
	         Thread.sleep(5000);
	         
	      }
	      catch(InterruptedException  e)
	      {
	         e.printStackTrace();
	      }
	   }

	public void ClearConsole() {
		try {
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
			Process startProcess = pb.inheritIO().start();
			startProcess.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
