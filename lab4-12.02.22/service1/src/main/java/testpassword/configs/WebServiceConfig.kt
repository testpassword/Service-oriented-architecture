package testpassword.configs

import org.springframework.ws.config.annotation.EnableWs
import org.springframework.ws.config.annotation.WsConfigurerAdapter
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.ws.transport.http.MessageDispatcherServlet
import org.springframework.xml.xsd.XsdSchema
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition
import org.springframework.xml.xsd.SimpleXsdSchema
import org.springframework.core.io.ClassPathResource

@EnableWs @Configuration
class WebServiceConfig: WsConfigurerAdapter() {

    @Bean fun messageDispatcherServlet(applicationContext: ApplicationContext): ServletRegistrationBean<MessageDispatcherServlet> =
        ServletRegistrationBean(
            MessageDispatcherServlet().apply {
                setApplicationContext(applicationContext)
                isTransformWsdlLocations = true
        }, "/api/*")

    @Bean(name = ["schema"]) fun defaultWsdl11Definition(countriesSchema: XsdSchema): DefaultWsdl11Definition =
        DefaultWsdl11Definition().apply {
            setPortTypeName("SchemaPort")
            setLocationUri("/api")
            setTargetNamespace("http://testpassword/dtos")
            setSchema(countriesSchema)
        }

    @Bean fun countriesSchema(): XsdSchema =
        SimpleXsdSchema(ClassPathResource("schema.xsd"))
}