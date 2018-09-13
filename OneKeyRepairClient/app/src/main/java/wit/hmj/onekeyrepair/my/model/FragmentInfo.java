package wit.hmj.onekeyrepair.my.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class FragmentInfo implements Serializable {
    //state=1 待审核 state=2 待维修 state=3 已完成
    private int state;
    private String thing;
    private String name;
    private String phone;
    private String address;
    private String time;
    private String workNumber;
    private String workerName;
    private String workerPhone;
    private List<String> imageList;
    private String score;
    private String evaluation;

    public FragmentInfo(int state, String thing, String name, String phone, String address, String time, String workNumber, String workerName, String workerPhone, List<String> imageList, String score, String evaluation) {
        this.state = state;
        this.thing = thing;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.time = time;
        this.workNumber = workNumber;
        this.workerName = workerName;
        this.workerPhone = workerPhone;
        this.imageList = imageList;
        this.score = score;
        this.evaluation = evaluation;
    }

    public String getScore() {
        return score;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public int getState() {
        return state;
    }

    public String getThing() {
        return thing;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }
}
