<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>
<style>
#fileDrop{
	width: 80%;
	height: 100px;
	border: 1px dashed blue;
	background-color: yellow;
	margin: auto;
}
#buttonDiv{
	clear : both;
}
#uploadedList > div{
	float : left;
	margin : 20px;
}
</style>
<script src="/resources/js/myscript.js"></script>
<script>
$(document).ready(function(){
	$("#btnListAll").click(function(){
		$("#frmList").submit();
	});
	
	$("#fileDrop").on("dragenter dragover", function(e){
		e.preventDefault();
	});
	
	$("#fileDrop").on("drop", function(e){
		e.preventDefault(); // 브라우저로 파일 열기 안하기
// 		console.log(e);
		var file = e.originalEvent.dataTransfer.files[0];
// 		console.log(file);
		var formData = new FormData(); // <form>
		formData.append("file", file); // <input name="file"/>
		
		var url = "/upload/uploadAjax"; // UploadController.java
		
		// <form enctype="multipart/form-data">
		// -> 기본값 : application/x-www-from-urlencoded
		// "processData" : false, "contentType" : false
		
		$.ajax({
			"type" : "post",
			"url" : url,
			"processData" : false,
			"contentType" : false,
			"data" : formData,
			"success" : function(fullName){
					console.log(fullName); // 2020/1/20/
					//파일명 얻기
					var underScoreIndex = fullName.indexOf("_");
					var fileName = fullName.substring(underScoreIndex + 1); // Tulips.jpg
					//썸네일 이미지의 이름 얻기	
					var thumbnailName = getThumbnailName(fullName); // myscript.js
					console.log("thumbnailName: " + thumbnailName);
					var isImage = checkImage(thumbnailName);
					console.log("isImage:" + isImage);
					var html = "<div data-filename='"+fullName+"'>";
					if(isImage == true){
						html += 
					"<img class='img-thumbnail' src='/upload/displayFile?fileName=" + thumbnailName + "'/><br>";
							 											
					}else{
						html +=  
						"<img class='img-thumbnail' src='/resources/images/file_image.png'/><br>";
							
							
					}
					html += "<span>" + fileName + "</span>";
					html += "<a href='"+fullName+"' class='attach-del'><span class='pull-right'>x</span></a>";
					html += "</div>";
					$("#uploadedList").append(html);
				}
			}); //$.ajax()
		}); //$("#fileDrop").on("drop"
				
		//Submit 버튼
		$("#btnSubmit").click(function(){
			var upDiv = $("#uploadedList > div");
			upDiv.each(function(index){
				var filename = $(this).attr("data-filename");
				console.log(filename);
				var hiddenInput = "<input type='hidden' name='files["+index+"]'" +
								" value='"+filename+"'/>";
				$("#registerForm").prepend(hiddenInput);
			});
			$("#registerForm").submit(); //폼 전송
		});//$("#btnSubmit").click
		
		// 첨부 파일 삭제 링크
		$("#uploadedList").on("click", ".attach-del", function(e){
			var that = $(this);
			e.preventDefault();
			var fullName = that.attr("href");
			console.log("fullName: " + fullName);
			var url = "/upload/deleteFile";
			var sendData = {"fileName" : fullName};
			$.get(url, sendData, function(rData){
				console.log(rData);
				if(rData == "success"){
					that.parent().remove();
				}
			});
		});
	});

</script>    
${pagingDto}
<div class="container-fluid">
	<form id="frmList" action="/board/listAll">
		<input type="hidden" name="page" value="${pagingDto.page}"/>
		<input type="hidden" name="perPage" value="${pagingDto.perPage}"/>
	</form>
	<div class="row">
		<div class="col-md-12">
			<!-- action을 생략하면 현재 경로: /board/register -->
			<form id="registerForm" role="form" method="post" action="/board/register">
				<input type="hidden" name="perPage" value="${pagingDto.perPage}"/>
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" class="form-control" id="title" name="title"/>
				</div>
				<div class="form-group">
					 
					<label for="exampleInputPassword1">Content</label><br>
					<textarea rows="5" cols="80" id="content" name="content"></textarea>
				</div>
				
<!-- 				<div class="form-group"> -->
<!-- 					<label for="writer">Writer</label> -->
<!-- 					<input type="text" class="form-control" id="writer" name="writer" -->
<%-- 							value="${memberVo.userid}" readonly/> --%>
<!-- 				</div> -->
				
				<!-- 파일 첨부 -->
				<div class="form-group">
					<label for="fileDrop">첨부할 파일을 드래그 &amp; 드롭하세요.</label>
					<div id="fileDrop"></div>
				</div>
				
				<!-- 썸네일 이미지 -->
				<div class="form-group" id="uploadedList">
				
				</div>
				
				<div class="form-group" id="buttonDiv">
					<button type="button" class="btn btn-primary" id="btnSubmit">Submit</button>
					<button type="button" class="btn btn-success" id="btnListAll">ListAll</button>
				</div>
			</form>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp" %>