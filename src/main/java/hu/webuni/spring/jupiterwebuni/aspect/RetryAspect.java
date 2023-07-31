package hu.webuni.spring.jupiterwebuni.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RetryAspect {

//    vagy a metóduson vagy az osztályon rajta van a @Retry annotáció
    @Pointcut("@annotation(hu.webuni.spring.jupiterwebuni.aspect.Retry) || @within(hu.webuni.spring.jupiterwebuni.aspect.Retry)")
    public void retryPointCut() {
    }

    @Around("retryPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Retry retry = null;
        Signature signature = joinPoint.getSignature();
        //TODO: retry annotáció példány kinyerése

//        ha metoduson van rajta
        if(signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            retry = method.getAnnotation(Retry.class);
        } else {
//            ha nem metodus signaterue akkor a getDeclaringType-ot kerjuk el
            Class<?> declaringType = signature.getDeclaringType();
            retry = declaringType.getAnnotation(Retry.class);
        }

        int times = retry.times();
        long waitTime = retry.waitTime();

        if (times <= 0) {
            times = 1;
        }

        for (int numTry=1; numTry <= times; numTry++) {

            System.out.format("Try times: %d %n", numTry);

            try {
                return joinPoint.proceed();
            } catch (Exception e) {

                if (numTry == times)
                    throw e;

                if (waitTime > 0)
                    Thread.sleep(waitTime);
            }
        }

        return null;	//soha nem jutunk ide
    }
}
