package com.yugugugu.view.face;


import com.yugugugu.view.UIObject;
import com.yugugugu.view.chat.ChatInit;
import com.yugugugu.view.chat.IChatEvent;
import com.yugugugu.view.chat.IChatMethod;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 小傅哥 on @2019
 */
public class FaceController extends FaceInit implements IFaceMethod {

    private FaceView faceView;

    public FaceController(UIObject obj, ChatInit chatInit, IChatEvent chatEvent, IChatMethod chatMethod) {
        super(obj);
        this.chatInit = chatInit;
        this.chatEvent = chatEvent;
        this.chatMethod = chatMethod;
    }

    @Override
    public void initView() {
        faceView = new FaceView(this);
    }

    @Override
    public void initEventDefine() {
        new FaceEventDefine(this);
    }

    @Override
    public void doShowFace(Double x, Double y) {
        setX(x + 230 * (1 - 0.618));  // 设置位置X
        setY(y - 160);                      // 设置位置Y
        show();                             // 展示窗口
    }

}
