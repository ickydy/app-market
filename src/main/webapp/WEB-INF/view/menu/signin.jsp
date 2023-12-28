<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container py-5 d-flex flex-column justify-content-center align-items-center">
	<h3 class="py-2">OPEN MARKET</h3>
	<div class="card" style="width: 360px; height:500px;">
	  <div class="card-body d-flex flex-column justify-content-between">
	    <h4 class="card-title">
	    	<span class="d-block">오픈마켓에 오신 것을</span>
	    	<span class="d-block">환영합니다</span>
	    </h4>
	    <ul class="list-group">
		  <li class="list-group-item border border-0">
		  	<a href="${kakaoLoginLink }">
		  		<img src="${contextPath }/resource/icon/kakao_login.png"/>
		  	</a>
		  </li>
		  <li class="list-group-item border border-0">
			  <a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=lU7GLew0nobFRhFpafKh&state=state&redirect_uri=http://192.168.4.123:8080${contextPath }/callback/naver">
			  	<button type="button" style="width:300px; height:45px; border:none; border-radius:7px; background-color:#03C75A; color:#fff;">
			  		<div style="display:flex; align-items:center;">
			  			<div>
			  				<img src="${contextPath }/resource/icon/naver_icon.png" style="width:40px;"/>
			  			</div>
			  			<div style="padding-left:70px; font-size:">
			  				<small>네이버 로그인</small>
			  			</div>
			  		</div>
			  	</button>
			  </a>
		  </li>
		</ul>
	  </div>
	</div>
</div>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>