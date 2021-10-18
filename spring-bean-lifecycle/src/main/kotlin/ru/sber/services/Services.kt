package ru.sber.services

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class CallbackBean : InitializingBean, DisposableBean {
    var greeting: String? = "What's happening?"

    override fun afterPropertiesSet() {
        greeting = "Hello! My name is callbackBean!"
    }

    override fun destroy() {
        greeting = "Sorry, but I really have to go."
    }


}

class CombinedBean : InitializingBean {
    var postProcessBeforeInitializationOrderMessage: String? = null
    var postConstructOrderMessage: String? = null
    var customInitOrderMessage: String? = null
    var afterPropertiesSetOrderMessage: String? = null
    var postProcessAfterInitializationOrderMessage: String? = null

    override fun afterPropertiesSet() {
        afterPropertiesSetOrderMessage = "afterPropertiesSet() is called"
    }

    fun customInit() {
        customInitOrderMessage = "customInit() is called"
    }

    @PostConstruct
    fun postConstruct() {
        postConstructOrderMessage = "postConstruct() is called"
    }


}

@Component
class InitialisePropertiesBeanPostProcessor : BeanPostProcessor {
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



@Component
class BeanFactoryPostProcessorBean : BeanFactoryPostProcessorInterface {
    var preConfiguredProperty: String? = "I'm not set up yet"

    override fun postConstruct() {
        preConfiguredProperty = "Done!"
    }
}

interface BeanFactoryPostProcessorInterface {
    @PostConstruct
    fun postConstruct()
}

