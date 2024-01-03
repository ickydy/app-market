<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container mt-3">
	<div class="py-5 text-center">
		<div class="row py-lg-5">
			<div class="col-lg-6 col-md-8 mx-auto">
				<h1 class="fw-light">Open Market</h1>
				<p class="lead text-body-secondary">
					Something short and leading about the collection below—its contents,
					the creator, etc. Make it short and sweet, but not too short so folks don’t simply skip over it entirely.
				</p>
			</div>
		</div>
	</div>
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
									${product.account.nickname }
								</small>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>