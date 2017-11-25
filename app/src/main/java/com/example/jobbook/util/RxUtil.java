package com.example.jobbook.util;

import com.example.jobbook.model.exception.ApiException;
import com.example.jobbook.model.http.api.bean.ResultBean;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Xu on 2017/11/25.
 */

public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ResultBean<T>, T> handleResult() {   //compose判断结果
        return new FlowableTransformer<ResultBean<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<ResultBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<ResultBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(ResultBean<T> resultBean) {
                        if(resultBean.getCode() == 0) {
                            return createData(resultBean.getData());
                        } else {
                            return Flowable.error(new ApiException(resultBean.getCode()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
