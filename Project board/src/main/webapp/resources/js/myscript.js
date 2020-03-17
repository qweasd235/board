/**
 * 
 */

//타입스탬프(밀리세컨드) 값을 받아서 문자열 형태로 변환
function dateString(mSec){
	var d = new Date(mSec);
	var year = d.getFullYear(); //년
	var month = d.getMonth() + 1; //월
	var date = d.getDate(); //일
	var hour = d.getHours(); //시
	var minute = d.getMinutes(); //분
	var sec = d.getSeconds(); //초
	
	return year + "/" + 
	make2digits(month) + "/" + 
	make2digits(date) + " " + 
	make2digits(hour) + ":" + 
	make2digits(minute) + ":" + 
	make2digits(sec);
}

//자릿수 2자리로 맞추기
function make2digits(num){
	return (num < 10) ? "0" + num : num;
}

function checkImage(fileName){
	var dotIndex = fileName.lastIndexOf(".");
	var formatName = fileName.substring(dotIndex + 1).toUpperCase();
//	alert("formatName: " + formatName);
	var arrFormat = [ "JPG", "PNG", "GIF"];
	
	for (var v = 0; v < arrFormat.length; v++){
		if(formatName == arrFormat[v]){
			return true;
		}
	}
	
//	arrFormat.forEach(function(aFormat){
//		if(formatName == arrFormat[v]){
//			return true;
//		}	
//	});
	return false;
}


//썸네일 이미지의 이름 얻기	
function getThumbnailName(fullName){
	var slashIndex = fullName.lastIndexOf("/");
	var front = fullName.substring(0, slashIndex + 1); // 2020/1/20
	var rear = fullName.substring(slashIndex + 1); // d752e169-c1e1-4683-a810-6f881fc0a9bb_Tulips.jpg
	var thumbnailName = front + "s_" + rear;
	return thumbnailName;
}
