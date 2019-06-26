package com.github.arugal.listen.example.test;

import com.github.arugal.listen.example.Listen;
import com.github.arugal.listen.example.ListenManager;
import com.github.arugal.listen.example.SubListenManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 2019-06-26/10:55
 */
public class SubListenManagerTest {

    private List<Listen> listens = new LinkedList<>();

    public static class SubListenManagerTestListen implements Listen<SubListenManagerTest> {

        @Override
        public void notify(SubListenManagerTest subListenManagerTest) {
            System.out.println("In SubListenManagerTestListen:" + subListenManagerTest);
        }
    }

    public static class SubListen implements Listen<SubListenManager.SubContext> {

        @Override
        public void notify(SubListenManager.SubContext context) {
            context.setBit(true);
        }
    }

    @Before
    public void up() {
        listens.add(new SubListenManagerTestListen());
        listens.add(new SubListen());
    }

    @Test
    public void listenRefreshTest() {
        ListenManager<SubListenManager.SubContext> listenManager = new SubListenManager((listens, context) -> listens.forEach((listen) -> listen.notify(context)));
        // scope
        Assert.assertEquals(SubListenManager.SubContext.class, listenManager.listenScope());

        // refresh
        listenManager.refreshListen(listens);
        Assert.assertEquals(1, listenManager.getListens().size());

        // accept
        SubListenManager.SubContext context = new SubListenManager.SubContext();
        listenManager.accept(context);
        Assert.assertTrue(context.isBit());


    }
}
