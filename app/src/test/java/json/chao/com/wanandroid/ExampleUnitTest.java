package json.chao.com.wanandroid;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(PowerMockRunner.class)
public class ExampleUnitTest {

//    @Rule
//    public MyRuler mMyRuler = new MyRuler();

    @Test()
    @PrepareForTest({Banana.class})
    public void addition_isCorrect() throws Exception {
        PowerMockito.mockStatic(Banana.class);
        when(Banana.getColor()).thenReturn("紫色");
        Assert.assertEquals("紫色", Banana.getColor());

//        Person person = mock(Person.class);
//        when(person.getAge()).thenReturn(18);
//        System.out.println(person.getAge());
//        verify(person, after(1000)).getAge();
//
//        System.out.println(person.getAge());
//
//        verify(person, atLeast(2)).getAge();
//
//        System.out.println(person.getAge());
//
//        verify(person, Mockito.atMost(5)).getAge();
//
//        System.out.println(person.getAge());
//
//        when(person.setAge(contains("1"))).thenReturn(10);
//
//        System.out.println(person.setAge("01"));
//
//        when(person.setAge(anyString())).thenReturn(10);
//
//        System.out.println(person.setAge(""));
//
//        when(person.setAge(any(String.class))).thenReturn(100);
//
//        System.out.println(person.setAge("fdsfsd"));
//
//        when(person.setAge(argThat(argument -> argument.contains("20")))).thenReturn(100);
    }

}