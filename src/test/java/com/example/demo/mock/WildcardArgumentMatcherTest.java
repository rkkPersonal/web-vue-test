package com.example.demo.mock;


import com.example.demo.test.mockito.SimpleService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;

@RunWith(MockitoJUnitRunner.class)
public class WildcardArgumentMatcherTest {

    @Mock
    private SimpleService simpleService;

    @After
    public void before() {
        Mockito.reset(simpleService);
    }

    @Test
    public void wildcardMethod1() {
        Mockito.when(simpleService.method1(Mockito.anyInt(), Mockito.anyString(), Mockito.anyCollection(), Mockito.isA(Serializable.class))).thenReturn(100);

        int i = simpleService.method1(1, "Steven", CollectionUtils.EMPTY_COLLECTION, "Mockito");
        Assert.assertEquals(i, 100);

        int a = simpleService.method1(1, "James", CollectionUtils.EMPTY_COLLECTION, "Mockito");
        Assert.assertEquals(a, 100);

    }

    @Test
    public void wildcardWithSpec() {
        Mockito.when(simpleService.method1(Mockito.anyInt(), Mockito.eq("Steven"), Mockito.anyCollection(), Mockito.isA(Serializable.class))).thenReturn(100);
        Mockito.when(simpleService.method1(Mockito.anyInt(), Mockito.eq("James"), Mockito.anyCollection(), Mockito.isA(Serializable.class))).thenReturn(200);

        int i = simpleService.method1(1, "Steven", CollectionUtils.EMPTY_COLLECTION, "Mockito");
        Assert.assertEquals(i, 100);

        int a = simpleService.method1(1, "James", CollectionUtils.EMPTY_COLLECTION, "Mockito");
        Assert.assertEquals(a, 200);

        int f = simpleService.method1(1, "Peck", CollectionUtils.EMPTY_COLLECTION, "Mockito");
        Assert.assertEquals(f, 0);
    }


    /**
     * org.mockito.exceptions.misusing.InvalidUseOfMatchersException:
     * Invalid use of argument matchers!
     * 4 matchers expected, 3 recorded:
     * -> at com.example.demo.mock.WildcardArgumentMatcherTest.wildcardMethod2(WildcardArgumentMatcherTest.java:70)
     * -> at com.example.demo.mock.WildcardArgumentMatcherTest.wildcardMethod2(WildcardArgumentMatcherTest.java:70)
     * -> at com.example.demo.mock.WildcardArgumentMatcherTest.wildcardMethod2(WildcardArgumentMatcherTest.java:70)
     * <p>
     * This exception may occur if matchers are combined with raw values:
     * //incorrect:
     * someMethod(anyObject(), "raw String");
     * When using matchers, all arguments have to be provided by matchers.
     * For example:
     * //correct:
     * someMethod(anyObject(), eq("String by matcher"));
     * <p>
     * <p>
     * @incorrect:     Mockito.verify(simpleService,Mockito.times(1)).method2(Mockito.anyInt(),"Steven",Mockito.anyCollection(),Mockito.isA(Serializable.class));
     * @correct:   Mockito.verify(simpleService,Mockito.times(1)).method2(Mockito.anyInt(), Mockito.eq("Steven"),Mockito.anyCollection(),Mockito.isA(Serializable.class));
     */
    @Test
    public void wildcardMethod2() {

        Mockito.doNothing().when(simpleService).method2(Mockito.anyInt(), Mockito.anyString(), Mockito.anyCollection(), Mockito.isA(Serializable.class));

        simpleService.method2(1, "Steven", CollectionUtils.EMPTY_COLLECTION, "Mockito");

        Mockito.verify(simpleService, Mockito.times(1)).method2(1, "Steven", CollectionUtils.EMPTY_COLLECTION, "Mockito");

        //Mockito.verify(simpleService, Mockito.times(1)).method2(Mockito.anyInt(), "Steven", Mockito.anyCollection(), Mockito.isA(Serializable.class));
        Mockito.verify(simpleService, Mockito.times(1)).method2(Mockito.anyInt(), Mockito.eq("Steven"), Mockito.anyCollection(), Mockito.isA(Serializable.class));

    }
}
