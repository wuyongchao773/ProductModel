
//获得cookie公用方法
function getCookie(){
	var cookies = document.cookie;
	var start = cookies.indexOf("=");
	var newCookie = cookies.substr(start+1,cookies.length);
	return newCookie;
}