package com.yc.duanxin;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class DuanxinJudge {

	public static Integer sendJudgeCode(String standby_1) {
		//官网的URL
		String url="http://gw.api.taobao.com/router/rest";
		//成为开发者，创建应用后系统自动生成
		String appkey="23718207";
		String secret="8e41d59b1dbd6f74b2910ab4b5c6bda9";
		//短信模板的内容
		String json="{\"code\":\"1234\"}";
		String code=(int)((Math.random()*9+1)*100000)+"";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		//通过的审核名
		req.setSmsFreeSignName("三剑客小说网 ");
		//你要传的参数
		req.setSmsParamString("{\"code\":\""+code+"\"}");
		//要发的电话号码
		req.setRecNum(standby_1);
		req.setSmsTemplateCode("SMS_57740003");
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Integer.parseInt(code);

	}

}
