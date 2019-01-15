var rightAnswers = "";
$(function() {
	// 从浏览器中取id（从localstorage或cookie中取）
	// var wpid= window.localStorage? localStorage.getItem("wpid"):
	// Cookie.read("wpid");
	// var waid= window.localStorage? localStorage.getItem("waid"):
	// Cookie.read("waid");
	result=getQueryString();
	
	
	var arr = result.toString().split(",");
	//change:zx
	if(result==''){
		var wpid = localStorage.getItem("wpid");
		var waid = localStorage.getItem("waid");
	}else{
		var wpid = arr[0].split("=")[1];
		
		var waid = arr[3].split("=")[1];
	}


	
	// 根据id查找试卷信息
	$.ajax({
		url : "/Examination2.0/dataarraylist_showAnswerPagesDetail.action",
		type : "post",
		datatype : "json",
		data : {
			"wpid" : wpid,
			"waid" : waid
		},
		success : showPageInfoDetail
	});

});

// 进入显示试卷页面的初始化数据
function showPageInfoDetail(data) {
	
	var pageInfoDetails =  data ;
	if (pageInfoDetails.responseCode == 0) {
		var paperId = pageInfoDetails.obj.writingPaper.id;
		var paperName = pageInfoDetails.obj.writingPaper.paperName;
		var examineeClass = pageInfoDetails.obj.writingPaper.examineeClass;
		var stuName = pageInfoDetails.obj.examineeName;
		var countOfQuestion = pageInfoDetails.obj.writingPaper.countOfQuestion;
		var timesConsume = pageInfoDetails.obj.writingPaper.timesConsume;
		rightAnswers = pageInfoDetails.obj.answer;//用户的答案
		$("#paperId").text(paperId);
		$("#paperName").text(paperName);
		$("#examineeClass").text(examineeClass);
		$("#stuName").text(stuName);
		$("#countOfQuestion").text(countOfQuestion);
		$("#timesConsume").text(timesConsume);
		questionId = pageInfoDetails.obj.writingPaper.questionId;
		// alert(questionId.charAt(questionId.length-1));
		if (questionId.charAt(questionId.length - 1) == ",") {
			questionId = questionId.substring(0, questionId.length - 1)
		} else {
			questionId = questionId;
		}
		questionId = "[" + questionId + "]";
		// alert("questionId:"+questionId);
		// 根据ids查询考试题
		$.ajax({
			url : "/Examination2.0/dataarraylist_findQuestionByids.action",
			type : "post",
			datatype : "json",
			data : {
				"questionId" : questionId
			},
			success : showAllQuestion
		});
	}
	// 显示拼接好的试题信息
	function showAllQuestion(data) {
		var questionCount = 0;
		var questionStr = "";
		var allQuestion =  data ;
		$.each(
						allQuestion.obj,
						function(i, question) {
							// alert("question:"+question.question)
							questionStr += '<input type="hidden" name="answer_'
									+ (i + 1) + '" id="answer_' + (i + 1)
									+ '" value="' + question.answer + '">';
							questionStr += '<table width="100%" border="0" cellspacing="0" cellpadding="0" id="questionTable'
									+ (i + 1)
									+ '"> <tr><td width="8%"><p align="center">&nbsp;</p></td><td height="27" colspan="2">&nbsp;</td></tr>';
							questionStr += '<tr><td height="4%" valign="top">&nbsp;<b>'
									+ (i + 1)
									+ '.</b></td><td height="10%" colspan="2" rowspan="2"><a name="Food_'
									+ (i + 1)


									+ '"> </a><span class="style14"> </span><div class="textareaTest" name="text'+(i+1)+'" contenteditable="false">'+question.question+'</div>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;</td></tr>';
							questionStr += '<tr><td height="100%">&nbsp;</td></tr>';
							if (question.image != null) {
								questionStr += '<tr><td></td><td colspan="2">&nbsp;<span class="fontColor"> 图片说明:</span><br><div align="left"><img src="" alt=""><br /></div></td></tr>';
							}

							questionStr += '<tr><td rowspan="4">&nbsp;</td><td width="3%" valign="top">A.</td><td width="89%"><div class="textareaTest" name="textA_'+(i+1)+'" contenteditable="false">'+question.optionA+'</div></td></tr>';
							questionStr += '<tr><td width="3%" valign="top">B.</td><td ><div class="textareaTest" name="textB_'+(i+1)+'" contenteditable="false">'+question.optionB+'</div></td></tr>';
							questionStr += '<tr><td width="3%" valign="top">C.</td><td ><div class="textareaTest" name="textC_'+(i+1)+'" contenteditable="false">'+question.optionC+'</div></td></tr>';
							questionStr += '<tr><td width="3%" valign="top">D.</td><td ><div class="textareaTest" name="textD_'+(i+1)+'" contenteditable="false">'+question.optionD+'</div></td></tr>';

							questionStr += '<tr><td height="39"></td><td height="39" colspan="2"><span class="fontColor">&nbsp;&nbsp; 正确答案：</span><span id="rightAnswer_'
									+ (i + 1)
									+ '">'
									+ question.answer
									+ '</span><br><span>&nbsp;&nbsp;&nbsp;你的答案：</span><span id="myAnswer_'
									+ (i + 1)
									+ '"></span> &nbsp;&nbsp;&nbsp;<a href="#top"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; TOP</a></td></tr>';
							questionStr += '</table>';
							questionCount++;
						});
		$("#questionInfoTab").html(questionStr);
		// 给试卷加考生的做题答案
		 //alert(rightAnswers);

		//var rightAnswersArr= new Array(questionCount);//规定了数组的长度为questionCount  ;
		//var myanswer=rightAnswers.split(';');//将考生的答案分割出来
		var rightAnswersArr=rightAnswers.split(';');//将考生的答案分割出来
		//console.info(myanswer.length);
		/*for(var i=0;i<myanswer.length-1;i++){
			rightAnswersArr[i]=myanswer[i];
		}
	for(var k=myanswer.length-1;k<questionCount;k++){
			rightAnswersArr[k]=(k+1)+",无答案";
		}*/


		for (var j = 1; j <= questionCount; j++) {
			
			//console.info(rightAnswersArr);
			if(rightAnswersArr[j - 1]==undefined){
				continue;
			}
			var rightAnswer = $.trim($("#rightAnswer_" + rightAnswersArr[j - 1].split(',')[0]).text());//正确答案
			if (rightAnswer == rightAnswersArr[j - 1].split(',')[1]) {
				$("#myAnswer_" +rightAnswersArr[j - 1].split(',')[0]).text(rightAnswer);
			} else {
				$("#myAnswer_" + rightAnswersArr[j - 1].split(',')[0]).text(rightAnswersArr[j - 1].split(',')[1]);
				$("#myAnswer_" + rightAnswersArr[j - 1].split(',')[0]).css("color", "red");
			}
		}
		// 把试卷信息表中的题的数量更新
		$("#questionAmount").text(questionCount);
		// 显示侧栏第几题
		showtitleInfoStr(questionCount, rightAnswers);

	}
	// 显示侧栏第几题
	function showtitleInfoStr(countOfQuestion, rightAnswers) {
		var titleInfoStr = "";
		var rightAnswersArr = rightAnswers.split(';');
		 
		for (var i = 1; i <= countOfQuestion; i++) {
			//if(rightAnswersArr[i- 1]==undefined){
			
			titleInfoStr += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#Food_' + i
					+ '"> 第 ' +  i+ ' 题 </a><span id="span' +i + '">无答案</span><br>';
			//continue;
			//}else{
				//titleInfoStr += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#Food_' + i
				//+ '"> 第 ' +  rightAnswersArr[i - 1].split(',')[0] + ' 题 </a><span id="span' + rightAnswersArr[i - 1].split(',')[0] + '"> '
				//+ rightAnswersArr[i - 1].split(',')[1] + '</span><br>';
			//}
		}
		$("#divQuestionCounts").html(titleInfoStr);
		for(var j=1;j<rightAnswersArr.length;j++){//填写答案
			var id =parseInt(rightAnswersArr[j - 1].split(',')[0]);
			$("#span"+id).text(rightAnswersArr[j - 1].split(',')[1]);
		}
	}

}


function getQueryString() {

	var result = location.search.match(new RegExp("[\?\&][^\?\&]+=[^\?\&]+",
	"g"));

	if (result == null) {

		return "";

	}

	for (var i = 0; i < result.length; i++) {

		result[i] = result[i].substring(1);

	}

	return result;

}
