package json.chao.com.wanandroid.testStudyExample.JunitAndMockitoTestExample;

import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import json.chao.com.wanandroid.rule.MyRuler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JunitAndMockitoTest {

    @Spy
    Person mPerson;

    @Spy
    Person mPerson1;

    @InjectMocks
    Home mHome;

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Rule
    public MyRuler mMyRuler = new MyRuler();

    @Before
    public void setUp() {

    }

    @Test()
    public void WhenAndThenReturnTest() {
        when(mPerson.getAge()).thenReturn(18);

        System.out.println(mPerson.getAge());
    }

    @Test()
    public void verifyTest() {
        when(mPerson.getAge()).thenReturn(25);

        System.out.println(mPerson.getAge());

        verify(mPerson, after(1000)).getAge();

        System.out.println(mPerson.getAge());

        verify(mPerson, atLeast(2)).getAge();

        System.out.println(mPerson.getAge());

        verify(mPerson, atMost(5)).getAge();

        System.out.println(mPerson.getAge());
    }

    @Test()
    public void matcherTest() {
        when(mPerson.setAge(contains("1"))).thenReturn(10);

        System.out.println(mPerson.setAge("01"));

        when(mPerson.setAge(anyString())).thenReturn(10);

        System.out.println(mPerson.setAge(""));

        when(mPerson.setAge(any(String.class))).thenReturn(100);

        System.out.println(mPerson.setAge("fdsfsd"));

        when(mPerson.setAge(argThat(argument -> argument.contains("20")))).thenReturn(100);

        TextView textView = mock(TextView.class);

        textView.setText("11");

        textView.setText("22");

        verify(textView, times(2)).setText(anyString());

        //Void返回值方法, 只有Void返回值方法可以用doNothing()
        doNothing().doThrow(new RuntimeException()).when(textView).setText("11");

        //非Void返回值方法
        when(textView.getText()).thenThrow(new RuntimeException());
    }

    @Test
    public void spyTest() {
        Person person = spy(Person.class);

        when(person.getAge()).thenReturn(20);

        System.out.println(person.getAge());

        Assert.assertEquals(person.getAge(), 20);
    }

    @Test
    public void inOrderTest() {
        mPerson.setAge("25");
        mPerson.setSex("男");

        mPerson1.setAge("26");
        mPerson1.setSex("男");

        InOrder inOrder = inOrder(mPerson, mPerson1);

        inOrder.verify(mPerson).setAge("25");
        inOrder.verify(mPerson).setSex("男");

        inOrder.verify(mPerson1).setAge("26");
        inOrder.verify(mPerson1).setSex("男");
    }

    @Test
    public void injectMockitoTest() {
        when(mPerson1.getAge()).thenReturn(20);

//        when(mPerson.getAge()).thenReturn(20);

        Assert.assertEquals(mHome.getAge(), 20);

        System.out.println(mHome.getAge());

    }

    @Test
    public void captorTest() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        mPerson1.setAge("20");

        verify(mPerson1).setAge(captor.capture());

        Assert.assertEquals(captor.getValue(), "20");

        System.out.println(captor.getAllValues());

    }

    @Test
    public void continuousInvocation() {
        when(mPerson1.getAge())
                .thenReturn(1, 2, 3);
//                .thenThrow(new NullPointerException());

        System.out.println(mPerson1.getAge());

        System.out.println(mPerson1.getAge());

        System.out.println(mPerson1.getAge());

        System.out.println(mPerson1.getAge());
    }


}