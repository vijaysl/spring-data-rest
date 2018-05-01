**Update**
[Stack Overflow](https://stackoverflow.com/questions/50070023/spring-data-rest-kotlin-associations-post)
This works after changing val to var in the entities and removing the dependency

    com.fasterxml.jackson.module:jackson-module-kotlin
<br>

    curl -i -X POST -H "Content-Type:application/json" -d '{"name":"Anon"}' http://localhost:8080/authors
    curl -i -X POST -H "Content-Type:application/json" -d '{"name":"My Library"}' http://localhost:8080/libraries

**OneToMany**

    curl -i -X POST -d '{"title":"Books", "library":"/libraries/1"}' -H "Content-Type:application/json" http://localhost:8080/books

**ManyToMany**

    curl -i -X PUT -H "Content-Type:text/uri-list" -d "http://localhost:8080/books/1" http://localhost:8080/authors/1/books
<br>

### Original

    curl -i -X POST -H "Content-Type:application/json" -d '{"name":"My Library"}' http://localhost:8080/libraries
    curl -i -X POST -d '{"title":"Books", "library":"http://localhost:8080/libraries/1"}' -H "Content-Type:application/json" http://localhost:8080/books

2018-04-26 14:13:43.730 ERROR 79256 --- [nio-8080-exec-2] b.e.h.RestResponseEntityExceptionHandler : org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot construct instance of `com.baeldung.models.Library` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('http://localhost:8080/libraries/1'); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `com.baeldung.models.Library` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('http://localhost:8080/libraries/1')
 at [Source: (org.apache.catalina.connector.CoyoteInputStream); line: 1, column: 29] (through reference chain: com.baeldung.models.Book["library"])

org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot construct instance of `com.baeldung.models.Library` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('http://localhost:8080/libraries/1'); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `com.baeldung.models.Library` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('http://localhost:8080/libraries/1')
 at [Source: (org.apache.catalina.connector.CoyoteInputStream); line: 1, column: 29] (through reference chain: com.baeldung.models.Book["library"])
        at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:241) ~[spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readInternal(AbstractJackson2HttpMessageConverter.java:215) ~[spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.http.converter.AbstractHttpMessageConverter.read(AbstractHttpMessageConverter.java:196) ~[spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.data.rest.webmvc.config.PersistentEntityResourceHandlerMethodArgumentResolver.read(PersistentEntityResourceHandlerMethodArgumentResolver.java:230) ~[spring-data-rest-webmvc-3.0.6.RELEASE.jar:3.0.6.RELEASE]
        at org.springframework.data.rest.webmvc.config.PersistentEntityResourceHandlerMethodArgumentResolver.lambda$read$7(PersistentEntityResourceHandlerMethodArgumentResolver.java:188) ~[spring-data-rest-webmvc-3.0.6.RELEASE.jar:3.0.6.RELEASE]
        at java.util.Optional.orElseGet(Optional.java:267) ~[na:1.8.0_152]
        at org.springframework.data.rest.webmvc.config.PersistentEntityResourceHandlerMethodArgumentResolver.read(PersistentEntityResourceHandlerMethodArgumentResolver.java:188) ~[spring-data-rest-webmvc-3.0.6.RELEASE.jar:3.0.6.RELEASE]
        at org.springframework.data.rest.webmvc.config.PersistentEntityResourceHandlerMethodArgumentResolver.resolveArgument(PersistentEntityResourceHandlerMethodArgumentResolver.java:133) ~[spring-data-rest-webmvc-3.0.6.RELEASE.jar:3.0.6.RELEASE]
        at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:124) ~[spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:161) ~[spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:131) ~[spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102) ~[spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:877) ~[spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:783) ~[spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991) ~[spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925) ~[spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974) [spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:877) [spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:661) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851) [spring-webmvc-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:742) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52) [tomcat-embed-websocket-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200) [spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) [spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:496) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:803) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:790) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1459) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_152]
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_152]
        at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-8.5.29.jar:8.5.29]
        at java.lang.Thread.run(Thread.java:748) [na:1.8.0_152]
Caused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `com.baeldung.models.Library` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('http://localhost:8080/libraries/1')
 at [Source: (org.apache.catalina.connector.CoyoteInputStream); line: 1, column: 29] (through reference chain: com.baeldung.models.Book["library"])
        at com.fasterxml.jackson.databind.exc.MismatchedInputException.from(MismatchedInputException.java:63) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.DeserializationContext.reportInputMismatch(DeserializationContext.java:1342) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.DeserializationContext.handleMissingInstantiator(DeserializationContext.java:1031) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.ValueInstantiator._createFromStringFallbacks(ValueInstantiator.java:371) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.std.StdValueInstantiator.createFromString(StdValueInstantiator.java:323) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.deserializeFromString(BeanDeserializerBase.java:1366) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.BeanDeserializer._deserializeOther(BeanDeserializer.java:171) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:161) ~[jackson-databind-2.9.5.jar:2.9.5]

        at com.fasterxml.jackson.databind.deser.SettableBeanProperty.deserialize(SettableBeanProperty.java:529) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.BeanDeserializer._deserializeWithErrorWrapping(BeanDeserializer.java:528) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.BeanDeserializer._deserializeUsingPropertyBased(BeanDeserializer.java:417) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.deserializeFromObjectUsingNonDefault(BeanDeserializerBase.java:1280) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:326) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:159) ~[jackson-databind-2.9.5.jar:2.9.5]

        at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4001) ~[jackson-databind-2.9.5.jar:2.9.5]
        at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3072) ~[jackson-databind-2.9.5.jar:2.9.5]
        at org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.readJavaType(AbstractJackson2HttpMessageConverter.java:235) ~[spring-web-5.0.5.RELEASE.jar:5.0.5.RELEASE]
        ... 46 common frames omitted
