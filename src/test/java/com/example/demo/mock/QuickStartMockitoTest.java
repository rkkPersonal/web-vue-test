package com.example.demo.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class QuickStartMockitoTest {

    @Mock
    public List<String> list;

    @BeforeEach
    public void beforeInit(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testJ(){

        Mockito.doNothing().when(list).clear();
        list.clear();
        Mockito.verify(list,Mockito.times(1)).clear();
    }

    @Test
    public void testB(){
        Mockito.when(list.size()).thenReturn(1,2,3,4);
    }
}
