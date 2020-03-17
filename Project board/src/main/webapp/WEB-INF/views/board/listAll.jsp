<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp"%>

<script>
$(document).ready(function(){
	var msg = "${msg}";
	if(msg == "delete_success"){
		alert("삭제되었습니다.");
	}
	$("#btnRegister").click(function(){
// 		location.href = "/board/register?page=${pagingDto.page}&perPage=${pagingDto.perPage}";
		$("#frmPage").attr("action", "/board/register");
		$("#frmPage").submit();
	});
	
	$("#selNline").change(function(){
		var val = $(this).val();
// 		console.log(val);
		location.href = "/board/listAll?perPage=" + val;
	});
	
	$(".page-link").click(function(e){
		e.preventDefault();
		var page = $(this).attr("data-page");
		$("input[name=page]").val(page);
		$("#frmPage").submit();
	});
	
	$(".board-title").click(function(e){
		e.preventDefault();
		var bno = $(this).attr("data-bno");
		$("input[name=bno]").val(bno);
		$("#frmPage").attr("action", "/board/read");
		$("#frmPage").submit();
	});
	
});
</script>

<div class="container-fluid">

	<form id="frmPage" action="/board/listAll" method="get">
		<input type="hidden" name="bno"/>
		<input type="hidden" name="page"
			value="${pagingDto.page}"/>
		<input type="hidden" name="perPage"
			value="${pagingDto.perPage}"/>
		<input type="hidden" name="searchType"
			value="${pagingDto.searchType}"/>
		<input type="hidden" name="keyword"
			value="${pagingDto.keyword}"/>	
	</form>
	
	<div class="row">
	<form role="form" class="form-inline" action="/board/listAll">
		<input type="hidden" name="perPage"	value="${pagingDto.perPage}"/>
		<button type="button" class="btn btn-primary" id="btnRegister">write</button>
		<!-- n줄씩 보기 -->
		<select class="form-control" id="selNline">
			<c:forEach begin="10" end="50" step="10" var="v">
				<option value="${v}"
				<c:if test="${pagingDto.perPage == v}">
					selected
				</c:if>
				>${v}줄씩 보기</option>
			</c:forEach>
		</select>
		
			<select class="form-control" name="searchType">
				<option value="title"
					<c:if test="${pagingDto.searchType == 'title'}">
						selected
					</c:if>
				>제목</option>
				<option value="content"
					<c:if test="${pagingDto.searchType == 'content'}">
						selected
					</c:if>
				>내용</option>
				<option value="title_content"
				<c:if test="${pagingDto.searchType == 'title_content'}">
						selected
					</c:if>
				>제목 + 내용</option>
				<option value="writer"
				 <c:if test="${pagingDto.searchType == 'writer'}">
						selected
					</c:if>
				>작성자</option>
			</select>
			<input type="text" class="form-control" name="keyword"
					placeholder="검색어를 입력하세요" value="${pagingDto.keyword}">
			<button type="submit" class="btn btn-warning form-control">검색</button>
		</form>
	</div>
</div>
	<!-- 테이블 게시판 -->
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<table class="table">
				<thead>
					<tr>
						<th>글번호</th>
						<th>글제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="boardVo">
					<tr>
						<td>${boardVo.bno}</td>
						<td><a data-bno="${boardVo.bno}" class="board-title">${boardVo.title}
											<span class="badge badge-secondary">${boardVo.reply_cnt}</span></a></td>
						<td>${boardVo.writer}</td>
						<td><fmt:formatDate value="${boardVo.regdate}"
										pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><span class="badge label-success">${boardVo.viewcnt}</span></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<!-- pagingnation -->
	<div class="row">
		<div class="col-md-12 text-center">
			<nav>
				<ul class="pagination">
					<c:if test="${pagingDto.hasPrev == true}">
						<li class="page-item">
							<a class="page-link" 
								data-page="${pagingDto.startPage - 1}">이전</a>
						</li>
					</c:if>
					<c:forEach begin="${pagingDto.startPage}" end="${pagingDto.endPage}" var="v">
					<li
					<c:choose>
						<c:when test="${pagingDto.page == v}">
							class="page-item active"
						</c:when>
						<c:otherwise>
							class="page-item"
						</c:otherwise>
					</c:choose>
					 >
						<a class="page-link" data-page="${v}">${v}</a>
					</li>
					</c:forEach>
					<c:if test="${pagingDto.hasNext == true}">
						<li class="page-item">
							<a class="page-link" 
								data-page="${pagingDto.endPage + 1}">다음</a>
						</li>
					</c:if>	
				</ul>
			</nav>
		</div>
	</div>

</div>
<%@ include file="../include/footer.jsp"%>