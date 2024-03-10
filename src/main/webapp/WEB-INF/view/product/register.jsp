<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf"%>
<div class="container mt-3">
	<h4>내 물건 팔기</h4>
	<div class="border-top">
		<form action="${contextPath }/product/register" method="post" enctype="multipart/form-data" onsubmit="syncFileState();">
			<div class="my-3">
				<input type="file" id="image" name="images" style="display: none;" multiple accept="image/**" />
				<div class="d-flex align-items-start">
					<button type="button" class="btn btn-dark" style="--bs-btn-padding-y: 2.0rem; --bs-btn-padding-x: 2.0rem; --bs-btn-font-size: 30px;" onclick="document.querySelector('#image').click();">
						<div style="width: 40px; height: 40px;">
							<i class="bi bi-camera"></i>
						</div>
					</button>
					<div class="d-flex" style="overflow-x: auto;" id="imageView">
						<!-- javascript로 들어옴
							<div class="mx-1 rounded position-relative" style="overflow:hidden; min-width:105.8px;">
								<img alt="image" src="/resource/icon/kakao_login.png" width="105.8" height="105.8" class="object-fit-cover">
								<button type="button" class="btn-close position-absolute top-0 end-0" aria-label="Close"></button>
							</div>
						 -->
					</div>
				</div>
			</div>
			<div class="my-3">
				<h6 style="font-weight: bold;">제목</h6>
				<input type="text" class="form-control" id="title" name="title" />
			</div>
			<div class="my-3">
				<h6 style="font-weight: bold;">거래방식</h6>
				<div>
					<input type="radio" class="btn-check" name="type" value="sell" id="sell" autocomplete="off" checked> <label class="btn btn-outline-dark" for="sell">판매하기</label> <input type="radio" class="btn-check" name="type" value="free" id="free" autocomplete="off"> <label class="btn btn-outline-dark" for="free">나눔하기</label>
				</div>
				<div class="input-group my-2">
					<span class="input-group-text">￦</span> <input type="number" id="price" name="price" class="form-control" step="1000" min="1000" placeholder="가격을 입력해주세요 (천원단위)" />
				</div>
			</div>
			<div class="my-3">
				<h6 style="font-weight: bold;">자세한 설명</h6>
				<div>
					<textarea class="form-control" id="description" name="description" rows="6" style="resize: none;" placeholder="신뢰할 수 있는 거래를 위해 자세히 적어주세요"></textarea>
				</div>
				<div class="position-relative bottom-0 end-0">
					<span id="length">0</span> / 1000
				</div>
			</div>
			<button type="button" class="btn btn-sm btn-secondary mt-2">자주쓰는 문구</button>
			<div class="my-3">
				<button type="submit" class="btn form-control btn-dark">작성완료</button>
			</div>
		</form>
	</div>
</div>
<script>

	const fileState = [];

	// 파일 선택시 이미지 미리보기
	document.querySelector("#image").onchange = function(e) {
		
		/*
			// 이전에 선택되어있던 것들 삭제
			const imageView = document.querySelector("#imageView");
			while (imageView.firstChild) {
				imageView.removeChild(imageView.firstChild);
			}
			
		*/
			// 새로 선택된 이미지 세팅
			const files = [...document.querySelector("#image").files]
		
		for (let f of files) {
			fileState.push(f);
		}
		
		files.forEach(function(file) {
			const fileReader = new FileReader();
			fileReader.readAsDataURL(file);
			fileReader.onload = function(e) {
				const src = e.target.result;
				
				const div = document.createElement("div");
				div.className = "mx-1 rounded position-relative";
				div.style.overflow = "hidden";
				div.style.minWidth = "105.8px";
				
				const img = document.createElement("img");
				img.src = e.target.result;
				img.width = 105.8;
				img.height = 105.8;
				img.className = "object-fit-cover";
				div.appendChild(img);
				
				const button = document.createElement("button");
				button.type = "button";
				button.className = "btn-close position-absolute top-0 end-0";
				button.ariaLabel = "Close";
				div.appendChild(button);
				
				button.onclick = function() {
					document.querySelector("#imageView").removeChild(this.parentNode);
				}
				
				document.querySelector("#imageView").appendChild(div);
			}
		});
	}
	
	function syncFileState() {
		document.querySelector("#image").files = fileState;
	}
	
	// 나눔하기 선택시 가격 설정 불가
	document.querySelector("#free").onclick = function(e) {
		
		const price = document.querySelector("#price");
		price.value = "";
		price.placeholder = "나눔하기";
		price.disabled = true;
		
	}
	
	// 판매하기 선택시 원래대로
	document.querySelector("#sell").onclick = function(e) {
		
		const price = document.querySelector("#price");
		price.value = "";
		price.placeholder = "가격을 입력해주세요 (천원단위)";
		price.disabled = false;
		
	}
	
	// 글자수 제한
	document.querySelector("#description").onkeyup = function(e) {
		
		const description = document.querySelector("#description");
		const text = e.target.value;
		
		const length = document.querySelector("#length");
		length.firstChild.nodeValue = text.length;
		
		if (text.length > 1000) {
			window.alert("최대 작성 가능글자를 초과하였습니다. (최대 1000자)");
			event.target.value = event.target.value.substr(0, 1000);
			document.querySelector('#length').firstChild.nodeValue = 1000;
		}
		
	}
	
</script>
<%@ include file="/WEB-INF/view/component/footer.jspf"%>