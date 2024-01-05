<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container mt-3">
	<div class="my-3 p-3 bg-body rounded shadow-sm">
		<h6 class="border-bottom pb-2 mb-0">최근 메세지</h6>
		<c:forEach var="complex" items="${complexs }">
			<c:if test="${complex.recentMessage ne null }">
				<div class="d-flex text-body-secondary pt-3" style="cursor:pointer;" onclick="location.href='${contextPath }/chat/room/${complex.chatRoom.id}'">
					<img src="${contextPath }${complex.product.images[0].url}" width="50" height="50" class="rounded me-2 flex-shrink-0"/>
					<div class="pb-3 mb-0 small lh-sm border-bottom w-100">
						<p class="d-block mb-0 ${complex.product.accountId eq sessionScope.logonAccount.id ? 'text-primary' : 'text-gray-dark' } fw-bold">
							${complex.product.title }
						</p>
						<p class="p-0 m-0">
							<span class="d-block mb-1" style="font-size:xx-small;">
								@ ${complex.product.accountId eq sessionScope.logonAccount.id ? complex.buyerAccount.nickname : complex.product.account.nickname }
							</span>
							${complex.recentMessage }
						</p>
					</div>
					<c:if test="${complex.unchecked > 0 }">
						<div>
							<span class="badge rounded-pill text-bg-danger">${complex.unchecked }</span>
						</div>
					</c:if>
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>