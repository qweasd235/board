<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>    
<style>
#uploadedList > div{
	float : left;
	margin : 15px;
}
</style>
<script src="/resources/js/myscript.js"></script>
<script>
$(document).ready(function(){
	//게시글 수정 버튼
	$("#btnModify").click(function(){
// 		console.log("btnModify");
// 		location.href = "/board/modify";
		$("#title").prop("readonly", false);
		$("#content").prop("readonly", false);
// 		$("#writer").prop("readonly", false);
		$(this).hide(600);
		$("button[type=submit]").show(600);
		$(".pull-right").fadeIn(500); //첨부파일 삭제링크 나타내기
	});
	//게시글 삭제 버튼
	$("#btnDelete").click(function(){
// 		console.log("btnDelete");
// 		location.href = "/board/delete?bno=${boardVo.bno}";
		$("#frmList").attr("action", "/board/delete").submit();
	});
	//게시글 목록 가기
	$("#btnListAll").click(function(){
// 		console.log("btnListAll");
// 		location.href = "/board/listAll?page=${pagingDto.page}&perPage=${pagingDto.perPage}";
		$("#frmList").submit();
	});
	
	//댓글 작성 버튼
	$("#btnReply").click(function(){
		var bno = "${boardVo.bno}"; //게시글 번호 (댓글x)
		var reply_text = $("#reply_text").val(); // 댓글내용
		var replyer = $("#replyer").val();  //작성자
		var sendData = {
				"bno" : bno,
				"reply_text" : reply_text,
				"replyer" : replyer
		};
		var url = "/replies/register";
// 		$.post(url, JSON.stringify(sendData), function(rData){
// 			console.log(rData);
// 		});
		$.ajax({
			"type" : "post",
			"url" : url,
			"headers" : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "post"
			},
				"dataType" : "text",
				"data" : JSON.stringify(sendData),
				"success" : function(rData){
// 					console.log(rData);
					replyList();
			}
		}); //$.ajax
	});
	
	//댓글 수정 버튼
	$("#replyList").on("click", ".btnReplyUpdate", function(){
		console.log("댓글 수정버튼");
		var rno = $(this).attr("data-rno");
		var reply_text = $(this).attr("data-reply_text");
		var replyer = $(this).attr("data-replyer");
		$("#modal_rno").val(rno);
		$("#modal_reply_text").val(reply_text);
		$("#modal_replyer").val(replyer);
		$("#modal-a").trigger("click");
	});
	
	//댓글 삭제 버튼
	$("#replyList").on("click", ".btnReplyDelete", function(){
		console.log("댓글 삭제버튼");
		var bno = $(this).attr("data-bno");
		var rno = $(this).attr("data-rno");
		var url = "/replies/delete/" + rno + "/" + bno;
		
		$.ajax({
			"type" : "delete",
			"url" : url,
			"headers" : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "delete"
			},
				"success" : function(rData){
// 					console.log(rData);
					replyList();
			}
		});
	});
	
	//모달창 완료 버튼
	$("#btnModalReply").click(function(){
		var rno = $("#modal_rno").val();
		var reply_text = $("#modal_reply_text").val();
		var replyer = $("#modal_replyer").val();
		
		var sendData = {
			"rno" : rno,
			"reply_text" : reply_text,
			"replyer" : replyer
		};
		
		var url = "/replies/update";
		
		$.ajax({
			"type" : "put",
			"url" : url,
			"headers" : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "put"
			},
				"dataType" : "text",
				"data" : JSON.stringify(sendData),
				"success" : function(rData){
// 					console.log(rData);
					replyList();
					$("#btnModalClose").trigger("click");
				}
		});
	});
	
	// 댓글 목록 가져오기 - 정의
	function replyList(){
		$("#replyList").empty();
		var url = "/replies/all/${boardVo.bno}";
		$.getJSON(url, function(rData){
			console.log(rData);
			var strHtml = "";
			$(rData).each(function(){
				strHtml += "<tr>";
				strHtml += "<td>" + this.rno + "</td>";
				strHtml += "<td>" + this.reply_text + "</td>";
				strHtml += "<td>" + this.replyer + "</td>";
				strHtml += "<td>" + dateString(this.reg_date) + "</td>";
				strHtml += "<td><a href='#modal-container'><button type= 'button' class= 'btn-xs btn-warning btnReplyUpdate'";
				strHtml += "data-rno='" + this.rno + "'";
				strHtml += "data-reply_text='" + this.reply_text + "'";
				strHtml += "data-replyer='" + this.replyer + "'>수정</button></td>";
				strHtml += "<td><button type= 'button' class= 'btn-xs btn-danger btnReplyDelete'";
				strHtml += "data-rno='" + this.rno + "'";
				strHtml += "data-bno='" + this.bno + "'>삭제</button></td>";
				strHtml += "</tr>";
				
			});
			$("#replyList").append(strHtml);  //<tbody>의 자식 엘리먼트로 html을 추가
		});
	}
	
	function getAttachList(){
		var url = "/board/getAttach/${boardVo.bno}";
		$.getJSON(url, function(list){
			console.log("list", list);
			$(list).each(function(){
				var full_name = this;
				var underScoreIndex = full_name.indexOf("_");
				var file_name = full_name.substring(underScoreIndex + 1);
				var el = $("#attach_template").clone();
				el.removeAttr("id");
				el.find("span:first").text(file_name);
				var imgEl = el.find("img");
				if(checkImage(file_name) == true){
					var thumbnailName = getThumbnailName(full_name);
					imgEl.attr("src", "/upload/displayFile?fileName=" + thumbnailName);
				}else{
					imgEl.attr("src", "/resources/images/file_image.png");
				}
				
				el.attr("data-filename", full_name);
				el.find("a").attr("href", full_name);
				
				el.css("display", "block");
				$("#uploadedList").append(el);
			});
		});
		
// 		for(var v = 0; v < 4; v++){
// 			var el = $("#attach_template").clone();
// 			el.css("display", "block");
// 			$("#uploadedList").append(el);
// 		}
	}
	
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
	
	getAttachList();
	replyList();  // 기능 실행
});
</script>
${pagingDto}
<div class="container-fluid">
	<!-- 댓글수정 모달 창 -->
	<div class="row">
		<div class="col-md-12">
			 <a id="modal-a" href="#modal-container" role="button" class="btn" data-toggle="modal"
			 	style="display:none;">Launch demo modal</a>
			
			<div class="modal fade" id="modal-container" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="myModalLabel">
								댓글 수정하기
							</h5> 
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">
							<input type="hidden" id="modal_rno"/>
							<label for="modal_reply_text">댓글내용</label>
							<input type="text" class="form-control"
								id="modal_reply_text"/>
							<label form="model_replyer">작성자</label>
							<input type="text" class="form-control"
								id="modal_replyer"/>
						</div>
						<div class="modal-footer">
							 
							<button type="button" class="btn btn-primary"
								id="btnModalReply">
								수정
							</button> 
							<button type="button" class="btn btn-secondary" data-dismiss="modal"
									id="btnModalClose">
								닫기
							</button>
						</div>
					</div>
					
				</div>
				
			</div>
			
		</div>
	</div>


	<form id="frmList" action="/board/listAll">
		<input type="hidden" name="bno" value="${boardVo.bno}"/>
		<input type="hidden" name="page" value="${pagingDto.page}"/>
		<input type="hidden" name="perPage" value="${pagingDto.perPage}"/>
		<input type="hidden" name="searchType" value="${pagingDto.searchType}"/>
		<input type="hidden" name="keyword" value="${pagingDto.keyword}"/>
		
	</form>
	<div class="row">
		<div class="col-md-12">
			
			<form id="myform" role="form" method="post" action="/board/modify">
			<input type="hidden" name="bno" value="${boardVo.bno}"/>
			<input type="hidden" name="page" value="${pagingDto.page}"/>
			<input type="hidden" name="perPage" value="${pagingDto.perPage}"/>
				<div class="form-group">
					<label for="title">Title</label>
					<input type="text" class="form-control" id="title" name="title"
								value="${boardVo.title}" readonly/>
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">Content</label><br>
					<textarea rows="5" cols="80" id="content" name="content" readonly>
								${boardVo.content}</textarea>
				</div>
				<div class="form-group">
					 
					<label for="writer">Writer</label>
					<input type="text" class="form-control" id="writer" name="writer"
								value="${boardVo.writer}" readonly/>
				</div>
				<!-- 첨부 파일 목록 템플릿: clone해서 사용 -->
				<div id="attach_template" 
					style="display:none;"
					data-filename="">
					<img class="img-thumbnail"><br>
					<span></span>
					<a href="#" class="attach-del">
					<span class="pull-right" style="display:none;">x</span></a>
				</div>
				
				<!-- 첨부파일 이미지 보이기 -->
				<div class="form-group">
					<label for="uploadedList">첨부파일</label>
					<div id="uploadedList">
					
					</div>
				</div>
				
				<div style="clear:both;">
				<button type="submit" class="btn btn-success"
						style="display:none;">Submit</button>
				
				<button type="button" class="btn btn-warning" id="btnModify">Modify</button>
				<button type="button" class="btn btn-danger" id="btnDelete">Delete</button>
				<button type="button" class="btn btn-primary" id="btnListAll">ListAll</button>
				</div>
			</form>
		</div>
	</div>
	
	<hr/>
	<!-- 댓글 작성 -->
	<div class="row">
		<div class="col-md-12">
			<div class="form-group">
				<label>댓글내용</label>
				<input type="text" id="reply_text"
					class="form-control"/>
			</div>
			<div class="form-group">
				<label>작성자</label>
				<input type="text" id="replyer"
					class="form-control"/>
			</div>
			<div class="form-group">
				<button type="button" class="btn btn-success"
						id="btnReply">작성완료</button>
			</div>
		</div>
	</div>
	
	<hr/>
	
	<!-- 댓글목록 -->
	<div class="row">
		<div class="col-md-7">
			<table class="table">
				<thead>
					<tr>
						<th>번호</th>
						<th>댓글내용</th>
						<th>작성자</th>
						<th>날짜</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody id="replyList">
					
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp" %>