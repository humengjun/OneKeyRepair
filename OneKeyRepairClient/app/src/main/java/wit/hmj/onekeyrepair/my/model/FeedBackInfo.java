package wit.hmj.onekeyrepair.my.model;

/**
 * Created by Administrator on 2018/1/4.
 */

public class FeedBackInfo {

    private String name;
    private String content;
    private String time;
    private String imagePath;

    public FeedBackInfo(String name, String content, String time, String imagePath) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
