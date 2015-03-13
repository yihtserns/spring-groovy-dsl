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

### Single bean with property values
```groovy
// applicationContext.spring
/**
 * <bean id="myBean" class="org.example.MyBean">
 *   <property name="prop1" value="Value 1"/>
 *   <property name="prop2" value="true"/>
 *   <property name="prop3" value="3"/>
 * </bean>
 */
import org.example.MyBean

myBean = MyBean.new() {
    prop1 = 'Value 1'
    prop2 = true
    prop3 = 3
}
```

### Single bean with constructor args and property values
```groovy
// applicationContext.spring
/**
 * <bean id="myBean" class="org.example.MyBean">
 *   <constructor-arg index="0" value="My Bean"/>
 *   <constructor-arg index="1" value="100"/>
 *   <property name="prop1" value="Value 1"/>
 *   <property name="prop2" value="true"/>
 *   <property name="prop3" value="3"/>
 * </bean>
 */
import org.example.MyBean

myBean = MyBean.new('My Bean', 100) {
    prop1 = 'Value 1'
    prop2 = true
    prop3 = 3
}
```