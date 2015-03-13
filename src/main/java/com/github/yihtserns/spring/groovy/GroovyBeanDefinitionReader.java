/*
 * Copyright 2015 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yihtserns.spring.groovy;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.runtime.GroovyCategorySupport;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;

/**
 * Loads Spring bean definition from Groovy script.
 *
 * <h3>Usage example</h3>
 * <pre>
 * ApplicationContext ctx = new GenericApplicationContext();
 * GroovyBeanDefinitionReader groovyReader = new GroovyBeanDefinitionReader();
 * groovyReader.loadBeanDefinitions(new ClassPathResource("applicationContext.spring"));
 * ctx.refresh();
 *
 * MyBean myBean = (MyBean) ctx.getBean("myBean");
 * </pre>
 *
 * @author yihtserns
 */
public class GroovyBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public GroovyBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public int loadBeanDefinitions(final Resource resource) throws BeanDefinitionStoreException {
        final Map<String, Object> vars = new HashMap<String, Object>();

        try {
            final Script script = new GroovyShell().parse(new InputStreamReader(resource.getInputStream()));
            script.setBinding(new Binding(vars));

            GroovyCategorySupport.use(SpringCategory.class, new Closure(null) {

                public void doCall() throws IOException {
                    script.run();
                }
            });

            BeanDefinitionRegistry registry = getRegistry();
            int beanCount = 0;
            for (Map.Entry<String, Object> entry : vars.entrySet()) {
                String varName = entry.getKey();
                Object varValue = entry.getValue();

                if (varValue instanceof BeanDefinition) {
                    beanCount++;
                    registry.registerBeanDefinition(varName, (BeanDefinition) varValue);
                }
            }

            return beanCount;
        } catch (CompilationFailedException ex) {
            throw new BeanDefinitionStoreException("Unable to load Groovy script", ex);
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("Unable to load resource", ex);
        }
    }
}

