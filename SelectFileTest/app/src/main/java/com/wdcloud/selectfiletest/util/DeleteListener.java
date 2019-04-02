package com.wdcloud.selectfiletest.util;

/**发消息得包装类（）图片删除
 * Created by Umbrella on 2018/7/24.
 */

public class DeleteListener {

    private int postion;

    public DeleteListener(int postion) {
        this.postion = postion;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }
}
