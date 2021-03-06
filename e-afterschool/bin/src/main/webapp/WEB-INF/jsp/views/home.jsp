<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jsp" %>

<link href="${pageContext.request.contextPath}/css/box.css" rel="stylesheet" type="text/css">

<style>
#registBtn { background-color: #ffffcc!important; }
#loginBtn { background-color: #ffcc66!important; }
#infoBtn { background-color: #99cc99!important; }
.info-card { background-color: #cccc66!important; }
</style>

<div class="content">
	<div class="home-title justify-content-center align-items-center">
		<span>${city.content} <span class="text-info-600">캠퍼스형</span> 방과후학교</span>
		<span>오신것을 환영합니다.</span>
	</div>
	
	<div class="row mt-4">
		<div class="col-6">
			<button type="button" id="registBtn" class="btn btn-box btn-outline bg-white border-slate-300">
				<div class="py-3">
					<p>처음이십니까?</p>
					<p><span class="text-info-600 fs-18">학 생 등 록</span>&nbsp;&nbsp;을</p>
					<p class="mb-0">먼저 하여 주십시오.</p>
				</div>
			</button>
		</div>
		<div class="col-6"> 
			<button type="button" id="loginBtn" class="btn btn-box btn-outline bg-light border-slate-300">
				<div class="fs-20 text-info-600">
					<span>로&nbsp;&nbsp;&nbsp;그&nbsp;&nbsp;&nbsp;인</span>
				</div>
			</button>
		</div>
	</div>
	
	<div class="row mt-4">
		<div class="col-6">
			<button type="button" id="infoBtn" class="btn btn-box btn-outline bg-light border-slate-300">
				<div class="fs-20 text-white">
					<p><span>프 로 그 램</span></p>
					<p class="mb-0"><span>안&nbsp;&nbsp;&nbsp;&nbsp;내&nbsp;&nbsp;&nbsp;&nbsp;장</span></p>
				</div>
			</button>
		</div>
		<div class="col-6 guidance-content">
			<div class="card info-card">
				<div class="card-body">
					<div class="card-box">
						<div class="text-center">
							수강신청주소<br>
							<span class="fs-11" style="letter-spacing:0rem">
								http://e-afterschool.kr/${city.link}
							</span>
						</div>
						<div class="text-center mt-2">
							수강신청문의<br>
							${city.tel1}
						</div>
						<div class="text-center mt-2">
							기타운영문의<br>
							${city.tel2}
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
$("#registBtn").click(function() {
	location.href = contextPath + "/student/regist?cityId=${city.id}";
});

$("#loginBtn").click(function() {
	location.href = contextPath + "/login/${city.id}";
});

$("#infoBtn").click(function() {
	location.href = contextPath + "/info?cityId=${city.id}";
});
</script>