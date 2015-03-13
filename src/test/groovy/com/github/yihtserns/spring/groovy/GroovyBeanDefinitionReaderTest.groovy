/*
 * Copyright 2015 yihtserns.
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
package com.github.yihtserns.spring.groovy

import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.io.ByteArrayResource

import org.junit.After
import org.junit.Test

/**
 * @author yihtserns
 */
class GroovyBeanDefinitionReaderTest {

    def appContexts = []

    @After
    void closeAppContexts() {
        appContexts*.close()
    }

    @Test
    void 'can create bean without parameter'() {
        String script = """
            import com.github.yihtserns.spring.groovy.TestBean

            bean = TestBean.new()
        """

        def appContext = new GenericApplicationContext()
        appContexts << appContext
        
        def reader = new GroovyBeanDefinitionReader(appContext)
        reader.loadBeanDefinitions(new ByteArrayResource(script.bytes))

        appContext.refresh()
        assert appContext.getBean('bean') instanceof TestBean
    }

    @Test
    public void 'can create bean with constructor parameters'() {
        String script = """
            import com.github.yihtserns.spring.groovy.TestBean

            bean = TestBean.new('My Bean', 3)
        """

        def appContext = new GenericApplicationContext();
        appContexts << appContext

        def reader = new GroovyBeanDefinitionReader(appContext)
        reader.loadBeanDefinitions(new ByteArrayResource(script.bytes))

        appContext.refresh()
        def bean = appContext.getBean('bean')

        assert bean.name == 'My Bean'
        assert bean.number == 3
    }
}