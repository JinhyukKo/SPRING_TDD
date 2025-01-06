package AOP;

import com.example.sayHello.Hello;
import com.example.sayHello.HelloTarget;
import com.example.sayHello.UppercaseHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    static final String name = "jin";
    static final String NAME = "JIN";
    static final String HELLO = "HELLO ";
    static final String HI =  "HI ";
    static final String THANKYOU = "THANK YOU ";



    @Test
    public void SimpleProxy(){
        Hello proxyiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
        assert proxyiedHello.sayHello(name).equals(HELLO+NAME);
        assert proxyiedHello.sayHi(name).equals(HI+NAME);
        assert proxyiedHello.sayThankYou(name).equals(THANKYOU+NAME);
    }
}
