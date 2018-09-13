package wit.hmj.onekeyrepair.model;


/**
 * Created by Administrator on 2018/1/9.
 */

public class RegisterJson {

    /**
     * msg : success
     * status : 1
     * data : {"receive":"123"}
     */

    private String msg;
    private int status;
    private DataBean data;

    public RegisterJson(String msg, int status, DataBean data) {
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * receive : 123
         */

        private String receive;

        public DataBean(String receive) {
            this.receive = receive;
        }

        public String getReceive() {
            return receive;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }
    }
}
