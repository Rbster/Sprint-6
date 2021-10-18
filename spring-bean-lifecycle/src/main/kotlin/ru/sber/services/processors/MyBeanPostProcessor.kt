package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ru.sber.services.CombinedBean

@Component
class MyBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "combinedBean") {
            val b: CombinedBean = bean as CombinedBean
            b.postProcessBeforeInitializationOrderMessage = "postProcessBeforeInitialization() is called"
            return b
        } else {
            return bean
        }
    }
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "combinedBean") {
            val b: CombinedBean = bean as CombinedBean
            b.postProcessAfterInitializationOrderMessage = "postProcessAfterInitialization() is called"
            return b
        } else {
            return bean
        }
    }
}