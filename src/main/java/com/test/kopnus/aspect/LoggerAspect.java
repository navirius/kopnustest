package com.test.kopnus.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Component
@Aspect
public class LoggerAspect {
    private Logger logger = LoggerFactory.getLogger(LoggerAspect.class.getName());

    @Pointcut("execution(* com.test.kopnus.controller.*.*(..) )")
    public void restcontroller() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postmapping() {}
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping() {}
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {}

    @Around("restcontroller() && getMapping()")
    public Object LogControllerGet(ProceedingJoinPoint joinPoint) throws Throwable {
        return ExecuteLogging2(joinPoint, null);
    }

    @Around("restcontroller() && postmapping() && args(.., @RequestBody body)")
    public Object LogControllerPost(ProceedingJoinPoint joinPoint, Object body) throws Throwable{
        return ExecuteLogging2(joinPoint, body);
    }

    private Object ExecuteLogging2(ProceedingJoinPoint joinPoint, Object body) throws Throwable {
        logger.info("==================START=================");
        Instant start = Instant.now();
        try{
            final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            logger.info("Remote Host: " + request.getRemoteHost());
            logger.info("Request Method: " + request.getMethod());
            logger.info("Request URI: " + request.getRequestURI());
            logger.info("Request Param: " + request.getQueryString());
            logger.info("Header: " + getRequestHeader(request));
            logger.info("Method-Name: " + joinPoint.getSignature().getName());
            if(request.getRequestURI().contains("fileUpload"))
                return joinPoint.proceed();

            if(request.getContentType()!=null && request.getContentType().equals("application/text")) {
                if (body != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonBody = mapper.writeValueAsString(body);
                    logger.info("requestBody: " + jsonBody);
                }
            }
            else{
                logger.info("requestBody: "+request.getContentType());
            }

            return joinPoint.proceed();
        }
        finally {
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            logger.info("ExecuteTime: " + timeElapsed + "ms");
            logger.info("==================END=================");
        }
    }

    private String getRequestHeader(final HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request.getParameterMap());
        return json;
    }

    @AfterThrowing(pointcut = "restcontroller()", throwing = "error")
    private void LogControllerException(JoinPoint joinPoint, Throwable error){
        logger.info("======LOGGER ERROR_START======");
        logger.info("Method: "+joinPoint.getSignature());
        logger.info("Message: "+error.getMessage());
        logger.info("StackTrace: "+ Arrays.toString(error.getStackTrace()));
        logger.info("======LOGGER ERROR_END======");
    }
//    private Object ExecuteLogging(ProceedingJoinPoint joinPoint, Object body, HttpServletRequest request, boolean isPost) {
//        logger.info("==================START=================");
//        Instant start = Instant.now();
//        try {
//            StringBuilder requestUrl = new StringBuilder(request.getRequestURL().toString());
//            String queryString = request.getQueryString();
//            if(queryString == null)
//                logger.info("URL: " + requestUrl.toString());
//            else
//                logger.info("URL: " + requestUrl.append("?").append(queryString).toString());
//
//            String requestData = request.getReader().lines().collect(Collectors.joining());
//            logger.info("Request: " + requestData);
//            ObjectMapper mapper = new ObjectMapper();
//            if(isPost){
//                String bodyJson = mapper.writeValueAsString(body);
//                logger.info("RequestBody: " + bodyJson);
//            }
//            else {
//                String bodyJson = mapper.writeValueAsString(body);
//                logger.info("RequestHeader: " + bodyJson);
//            }
//
//
//            Object result = joinPoint.proceed();
//            if(result!=null)
//                logger.info("Result: " + result.toString());
//            return result;
//        }
//        catch (Throwable t){
//            logger.error("Error: " + t.getMessage() +"\n" + t.getStackTrace());
//            return new Object();
//        }
//        finally {
//            Instant finish = Instant.now();
//            long timeElapsed = Duration.between(start, finish).toMillis();
//            logger.info("ExecuteTime: " + timeElapsed + "ms");
//            logger.info("==================END=================");
//        }
//    }

//    private void ParameterLogger(ProceedingJoinPoint joinPoint)
//    {
//        Object[] args = joinPoint.getArgs();
//            MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
//            Method method = methodSignature.getMethod();
//            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//            assert args.length == parameterAnnotations.length;
//            for (int argIndex = 0; argIndex < args.length; argIndex++) {
//                for (Annotation annotation : parameterAnnotations[argIndex]) {
//                    if (!(annotation instanceof RequestHeader)){
//                        continue;
//                    }
//                    if(annotation instanceof RequestParam){
//                        RequestParam requestParam = (RequestParam) annotation;
//                        if (! "accessToken".equals(requestParam.value()))
//                            continue;
//                        System.out.println("  " + requestParam.value() + " = " + args[argIndex]);
//                    }
//
//                    if(annotation instanceof RequestBody){
//                        RequestBody requestBody = (RequestBody) annotation;
//                        ObjectMapper mapper = new ObjectMapper();
//                        //String bodyJson = mapper.writeValueAsString(requestBody.);
//                        logger.info("RequestBody: ");
//                    }
//
//                }
//            }
//    }

}
