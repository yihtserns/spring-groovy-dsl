Spring Groovy DSL
=================
Groovy DSL to configure Spring beans.

Usage example
-------------
```java
import com.github.yihtserns.spring.groovy.GroovyBeanDefinitionReader;

...

ApplicationContext ctx = ... // e.g. new GenericApplicationContext();
GroovyBeanDefinitionReader groovyReader = new GroovyBeanDefinitionReader();

groovyReader.loadBeanDefinitions(new ClassPathResource("applicationContext.spring"));
ctx.refresh();
```

Sample
------
### Single no-arg, no-property bean
```groovy
// applicationContext.spring
/**
 * <bean id="myBean" class="org.example.MyBean"/>
 */
import org.example.MyBean

myBean = MyBean.new()
```

### Single bean with constructor args
```groovy
// applicationContext.spring
/**
 * <bean id="myBean" class="org.example.MyBean">
 *   <constructor-arg index="0" value="My Bean"/>
 *   <constructor-arg index=1" value="100"/>
 * </bean>
 */
import org.example.MyBean

myBean = MyBean.new('My Bean', 100)
```