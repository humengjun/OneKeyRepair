package model;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */

public class RepairJson {

	/**
	 * msg : success data :
	 * [{"address":"123","workNumber":"123","workerPhone":"123"
	 * ,"phone":"123","city"
	 * :"123","name":"123","time":"199527","state":2,"workerName"
	 * :"123","thing":"123","imageList":["img1","img2","img3"]}] status : 1
	 */

	private String msg;
	private int status;
	private List<DataBean> data;

	public RepairJson(String msg, int status, List<DataBean> data) {
		this.msg = msg;
		this.status = status;
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}

	public static class DataBean {
		/**
		 * address : 123 workNumber : 123 workerPhone : 123 phone : 123 city :
		 * 123 name : 123 time : 199527 state : 2 workerName : 123 thing : 123
		 * imageList : ["img1","img2","img3"]
		 */

		private String address;
		private String workNumber;
		private String workerPhone;
		private String phone;
		private String city;
		private String name;
		private String time;
		private int state;
		private String workerName;
		private String thing;
		private List<String> imageList;
		private String score;
		private String evaluation;

		public DataBean(String address, String workNumber, String workerPhone,
				String phone, String city, String name, String time, int state,
				String workerName, String thing, List<String> imageList,
				String score, String evaluation) {
			this.address = address;
			this.workNumber = workNumber;
			this.workerPhone = workerPhone;
			this.phone = phone;
			this.city = city;
			this.name = name;
			this.time = time;
			this.state = state;
			this.workerName = workerName;
			this.thing = thing;
			this.imageList = imageList;
			this.score = score;
			this.evaluation = evaluation;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getEvaluation() {
			return evaluation;
		}

		public void setEvaluation(String evaluation) {
			this.evaluation = evaluation;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getWorkNumber() {
			return workNumber;
		}

		public void setWorkNumber(String workNumber) {
			this.workNumber = workNumber;
		}

		public String getWorkerPhone() {
			return workerPhone;
		}

		public void setWorkerPhone(String workerPhone) {
			this.workerPhone = workerPhone;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public String getWorkerName() {
			return workerName;
		}

		public void setWorkerName(String workerName) {
			this.workerName = workerName;
		}

		public String getThing() {
			return thing;
		}

		public void setThing(String thing) {
			this.thing = thing;
		}

		public List<String> getImageList() {
			return imageList;
		}

		public void setImageList(List<String> imageList) {
			this.imageList = imageList;
		}
	}
}
