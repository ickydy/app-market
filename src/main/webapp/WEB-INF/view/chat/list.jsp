<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container mt-3">
	<div class="my-3 p-3 bg-body rounded shadow-sm">
		<h6 class="border-bottom pb-2 mb-0">최근 메세지</h6>
		<c:forEach var="complex" items="${complexs }">
			<div class="d-flex text-body-secondary pt-3">
				<img src="${contextPath }${complex.product.images[0].url}" width="32" height="32" class="rounded me-2 flex-shrink-0"/>
				<p class="pb-3 mb-0 small lh-sm border-bottom">
					<a href="${contextPath }/chat/room/${complex.chatRoom.id}"
						class="d-block text-gray-dark fw-bold link-dark link-opacity-100-hover link-offset-2 link-underline link-underline-opacity-0">${complex.product.title }</a>
					${complex.recentMessage }
				</p>
			</div>
		</c:forEach>
		<small class="d-block text-end mt-3">
			<a href="#">All updates</a>
		</small>
	</div>
</div>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>