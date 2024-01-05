<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container mt-3 d-flex flex-column position-relative g-0" style="height:85vh;">
	<div class="position-absolute top-0 z-3 bg-dark text-white d-flex p-2 align-items-center gap-3 rounded-3"
		style="width:100%; cursor:pointer;" onclick="location.href='${contextPath}/product/${product.id }'">
		<img src="${contextPath }${product.images[0].url }" width="64" height="64" class="rounded-circle"/>
		<span>${product.title }</span>
		<span>
			<c:choose>
				<c:when test="${product.price ne null }">
					<fmt:formatNumber value="${product.price }" pattern="#,###"/>원
				</c:when>
				<c:otherwise>
					나눔 🧡
				</c:otherwise>
			</c:choose>
		</span>
	</div>
	<!-- 채팅뿌려지는곳 -->
	<div class="flex-grow-1 px-1 overflow-auto z-0" style="margin-top:80px; margin-bottom:40px;" id="chatview">
		<div id="0"></div>
		<c:forEach items="${chatMessages }" var="one" varStatus="status">
			<c:choose>
				<c:when test="${status.first }">
					<fmt:formatDate value="${one.sentAt }" pattern="yyyy년 MM월 dd일 E요일" var="now"/>
					<div class="d-flex justify-content-center my-1 align-items-end gap-1">
						<div class="card px-2 py-1 bg-secondary text-white">
							<small>${now }</small>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<fmt:formatDate value="${one.sentAt }" pattern="yyyy년 MM월 dd일 E요일" var="next"/>
					<c:if test="${now != next }">
						<hr/>
						<div class="d-flex justify-content-center my-1 align-items-end gap-1">
							<div class="card px-2 py-1 bg-secondary text-white">
								<small>${now }</small>
							</div>
						</div>
					</c:if>
					<c:set var="now" value="${next }"/>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${one.talkerId eq sessionScope.logonAccount.id }">
					<div id="${one.id }" class="d-flex my-1 justify-content-end align-items-end gap-1">
						<div>
							<c:if test="${one.checkedAt eq null }">
								<div style="font-size:xx-small;">안읽음</div>
							</c:if>
							<div style="font-size:xx-small;"><fmt:formatDate value="${one.sentAt }" pattern="a hh:mm"/></div>
						</div>
						<div class="card px-2 py-1" style="background-color:gold;">${one.content }</div>
					</div>
				</c:when>
				<c:otherwise>
					<div id="${one.id }" class="d-flex my-1 justify-content-start align-items-end gap-1">
						<div class="card px-2 py-1">${one.content }</div>
						<div>
							<span style="font-size:xx-small;"><fmt:formatDate value="${one.sentAt }" pattern="a hh:mm"/></span>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<div class="position-absolute bottom-0 z-3 input-group" style="width:100%;">
		<input type="text" id="msg" class="form-control" placeholder="메세지를 입력하세요" aria-label="Recipient's username" aria-describedby="sendBt">
		<button  type="button" class="btn btn-outline-secondary" id="sendBt"><i class="bi bi-send"></i></button>
	</div>
</div>
<script>

	// 스크롤 맨 밑으로 보내기
	document.querySelector("#chatview").scrollTop = document.querySelector("#chatview").scrollHeight;
	
	// 메시지 등록 ajax 처리
	function sendChatMessage() {
		const val = document.querySelector("#msg").value;
		if (val) {
			const xhr = new XMLHttpRequest();
			xhr.open("post", "${contextPath}/chat/room/${chatRoom.id}/message", true);
			xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
			xhr.send("content=" + val);
			
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {
					var response = JSON.parse(xhr.responseText);
					document.querySelector("#msg").value="";
				}
			}
		}
	}
	
	document.querySelector("#sendBt").onclick = sendChatMessage;
	document.querySelector("#msg").onkeyup = function(e) {
		if(e.keyCode == 13) {
			sendChatMessage();
			// getLatestMessage();
		}
	}
	
	// 추가된 최신 메시지 얻어오는 Ajax 함수
	var logonAccountId = '${sessionScope.logonAccount.id}';
	function getLatestMessage() {
		const id = document.querySelector("#chatview").lastElementChild.id;
		const xhr = new XMLHttpRequest();
		xhr.open("get", "${contextPath}/chat/room/${chatRoom.id}/latest?lastMessageId=" + id);
		xhr.send();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				var response = JSON.parse(xhr.responseText);
				if (response.messages.length == 0) {
					return;
				}
				for ( var msg of response.messages) {
					if (msg.talkerId == logonAccountId) {
						const div = document.createElement("div");
						div.id = msg.id;
						div.className = "d-flex my-1 justify-content-end align-items-end gap-1";
						
							const inDiv1 = document.createElement("div");
								const inDiv3 = document.createElement("div");
								inDiv3.style="font-size:xx-small";
								inDiv3.textContent = "안읽음";
							inDiv1.appendChild(inDiv3);
							
								const inDiv4 = document.createElement("div");
								inDiv4.style = "font-size:xx-small";
								inDiv4.textContent = msg.strSentAt;
							inDiv1.appendChild(inDiv4);
						
						div.appendChild(inDiv1);
						
							const inDiv2 = document.createElement("div");
							inDiv2.className = "card px-2 py-1";
							inDiv2.style.backgroundColor = "gold";
							inDiv2.textContent = msg.content;
						div.appendChild(inDiv2);
						
						document.querySelector("#chatview").appendChild(div);
					} else {
						const div = document.createElement("div");
						div.id = msg.id;
						div.className = "d-flex my-1 justify-content-end align-items-end gap-1";
						
							const inDiv1 = document.createElement("div");
							
							inDiv1.className = "card px-2 py-1";
							inDiv1.textContent = msg.content;
						div.appendChild(inDiv1);
						
							const inDiv2 = document.createElement("div");
							
								const inDiv3 = document.createElement("div");
								inDiv3.style="font-size:xx-small";
								inDiv3.textContent = "안읽음";
							inDiv2.appendChild(inDiv3);
							
								const inDiv4 = document.createElement("div");
								inDiv4.style = "font-size:xx-small";
								inDiv4.textContent = msg.strSentAt;
							inDiv2.appendChild(inDiv4);
							
						div.appendChild(inDiv2);
						
						document.querySelector("#chatview").appendChild(div);
					}
				}
				// 스크롤 다시 맨 밑으로
				document.querySelector("#chatview").scrollTop = document.querySelector("#chatview").scrollHeight;					
			}
		}
	}
	// 1초에 한번씩 자동 호출
	setInterval(getLatestMessage, 1000);
	
</script>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>