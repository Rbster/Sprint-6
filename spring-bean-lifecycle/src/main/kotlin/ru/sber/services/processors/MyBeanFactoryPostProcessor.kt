package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import kotlin.reflect.KClass

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.getBeanDefinition("beanFactoryPostProcessorBean").initMethodName = "postConstruct"
//        beanFactory.getDependentBeans("beanFactoryPostProcessorBean")
//        println("here: ")
//        for (beanDefinitionName in beanFactory.beanDefinitionNames) {
//            if (beanDefinitionName == "beanFactoryPostProcessorInterface") {
//                println("name: $beanDefinitionName")
//
//            }
//            if (beanDefinitionName == "BeanFactoryPostProcessorInterface") {
//                println(beanDefinitionName + "fields: ")
//            }
//            for (method in (beanFactory.getBean(beanDefinitionName).javaClass.declaredMethods)) {
//
//                if (beanDefinitionName == "BeanFactoryPostProcessorInterface") {
//                    print(method.name + "; ")
//                }
//                if (method.isAnnotationPresent(PostConstruct::class.java)) {
//                    for (postConstructDependantBeanName in beanFactory.getDependentBeans(beanDefinitionName)) {
//                        beanFactory
//                            .getBeanDefinition(postConstructDependantBeanName)
//                            .initMethodName = method.name
//                    }
//                }
//            }


//            if (beanDefinitionName == "beanFactoryPostProcessorBean") {
//                println(beanDefinitionName + " depend on:")
//            }
//            for (dependentOnBeanName in beanFactory.getDependenciesForBean(beanDefinitionName)) {
//                println("""$beanDefinitionName depend on $dependentOnBeanName""")
//            }

//            for (method in (beanFactory.getBean(beanDefinitionName).javaClass.declaredMethods)) {
//                if (beanDefinitionName == "beanFactoryPostProcessorBean") println(method.name + " " + method.annotations.size)
//                if (method.isAnnotationPresent(PostConstruct::class.java)) {
//                    println("bean name: $beanDefinitionName\n" +
//                            "method name: ${method.name}")
//                    beanFactory
//                        .getBeanDefinition(beanDefinitionName)
//                        .initMethodName = method.name
//                }
//            }
//    }

    }
}