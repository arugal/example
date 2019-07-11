package com.github.arugal.powermock.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author: zhangwei
 * @date: 2019-07-11/09:52
 * <p>
 * https://github.com/powermock/powermock
 * https://www.ezlippi.com/blog/2017/08/powermock-introduction.html
 * https://www.cnblogs.com/yueshutong/p/10213080.html
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ExampleTest.ExampleMocking.class)
public class ExampleTest {

    @Test
    public void demoPrivateMethodMockingOfSpy() {
        final String expected = "TEST VALUE";

        // return real value of spy
        ExampleMocking underTest = PowerMockito.spy(new ExampleMocking());

        PowerMockito.doReturn(expected).when(underTest).methodToTest();

        String toTest = underTest.methodToTest();

        Assert.assertEquals(expected, toTest);
    }

    @Test
    public void demoPrivateMethodMockingOfMock() {
        final String expected = "TEST VALUE";

        // return null of mock
        ExampleMocking underTest = PowerMockito.mock(ExampleMocking.class);

        PowerMockito.doReturn(expected).when(underTest).methodToTest();

        String toTest = underTest.methodToTest();

        Assert.assertEquals(expected, toTest);
    }

    @Test
    public void verify() {
        ExampleMocking underTest = PowerMockito.mock(ExampleMocking.class);

        PowerMockito.doNothing().when(underTest).noting();

        underTest.noting();
        
        Mockito.verify(underTest, Mockito.times(1)).noting();
    }


    public static class ExampleMocking {

        public void noting() {
            System.out.println("noting");
        }

        public String methodToTest() {
            System.out.println("public Method");
            return methodToMock("input");
        }

        private String methodToMock(String input) {
            System.out.println("private Method");
            return "REAL VALUE = " + input;
        }
    }
}
