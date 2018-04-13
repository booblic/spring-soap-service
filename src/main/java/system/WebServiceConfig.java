package system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.security.wss4j.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.security.wss4j.callback.SpringSecurityPasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import service.UserService;

import java.util.List;
import java.util.Properties;

@EnableWs
@Configuration
@ComponentScan("repository")
public class WebServiceConfig extends WsConfigurerAdapter {

    UserDetailsService userDetailsService = new UserService();


    @Bean
    public SpringSecurityPasswordValidationCallbackHandler securityPassword() {
        SpringSecurityPasswordValidationCallbackHandler securityPassword = new SpringSecurityPasswordValidationCallbackHandler();
        securityPassword.setUserDetailsService(userDetailsService);
        return securityPassword;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler securityCallbackHandler(){
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        Properties users = new Properties();
        users.setProperty("admin", "secret");
        callbackHandler.setUsers(users);
        return callbackHandler;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor(){
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setValidationActions("Timestamp UsernameToken");
        securityInterceptor.setValidationCallbackHandler(securityPassword());
        return securityInterceptor;
    }

    @Override
    public void addInterceptors(List interceptors) {
        interceptors.add(securityInterceptor());
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "countries")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountriesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }
}