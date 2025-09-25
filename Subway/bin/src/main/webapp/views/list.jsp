<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.kh.subway.model.vo.Subway, java.util.List"%>
<%
	// Controller에서 포장해서 보낸거 풀어야함, 그건 여기서
	// ??? 10:46 request.getAttribute("키값") : Object
	List<Subway> orders = (List<Subway>)request.getAttribute("orders");
	// 다른 패키지 클래스 쓰려는거니까 import해야함, 스크립틀릿 추가할건데 여기보다 위에써야함, 페이지지시어 추가, 속성으로 import
	// 여러개는 풀클래스명을 콤마로 구분해서 작성
	// 우항은 형변환 해줘야합니다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문내역확인하기</title>
<style>
	#wrap{
		width: 1600px;
		border: 1px solid lightgrey;
		margin: auto;
		text-align: center;
	}
	
	table{
		box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.4);
	}
</style>
</head>
<body>

	<div id="wrap">
	
		<h1>주문내역보기</h1>
		
		<table>
			<tr>
				<th>주문자명</th>
				<th>전화번호</th>
				<th>주소</th>
				<th>요청사항</th>
				<th>샌드위치</th>
				<th>채소</th>
				<th>소스</th>
				<th>쿠키</th>
				<th>결제수단</th>
				<th>주문일자</th>
			</tr>
			
			<%-- 조회결과 유무에 따라 다른 출력을 원한다 --%>
			<% if(orders.isEmpty()) { %>
				<tr>
					<th colspan="10">조회결과가 존재하지 않습니다.</th>
				</tr>
			<% } else { %>
			
				<%-- 조회 결과가 몇개일지 모르는데 있는만큼 반복해줘야함 --%>
				<% for(Subway s : orders) { %>
					<tr>
						<%-- Subway객체의 필드값에 넣어뒀으니 출력식 사용해야함, s에 참조해서 getter 메소드 호출 --%>
						<td width="130"><%= s.getName() %></td>
						<td><%= s.getPhone() %></td>
						<td><%= s.getAddress() %></td>
						<td><%= s.getRequest() %></td>
						<td><%= s.getSandwich() %></td>
						<td><%= s.getVegetable() %></td>
						<td><%= s.getSauce() %></td>
						<td><%= s.getCookie() %></td>
						<td><%= s.getPayment() %></td>
						<td><%= s.getOrderDate() %></td>
					</tr>
				<% } %>
			
			<% } %>
		</table>
		
	</div>
</body>
</html>