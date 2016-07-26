package uncle.egg.newsapp.module;

/**
 * Created by egguncle on 16.7.21.
 */
public class  News {
    //标题
    private String desc;
    //上传时间
    private String publishedAt;
    //类型
    private String type;
    //链接
    private String url;
    //作者姓名
    private String who;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publicshedAt) {
        this.publishedAt = publicshedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
