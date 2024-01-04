<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container mt-3 d-flex flex-column position-relative" style="height:85vh;">
	<div class="position-absolute top-0 z-3 bg-dark text-white d-flex p-2 align-items-center gap-3 rounded-3" style="width:94%;">
		<img src="${contextPath }${product.images[0].url }" width="64" height="64" class="rounded-circle"/>
		<span>${product.title }</span>
		<span><fmt:formatNumber value="${product.price }" pattern="#,###"/>원</span>
	</div>
	<div class="flex-grow-1 px-1 overflow-auto z-0" style="margin-top:80px;">
		<p>하이</p>
	</div>
	<div class="position-absolute bottom-0 z-3 input-group" style="width:94%;">
		<input type="text" class="form-control" placeholder="메세지를 입력하세요" aria-label="Recipient's username" aria-describedby="button-addon2">
		<button class="btn btn-outline-secondary" type="button" id="button-addon2">Button</button>
	</div>
</div>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>