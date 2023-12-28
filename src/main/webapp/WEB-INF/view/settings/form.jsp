<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/component/header.jspf" %>
<div class="container mt-3">
	<h4>내 정보</h4>
	<div class="row gx-3">
		<div class="col-md-4 p-2">
			<div class="h6 my-2">
				<i class="bi bi-person-bounding-box"></i> 프로필 이미지
			</div>
			<div class="my-2">
				<img src="${sessionScope.logonAccount.profileImageUrl }" width="200" height="200" class="rounded-circle"
					style="cursor:pointer;"
					onclick="document.querySelector('#profileImage').click()"
					id="profileImageView"/>
			</div>
			<div style="display:none;">
				<input type="file" class="form-control" id="profileImage" accept="image/*"/>
			</div>
			<div class="h6 my-2">
				<i class="bi bi-person-vcard"></i> 닉네임
			</div>
			<form action="${contextPath }/settings/update/profile" method="post">
				<input type="hidden" name="_method" value="patch"/>
				<div class="my-2">
					<div>
						<input type="text" class="form-control" id="nickname" name="nickname" value="${sessionScope.logonAccount.nickname }"/>
					</div>
					<div>
						<button type="submit" class="form-control btn btn-warning mt-2">변경</button>
					</div>
				</div>
			</form>
		</div>
		<div class="col-md-8 p-2">
			<div class="h6">
				<i class="bi bi-map"></i> 동네 설정
			</div>
			<div style="height:400px;" id="map"></div>
			<form action="${contextPath }/settings/update/address" method="post">
				<input type="hidden" name="_method" value="patch"/>
				<div class="mt-2">
					<input type="text" class="form-control" id="address" name="address" readonly value="${sessionScope.logonAccount.address }"/>
					<input type="hidden" name="latitude" id="latitude"/>
					<input type="hidden" name="longitude" id="longitude"/>
				</div>
				<div>
					<button type="submit" class="form-control btn btn-warning mt-2">변경</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=145c3b70e061a8512d5744ee57476422&libraries=services"></script>
<script>

	// 이미지 미리보기 스크립트
	document.querySelector("#profileImage").onchange = function(e) {
		
		if (e.target.files[0]) {
			var fileReader = new FileReader();
			
			fileReader.readAsDataURL(e.target.files[0]);
		    
		    fileReader.onload = function(e) {
		    	console.log(e.target.result);
		    	document.querySelector("#profileImageView").src = e.target.result;
		    }
		}
	    
	};


	// ### 지도 스크립트 ###
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center : new kakao.maps.LatLng(${latitude == null ? 35.160102 : latitude}, ${longitude == null ? 126.851630 : longitude }), //지도의 중심좌표.
		level : 3
	//지도의 레벨(확대, 축소 정도)
	};

	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

	// 마커가 표시될 위치입니다 
	var markerPosition = new kakao.maps.LatLng(${latitude == null ? 35.160102 : latitude}, ${longitude == null ? 126.851630 : longitude });

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
		position : markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);

	// 지도에 클릭 이벤트를 등록합니다
	// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
	var geocoder = new kakao.maps.services.Geocoder();

	kakao.maps.event.addListener( map, 'click',
		function(mouseEvent) {

			// 클릭한 위도, 경도 정보를 가져옵니다 
			var latlng = mouseEvent.latLng;

			// 마커 위치를 클릭한 위치로 옮깁니다
			marker.setPosition(latlng);

			map.panTo(latlng);
			geocoder.coord2RegionCode(
				latlng.getLng(),
				latlng.getLat(), // 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
				function displayCenterInfo(result,
						status) {
					if (status === kakao.maps.services.Status.OK) {
						var infoDiv = document
								.getElementById('centerAddr');

						for (var i = 0; i < result.length; i++) {
							// 행정동의 region_type 값은 'H' 이므로
							if (result[i].region_type === 'H') {
								document
										.querySelector("#address").value = result[i].address_name;
								break;
							}
						}
					}
				});
			document.querySelector("#latitude").value = latlng.getLat();
			document.querySelector("#longitude").value = latlng.getLng();
		});
</script>
<%@ include file="/WEB-INF/view/component/footer.jspf" %>