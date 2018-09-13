package model;


/**
 * Created by Administrator on 2018/1/9.
 */

public class PersonInfoJson {

    /**
     * msg : success
     * data : {"name":"1saasfd234dfgfdfhf56","phone":"123456"}
     * status : 1
     */

    private String msg;
    private DataBean data;
    private int status;

    public PersonInfoJson(String msg, DataBean data, int status) {
        this.msg = msg;
        this.data = data;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * useid : 1saasfd234dfgfdfhf56
         */

        private String name;
        private String phone;

        public DataBean(String name,String phone) {
            this.name = name;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getPhone() {
        	return phone;
        }
        
        public void setPhone(String phone) {
        	this.phone = phone;
        }
    }
}
