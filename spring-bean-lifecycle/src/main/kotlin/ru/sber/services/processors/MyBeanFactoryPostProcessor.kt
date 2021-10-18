package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import org.springframework.util.ClassUtils

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        var className: String?
        for (bName in beanFactory.beanDefinitionNames) {
            className = beanFactory.getBeanDefinition(bName).beanClassName
            if (className != null) {
                for (intFace in ClassUtils
                    .getAllInterfacesForClass(ClassUtils.forName(className, beanFactory.beanClassLoader))
                ) {
                    for (method in intFace.declaredMethods) {
                        if (method.isAnnotationPresent(PostConstruct::class.java)) {
                            beanFactory.getBeanDefinition(bName).initMethodName = method.name
                        }
                    }
                }
            }
        }
    }
}