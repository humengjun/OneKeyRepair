package model;


/**
 * Created by Administrator on 2018/1/9.
 */

public class LoginJson {

    /**
     * msg : success
     * data : {"useid":"1saasfd234dfgfdfhf56"}
     * status : 1
     */

    private String msg;
    private DataBean data;
    private int status;

    public LoginJson(String msg, DataBean data, int status) {
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

        private String useid;

        public DataBean(String useid) {
            this.useid = useid;
        }

        public String getUseid() {
            return useid;
        }

        public void setUseid(String useid) {
            this.useid = useid;
        }
    }
}
