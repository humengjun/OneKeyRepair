package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GenerateJsonString {
	/**
	 * 得到RepairJson字符串，用于返回客户端
	 * 
	 * @throws JSONException
	 * 
	 * */
	public static String getRepairJsonString(RepairJson repairJson)
			throws JSONException {
		JSONObject json_main = new JSONObject();
		JSONArray json_data = new JSONArray();

		json_main.put("status", 1);
		json_main.put("msg", "success");
		List<RepairJson.DataBean> dataBeans = repairJson.getData();
		for (RepairJson.DataBean dataBean : dataBeans) {
			JSONObject json_dataBean = new JSONObject();
			JSONArray json_image = new JSONArray();
			json_dataBean.put("name", dataBean.getName());
			json_dataBean.put("phone", dataBean.getPhone());
			json_dataBean.put("thing", dataBean.getThing());
			json_dataBean.put("time", dataBean.getTime());
			json_dataBean.put("city", dataBean.getCity());
			json_dataBean.put("address", dataBean.getAddress());
			json_dataBean.put("state", dataBean.getState());
			json_dataBean.put("workNumber", dataBean.getWorkNumber());
			json_dataBean.put("workerName", dataBean.getWorkerName());
			json_dataBean.put("workerPhone", dataBean.getWorkerPhone());
			for (int i = 0; i < dataBean.getImageList().size(); i++) {
				json_image.put(dataBean.getImageList().get(i));
			}
			json_dataBean.put("imageList", json_image);
			json_dataBean.put("score", dataBean.getScore());
			json_dataBean.put("evaluation", dataBean.getEvaluation());
			json_data.put(json_dataBean);
		}
		json_main.put("data", json_data);
		return json_main.toString();
	}

	/**
	 * 得到FeedBackJson字符串，用于返回客户端
	 * 
	 * @throws JSONException
	 * 
	 * */
	public static String getFeedBackJsonString(FeedBackJson feedBackJson)
			throws JSONException {
		JSONObject json_main = new JSONObject();
		JSONArray json_data = new JSONArray();

		json_main.put("status", 1);
		json_main.put("msg", "success");
		List<FeedBackJson.DataBean> feedBackJsons = feedBackJson.getData();
		for (FeedBackJson.DataBean dataBean : feedBackJsons) {
			JSONObject json_dataBean = new JSONObject();
			json_dataBean.put("name", dataBean.getName());
			json_dataBean.put("time", dataBean.getTime());
			json_dataBean.put("content", dataBean.getContent());
			json_data.put(json_dataBean);
		}
		json_main.put("data", json_data);
		return json_main.toString();
	}

	/**
	 * 得到RegisterJson字符串，用于返回客户端
	 * 
	 * @throws JSONException
	 * 
	 * */
	public static String getRegisterJsonString(RegisterJson registerJson)
			throws JSONException {
		JSONObject json_main = new JSONObject();
		JSONObject json_data = new JSONObject();

		json_main.put("status", 1);
		json_main.put("msg", "success");
		json_data.put("receive", registerJson.getData().getReceive());

		json_main.put("data", json_data);
		return json_main.toString();
	}

	/**
	 * 得到PersonInfoJson字符串，用于返回客户端
	 * 
	 * @throws JSONException
	 * 
	 * */
	public static String getPersonInfoJsonString(PersonInfoJson personInfoJson)
			throws JSONException {
		JSONObject json_main = new JSONObject();
		JSONObject json_data = new JSONObject();

		json_main.put("status", 1);
		json_main.put("msg", "success");
		json_data.put("name", personInfoJson.getData().getName());
		json_data.put("phone", personInfoJson.getData().getPhone());

		json_main.put("data", json_data);
		return json_main.toString();
	}

	/**
	 * 得到LoginJson字符串，用于返回客户端
	 * 
	 * @throws JSONException
	 * 
	 * */
	public static String getLoginJsonString(LoginJson loginJson)
			throws JSONException {
		JSONObject json_main = new JSONObject();
		JSONObject json_data = new JSONObject();

		json_main.put("status", 1);
		json_main.put("msg", "success");
		json_data.put("useid", loginJson.getData().getUseid());

		json_main.put("data", json_data);
		return json_main.toString();
	}

	/**
	 * 得到发生错误时json字符串，用于返回客户端
	 * 
	 * @throws JSONException
	 * 
	 * */
	public static String getErrorJsonString(String msg) throws JSONException {
		JSONObject json_main = new JSONObject();
		json_main.put("status", 0);
		json_main.put("msg", msg);
		json_main.put("data", JSONObject.NULL);
		return json_main.toString();
	}

	/**
	 * 得到请求成功json字符串，用于返回客户端
	 * 
	 * @throws JSONException
	 * 
	 * */
	public static String getSuccessJsonString() throws JSONException {
		JSONObject json_main = new JSONObject();
		json_main.put("status", 1);
		json_main.put("msg", "success");
		json_main.put("data", JSONObject.NULL);
		return json_main.toString();
	}
}
