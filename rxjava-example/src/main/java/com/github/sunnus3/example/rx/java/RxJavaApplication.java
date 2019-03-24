package com.github.sunnus3.example.rx.java;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author: zhangwei
 * @date: 11:52/2019-03-06
 */
@SpringBootApplication
public class RxJavaApplication {

    private static final Logger logger = LoggerFactory.getLogger(RxJavaApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(RxJavaApplication.class, args);
    }

    @Bean
    public ApplicationRunner observableRunner() {
        return (args) -> {
            for (int i = 0; i < 3; i++) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                        printThread("subscribe");
                        logger.info("Observable emit 1");
                        observableEmitter.onNext(1);
                        logger.info("Observable emit 2");
                        observableEmitter.onNext(2);
                        logger.info("Observable emit 3");
                        observableEmitter.onNext(3);
                        observableEmitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                        .subscribe(new Observer<Integer>() {

                            private int i;
                            private Disposable disposable;

                            @Override
                            public void onSubscribe(Disposable disposable) {
                                this.disposable = disposable;
                            }

                            @Override
                            public void onNext(Integer integer) {
                                printThread("onNext");
                                logger.info("onNext {}", integer);
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                logger.error(throwable.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                logger.info("onComplete");
                            }
                        });
            }
        };
    }

    @Bean
    public ApplicationRunner mapRunner(){
        return (args) -> {
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                    emitter.onNext(10);
                    emitter.onComplete();
                }
            }).map(new Function<Integer, String>() {
                @Override
                public String apply(Integer integer) throws Exception {
                    return integer.toString();
                }
            }).doOnNext((String a) -> {
                logger.info("doOnNext:{}", a);
            }).subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        };
    }

    @Bean
    public ApplicationRunner concatRunner(){
        return (args) -> {

        };
    }


    private static void printThread(String method) {
        logger.info("{}:{}", method, Thread.currentThread().getName());
    }
}
