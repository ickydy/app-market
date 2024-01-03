<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container mt-3">
	<div class="text-center">
		<div class="row py-lg-5">
			<div class="col-10 mx-auto">
				<div class="card mb-3" style="width: 100%; height:300px; overflow:hidden;">
					<div class="row g-0 align-items-center">
						<div class="col-md-4">
							<img src="${fn:startsWith(sessionScope.logonAccount.profileImageUrl, '/upload') ? contextPath:'' }${sessionScope.logonAccount.profileImageUrl }"
								class="rounded-circle" style="max-height:290px;">
						</div>
						<div class="col-md-8">
							<div class="card-body">
								<h5 class="card-title">${sessionScope.logonAccount.nickname } 님</h5>
								<p class="card-text">
								</p>
								<p class="card-text">
									<small class="text-body-secondary">관심 상품 개수 ${products.size() }</small>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${empty products }">
			관심 상품을 찜해보세요!!
		</c:when>
		<c:otherwise>
			<div class="album p-3 bg-body-secondary rounded mb-5">
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
					<c:forEach var="product" items="${products }">
						<div class="col">
							<div class="card shadow-sm">
								<img alt="" src="${product.images[0].url }" class="card-img-top w-100 object-fit-cover" style="height:200px; overflow:hidden;">
								<div class="card-body">
									<div class="card-text" style="height:50px; overflow:hidden;">
										<div class="h5 text-bold">
											<a href="${contextPath }/product/${product.id }" class="text-truncate link-dark link-underline-opacity-0 link-underline-opacity-0-hover stretched-link">
												${product.title }
											</a>
										</div>
									</div>
									<div class="d-flex justify-content-between align-items-center">
										<div class="h6 text-bold">
											${product.price eq null ? '나눔' : product.price }${product.price eq null ? '' : '원' }
										</div>
										<small class="text-body-secondary">
											<c:set var="key" value="${product.id }"/>
											<i class="bi bi-heart-fill"></i> ${totalPicks[key] }
										</small>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>