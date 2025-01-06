package AOP;

import com.example.sayHello.Hello;
import com.example.sayHello.HelloTarget;
import com.example.sayHello.UppercaseAdvice;
import com.example.sayHello.UppercaseHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    static final String name = "jin";
    static final String NAME = "JIN";
    static final String HELLO = "HELLO ";
    static final String HI =  "HI ";
    static final String THANKYOU = "THANK YOU ";


    Hello proxiedHello;
    ProxyFactoryBean proxyFactoryBean;

    @Test
    public void SimpleProxy(){
        proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] {Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
        isProxyWorking();
    }

    @Test
    public void proxyFactoryBean(){
        proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());
        proxiedHello = (Hello) proxyFactoryBean.getObject();
        isProxyWorking();
    }


    @Test
    public void pointCut(){
        proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut,new UppercaseAdvice()));
        proxiedHello = (Hello) proxyFactoryBean.getObject();
        assert proxiedHello.sayHello(name).equals(HELLO+NAME);
        assert proxiedHello.sayHi(name).equals(HI+NAME);
        assert proxiedHello.sayThankYou(name).equals("thank you "+name);
    }

    public void isProxyWorking(){
        assert proxiedHello.sayHello(name).equals(HELLO+NAME);
        assert proxiedHello.sayHi(name).equals(HI+NAME);
        assert proxiedHello.sayThankYou(name).equals(THANKYOU+NAME);
    }
}
