package com.kh.java.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

// COS의 인터페이스 중에 FileRenamePolicy가 있음, 이것을 상속
// FileRenamePolicy 라는 인터페이스를 구현해서
// 이름바꾸기 정책을 사용
public class MyRenamePolicy implements FileRenamePolicy {
	// implements 하면 추상 메소드 구현하라고 빨간줄, 정의되어있고 구현이 안되어있는게 추상메소드, 오버라이딩 해야겠다
	
	// FileRenamePolicy 인터페이스가 가지고 있는 rename추상메소드가 있음~
	// rename메소드를 오버라이딩해서 기존파일명을 전달받아서 파일명을 수정한 뒤
	// 수정한 파일을 반환해줄 것!
	// 오버라이딩이란? 주체가 있어야함, super/high class에서 상속구조부터 시작
	// 상속구조에서 부모클래스가 가지고있는 메소드를 다시 클래스에서 재정의해서 사용하는 것이 오버라이딩
	// 사람의 기억력은 믿을 수 없군
	@Override
	public File rename(File originFile) {
		// 나중에 파일 업로드해서 가지고 오려면 rename이 호출된다, 매개변수로 사용자가업로드한 파일이 들어옴
		
		// 업로드 됐을 때 원본 파일명이
		// "aaa.jpg"
		// "bbb.jpg"
		// "ccc.jpg"
		// 이런식, 이런느낌으로 파일이 올라올건데 이 원본 파일명을 우리 정책을 이용해서 이름을 바꾸고, 이름 바꾼 파일을 반환해주는것이 목적
		// 일단 원본 파일명 필요, 확장자 떼서 써야함, 다른 앞의건 입맛대로 다 바꿔도 확장자는 원래 파일 확장자를 써야함
		// 그래서 원본 파일명을 가지고 오려면? 매개변수가 원본 파일이 담겨있으니까, 지금 File은 java.io.file임
		// 우리 출력했을 때 new file create할 때 사용한 그 File임
		// getName으로 받을 수 있음
		String originName = originFile.getName();
		
		// 이걸 우리 입맛대로 최대한 안겹치게
		// 우리 입맛대로 이름 바꾸기 => 최대한 이름이 안겹치도록
		// KHacademy_ 년월일시분초_ 랜덤값 + 원본 파일 확장자
		// 카카오도 생각이 있어서 했을거니 따라가보자
		/*
		 * 예시)
		 * 원본파일명						바꾸기
		 * bono.jpg				=>		KHacademy_20250930163122_999.jpg
		 * 
		 */
		// 뭐부터? 그냥 문자?
		// 확장자 구하기 먼저
		// 1. 원본파일의 확장자
		// 원본파일은 빼놨음 -> 이건 .jpg같은걸 그대로 들고와야한다, 잘라야 해, 문자열 자르려면? substring
		// 파일명에 점이 여러개 있을 수 있으니 그것도 걸러야함, 제일 뒤의 점부터 잘라야함, substring은 index를 받는다
		// index는 어떻게 얻나? 확장자 글자수는 다를 수 있음, 확장자 점의 마지막 위치도 다를 수 있음
		// 일단 원래 파일 이름에서 인덱스를 얻고싶음, 인덱스의 위치를 알아야함, 점을 줄테니 인덱스를 알아와라, indexOf로 하면 제일 앞의 점을 찾아버림
		// lastIndexOf를 쓴다, 제일 뒤의 점을 기준으로 반환받음, 사실 예외처리도 해야함 파일명에 점이 없으면 예외일어남, 바쁘니까 그냥 함, 나중에 try-catch 하시오
		String ext = originName.substring(originName.lastIndexOf("."));
		
		// 파일 업로드 시점의 연월일시분초로 가정했음, 그걸 얻으려면? new Date, 이게 지금시간
		// Date import는 sql로 하지말고 util로 해야합니다
		// 이 Date 못생겼음, 포맷팅 해야함(요일 어쩌고 KST 이런거 다 나옴, 그냥 숫자만 갖고싶음) -> 형식 내맘대로 할 수 있는 SimpleDateFormat 사용
		// 2. 년월일시분초
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss")
								.format(new Date());
		
		// 3. 랜덤숫자
		// 세자리 100부터 999까지
		// random 메소드 호출값에 900을 곱하면 0부터 899까지다, 100부터 999까지 하고싶으니 + 100 해줌, 그리고 소수점 버리기 int 형변환
		int randomNo = (int)(Math.random() * 900) + 100;
		
		// 이제 다 한거 합쳐줌
		// 1 + 2 + 3
		// 문자열 더하기 싫은데 ㅎㅎ
		String changeName = "KHacacemy_" + currentTime + "_" + randomNo + ext;
		
		/*
		문자열 다루기 잘해야한다
		문제 : 문자열 뒤집기, 몇글자든 뒤집으려면? 
		String str = "abc";
		StringBuilder sb = new StringBuilder();
		sb.append(str);
		sb.reverse(); sb에 있는 리버스 메소드 사용하면됨
		
		아니면 반복문 돌려서 길이보다 작아질때까지 계속 반복하면서 다시 문자열로 붙임
		마지막 인덱스 돌리고 0될때까지 돌려버림
		for(int i = str.length(); i < i--) {
			
		}
		문자열 다루기 어떻게 해야하지? 그럼 apa 같은거 검색해서 찾아서 메소드 호출해서 다루거나
		문자열의 본질 = 바이트형 배열, 그럼 시퀀스니까 바이트 하나씩 다 떼서 어떻게 쓰지 이런 접근을 할수도 있음
		이런걸 잘한다고 개발을 잘하는건 아니지만 이런걸 해야 개발에 대해, 언어에 대해 많이 생각할수있음
		나중에 도움이 될 수는 있지만 했던건 까먹고 의미없음
		그래도 언어의 자료를 다루는 방법에 대해 여러 측면에 대해 생각과 고민을 할 수 있다
		동작하는 것에 대해서 생각해볼 수 있음
		문자열 다루기 숫자 다루기 많이 해보셔
		*/
		// 기존 파일명을 수정된 파일명으로 적용시켜서 반환
		return new File(originFile.getParent(), changeName);
		// 100퍼 안겹친다고 보장은 못함
		// 그러려면 렉을 걸어서 멀티스레딩처리 트랜스퍼 처리를 해줘야함? 근데 그거는 보장이 되는데 뭐 지금 설마 겹칠까? 나중에 해보기로 하자
		// BoardInsertController로 돌아감
		
	}

}
