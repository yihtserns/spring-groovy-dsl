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

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionBuilder

/**
 * @author yihtserns
 */
class SpringCategory {

    static BeanDefinition 'new'(Class type, Object[] constructorArgs) {
        Closure configureProperties = {}

        if (constructorArgs.length != 0 && constructorArgs.last() instanceof Closure) {
            configureProperties = constructorArgs.last()
            constructorArgs = constructorArgs.length == 1 ? [] : constructorArgs[0..-2] // Exclude last item
        }

        BeanDefinitionBuilder bean = BeanDefinitionBuilder.genericBeanDefinition(type);
        bean.metaClass.set = { String propName, Object propValue -> bean.addPropertyValue(propName, propValue) }

        constructorArgs.each { arg ->
            bean.addConstructorArgValue(arg)
        }

        configureProperties.resolveStrategy = Closure.DELEGATE_FIRST
        configureProperties.delegate  = bean
        configureProperties()

        return bean.getBeanDefinition()
    }
}