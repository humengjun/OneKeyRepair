package wit.hmj.onekeyrepair.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/30.
 */
public class Recorder implements Serializable {
    private int time;
    private String filePath;

    public Recorder() {
    }

    public Recorder(int time, String filePath) {
        this.time = time;
        this.filePath = filePath;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public float getTime() {
        return time;
    }

    public String getFilePath() {
        return filePath;
    }
}
