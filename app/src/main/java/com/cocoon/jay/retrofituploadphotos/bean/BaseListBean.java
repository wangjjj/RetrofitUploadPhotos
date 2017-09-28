package com.cocoon.jay.retrofituploadphotos.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class BaseListBean extends BaseBean {


    private List<String> papers;

    public List<String> getPapers() {
        return papers;
    }

    public void setPapers(List<String> papers) {
        this.papers = papers;
    }



    private List<String> data;

    public List<String> getData() {
        return data;
    }
    public void setData(List<String> data) {
        this.data = data;
    }


    private List<String> imgs;

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }




}
