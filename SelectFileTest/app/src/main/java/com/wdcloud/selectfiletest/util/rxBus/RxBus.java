package com.wdcloud.selectfiletest.util.rxBus;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.SerializedSubscriber;

/**
 * 1、Subject同时充当了Observer和Observable的角色，
 * Subject是非线程安全的，要避免该问题，
 * 需要将 Subject转换为一个 SerializedSubject，
 * 上述RxBus类中把线程非安全的PublishSubject包装成线程安全的Subject。
 * 2、PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者。
 * 3、ofType操作符只发射指定类型的数据，其内部就是filter+cast
 * Created by Umbrella on 2018/5/11.
 */

public class RxBus {

    //相当于Rxjava1.x中的Subject
    private final FlowableProcessor<Object> mBus;
    private static volatile RxBus sRxBus = null;

    private RxBus() {
        //调用toSerialized()方法，保证线程安全
        mBus = PublishProcessor.create().toSerialized();
    }

    public static synchronized RxBus getDefault() {
        if (sRxBus == null) {
            synchronized (RxBus.class) {
                if (sRxBus == null) {
                    sRxBus = new RxBus();
                }
            }
        }
        return sRxBus;
    }


    /**
     * 发送消息
     *
     * @param o
     */
    public void post(Object o) {
        new SerializedSubscriber<>(mBus).onNext(o);
    }

    /**
     * 确定接收消息的类型
     *
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> aClass) {
        return mBus.ofType(aClass);
    }

    /**
     * 判断是否有订阅者
     *
     * @return
     */
    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

}
