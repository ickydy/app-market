<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container mt-3">
	<div class="py-5 text-center">
		<div class="row py-lg-5">
			<div class="col-lg-6 col-md-8 mx-auto">
				<h1 class="fw-light">Open Market</h1>
				<div>
					<form action="${contextPath }/">
						<input type="text" name="search" class="form-control mt-5" value="${param.search }">
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="album p-3 bg-body-secondary rounded mb-5">
		<c:choose>
			<c:when test="${empty products }">
				<div class="h4 text-center px-2">
					"${param.search }" 에 일치하는 결과를 찾지 못했습니다.
				</div>
			</c:when>
			<c:otherwise>
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
											${product.account.nickname }
										</small>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<c:if test="${not empty products }">
		<div>
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-center">
					<li class="page-item">
						<a class="page-link ${currentPage ne 1 ? '' : 'disabled' }" href="${contextPath }/index?p=${currentPage - 1}&${param.search}">&lt;</a>
					</li>
					<c:forEach var="i" begin="1" end="${pages }">
						<li class="page-item">
							<a class="page-link ${i eq currentPage ? 'active' : '' }" href="${contextPath }/index?p=${i}&${param.search}">${i }</a>
						</li>
					</c:forEach>
					<li class="page-item">
						<a class="page-link ${currentPage ne pages ? '' : 'disabled' }" href="${contextPath }/index?p=${currentPage + 1}&${param.search}">&gt;</a>
					</li>
				</ul>
			</nav>
		</div>
	</c:if>
</div>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>