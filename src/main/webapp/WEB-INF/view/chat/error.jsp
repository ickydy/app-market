<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	window.alert("삭제된 채팅방이거나\n접근권한이 없는 채팅방입니다.");
	location.href = '${pageContext.servletContext.contextPath}/chat/list';
</script>