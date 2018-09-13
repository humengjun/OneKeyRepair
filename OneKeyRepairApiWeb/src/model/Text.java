package model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.FeedBackJson;
import model.GenerateJsonString;
import model.RegisterJson;
import model.LoginJson;
import model.RepairJson;
import model.RepairJson.DataBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







import utils.DBUtil;

public class Text {

	public static void main(String[] args) {
		
		try {
//			List<DataBean> dataBeans = new ArrayList<DataBean>();
//			List<String> imageList = new ArrayList<String>();
//			imageList.add("img1");
//			imageList.add("img2");
//			imageList.add("img3");
//			dataBeans.add(new DataBean("1", "2", "3", "4", "5", "6", "7", 1, "8", "9", imageList));
//			String jsonString = GenerateJsonString.getRepairJsonString(new RepairJson("success", 1, dataBeans));
//			System.out.println(jsonString);
			
//			List<FeedBackJson.DataBean> dataBeans = new ArrayList<FeedBackJson.DataBean>();
//			dataBeans.add(new FeedBackJson.DataBean("1","2","123"));
//			dataBeans.add(new FeedBackJson.DataBean("2","3","1234"));
//			dataBeans.add(new FeedBackJson.DataBean("3","4","12345"));
//			String jsonString = GenerateJsonString.getFeedBackJsonString(new FeedBackJson("success", 1, dataBeans));
//			System.out.println(jsonString);
			
//			String jsonString = GenerateJsonString.getRegister1JsonString(new Register1Json("success", 1, new Register1Json.DataBean("123456")));
//			System.out.println(jsonString);
			
//			String jsonString = GenerateJsonString.getRegister2JsonString(new Register2Json("success",  new Register2Json.DataBean("123456"),1));
//			System.out.println(jsonString);
			
			String jsonString = GenerateJsonString.getErrorJsonString("手机号已被注册");
			System.out.println(jsonString);
			
			
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}
}
