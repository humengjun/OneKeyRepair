package model;


import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */

public class FeedBackJson {

    /**
     * msg : success
     * data : [{"name":"6","time":"7","content":"12345"}]
     * status : 1
     */

    private String msg;
    private int status;
    private List<DataBean> data;

    public FeedBackJson(String msg, int status, List<DataBean> data) {
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
         * name : 6
         * time : 7
         * content : 12345
         */

        private String name;
        private String time;
        private String content;

        public DataBean(String name, String time, String content) {
            this.name = name;
            this.time = time;
            this.content = content;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
