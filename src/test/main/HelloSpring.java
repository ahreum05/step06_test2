package test.main;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import test.bean.ScoreVO;
import test.service.ScoreServiceImpl;

@Component("main")
public class HelloSpring {
	@Autowired
	ScoreServiceImpl service;
	Scanner sc = new Scanner(System.in);

	// menu
	public void menu() {
		int choice = 0;

		while (true) {
			System.out.println();
			
			System.out.println("1. 입력");
			System.out.println("2. 출력");
			System.out.println("3. 수정");
			System.out.println("4. 삭제");
			System.out.println("5. 종료");
			System.out.println("-------------");
			System.out.print("번호: ");
			choice = sc.nextInt();
			
			System.out.println();
			switch (choice) {
			case 1:
				input();
				break;
			case 2:
				output();
				break;
			case 3:
				update();
				break;
			case 4:
				delete();
				break;
			case 5:
				System.out.println("종료");
				return;
			default:
				System.out.println("잘못된 번호입니다. 다시 입력해 주세요.");
				break;
			}
			System.out.println();
		}

	}

	// 입력
	public void input() {
		System.out.print("학번: ");
		String studno = sc.next();

		if (service.studnocheck(studno)) {
			System.out.println("이미 존재하는 학번입니다.\n");
			return;
		}

		System.out.print("이름: ");
		String name = sc.next();
		System.out.print("국어: ");
		int kor = sc.nextInt();
		System.out.print("영어: ");
		int eng = sc.nextInt();
		System.out.print("수학: ");
		int mat = sc.nextInt();

		int tot = kor + eng + mat;
		double avg = (double) tot / 3;
		// db 처리
		ScoreVO vo = new ScoreVO();
		vo.setStudno(studno);
		vo.setName(name);
		vo.setKor(kor);
		vo.setEng(eng);
		vo.setMat(mat);
		vo.setTot(tot);
		vo.setAvg(avg);

		int result = service.insertScore(vo);
		if (result > 0) {
			System.out.println("입력 성공");
		} else {
			System.out.println("입력 실패");
		}
	}

	// 출력
	public void output() {
		String title = "학번\t" + "이름\t" + "국어\t" + "영어\t" + "수학\t" + "총점\t" + "평균\t";
		System.out.println(title);
		for (ScoreVO vo : service.getScoreList()) {
			System.out.println(vo);
		}
	}

	// 수정
	public void update() {
		System.out.print("학번: ");
		String studno = sc.next();
		ScoreVO vo = service.getScore(studno);

		if (vo == null) {
			System.out.println("해당 학번의 데이터가 없습니다.\n");
			return;
		}
		System.out.print("이름: ");
		vo.setName(sc.next());
		System.out.print("국어: ");
		vo.setKor(sc.nextInt());
		System.out.print("영어: ");
		vo.setEng(sc.nextInt());
		System.out.print("수학: ");
		vo.setMat(sc.nextInt());
		int tot = vo.getKor() + vo.getEng() + vo.getMat();
		double avg = (double) tot / 3;
		vo.setTot(tot);
		vo.setAvg(avg);

		int result = service.updateScore(vo);
		if (result > 0) {
			System.out.println("수정 성공");
		} else {
			System.out.println("수정 실패");
		}

	}

	// 삭제
	public void delete() {
		System.out.print("학번: ");
		String studno = sc.next();

		if (!service.studnocheck(studno)) {
			System.out.println("해당 학번의 데이터가 없습니다.\n");
			return;
		}
		
		int result = service.deleteScore(studno);
		if (result > 0) {
			System.out.println("삭제 성공");
		} else {
			System.out.println("삭제 실패");
		}

	}

	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext("bean.xml");

		HelloSpring main = (HelloSpring) context.getBean("main");
		// HelloSpring main = context.getBean("main", HelloSpring.class);
		main.menu();

		context.close();
	}
}
