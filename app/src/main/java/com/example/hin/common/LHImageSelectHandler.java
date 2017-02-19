/**
 *
 */
package com.example.hin.common;

import com.example.hin.common.select.ImageSelectHandler;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yanry
 *         <p>
 *         2016年5月3日
 */
public class LHImageSelectHandler extends ImageSelectHandler {

    private static volatile LHImageSelectHandler instance = null;

    private LHImageSelectHandler() {
    }

    public synchronized static LHImageSelectHandler get() {
        if (instance == null) {
            synchronized (LHImageSelectHandler.class) {
                if (instance == null) {
                    instance = new LHImageSelectHandler();
                }
            }
        }
        return instance;
    }

    @Override
    protected void execute(Runnable r) {
        Flowable.just(r).subscribeOn(Schedulers.io()).subscribe(new Consumer<Runnable>() {
            @Override
            public void accept(Runnable runnable) throws Exception {
                runnable.run();
            }
        });
    }

    @Override
    protected void onAlbumDataLoaded() {

    }

}
