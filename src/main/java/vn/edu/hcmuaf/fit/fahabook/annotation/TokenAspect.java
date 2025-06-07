package vn.edu.hcmuaf.fit.fahabook.annotation;

import java.lang.reflect.Parameter;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;

@Aspect
@Component
@RequiredArgsConstructor
public class TokenAspect {
    @Around(
            "within(@org.springframework.web.bind.annotation.RestController *) && execution(* *(.., @vn.edu.hcmuaf.fit.fahabook.annotation.GetToken (*), ..))")
    public Object injectToken(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new AppException(ErrorCode.SERVICE_UNAVAILABLE);
        }
        HttpServletRequest request = attributes.getRequest();

        // Lấy token từ Header Authorization
        String authHeader = request.getHeader("Authorization");
        String token = (authHeader != null && authHeader.startsWith(BEARER_PREFIX))
                ? authHeader.substring(BEARER_PREFIX.length())
                : null;

        Object[] args = joinPoint.getArgs();
        Parameter[] parameters =
                ((MethodSignature) joinPoint.getSignature()).getMethod().getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(GetToken.class)) {
                GetToken annotation = parameters[i].getAnnotation(GetToken.class);

                // Nếu required = false và không có token, đặt giá trị null
                if (token == null && !annotation.required()) {
                    args[i] = null;
                } else if (token != null) {
                    args[i] = token;
                }
                break;
            }
        }
        return joinPoint.proceed(args);
    }

    private static final String BEARER_PREFIX = "Bearer ";

    public static String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
