/**
 * checkEmpty(txtObject,txtPrompt)
 * @param txtObject 检查的文本对象
 * @return 真表示检查通过
 */
 function checkEmpty(txtObject) {
 	if (txtObject.value==""||txtObject.value==null) {
 		return false;
 	} else {
 		return true;
 	}
 }
 
 /**
  * checkMinLength
  * @param txtObject文本对象,minLength表示最小的值是多少
  * 
  */
  function checkMinLength(txtObject,minLength) {
  	if (txtObject.value.length < minLength) {
  		return false;
  	}
  	else {
  		return true;
  	}
  }
  
  //删除前的确认，参数是提示信息，返回真表示删除
  //调用方法： return confirmOperator('真的要删除此考生吗？删除后他的成绩也会全部丢失。');
function confirmOperator(promptMessage){
	var flag= confirm(promptMessage);
	//真表示要删除
	if (flag) {
		return true;
	} else {
		return false;
	}
}

//回车转到下一个文本框
//回车跳到下一个文本框，参数为要跳到的地方
function enterGoto(nextObject) {
	if (event.keyCode == 13) {
		nextObject.focus();
	}
}