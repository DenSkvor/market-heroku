package ru.geekbrains.spring.market.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.spring.market.HistoryAOP;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private HistoryAOP historyAOP;

    @Pointcut("execution(* ru.geekbrains.spring.market.controllers.*.*(..))")
    private void getLog(){
    }

    @After("getLog()")
    public void logAfter(JoinPoint jp) {
        //получаем ссылку
        StringBuilder url = new StringBuilder();
        url
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append(" ")
                .append(jp.getTarget().getClass().getAnnotation(RequestMapping.class).value()[0]);

        StringBuilder methodName = new StringBuilder();
        methodName
                .append(jp.toShortString())
                .delete(methodName.lastIndexOf("("), methodName.length()).delete(0, methodName.lastIndexOf(".") + 1);
        Method[] methods = jp.getTarget().getClass().getMethods();
        Method method = null;
        for (Method m : methods) {
            if(m.getName().equals(methodName.toString())){
                method = m;
                break;
            }
        }
        methodName.setLength(0);

        String[] annoValues = null;
        if(method.getAnnotation(GetMapping.class) != null) annoValues = method.getAnnotation(GetMapping.class).value();
        else if(method.getAnnotation(PostMapping.class) != null) annoValues = method.getAnnotation(PostMapping.class).value();

        if(annoValues != null && annoValues.length > 0) url.append(annoValues[0]);

        //получаем сигнатуру выполняемого метода
        CodeSignature codeSignature = (CodeSignature) jp.getSignature();
        Class[] paramTypes = codeSignature.getParameterTypes();
        String[] paramNames = codeSignature.getParameterNames();
        //Object[] args = jp.getArgs(); можно добавить в лог значения аргументов, но тогда там будет слишком уж большое полотно текста

        StringBuilder exctdMethod = new StringBuilder();
        exctdMethod
                .append(jp.toString())
                .delete(exctdMethod.lastIndexOf("(") + 1, exctdMethod.lastIndexOf(")") + 1);
        for (int i = 0; i < paramNames.length; i++) {
            exctdMethod.append("[").append(paramTypes[i].getName()).append(" ").append(paramNames[i]).append("]");
        }
        exctdMethod.append(")");

        //склеиваем в итоговый лог
        historyAOP.getSessionHistory().add(url.append(" ").append("-> ").append(exctdMethod).toString());
        exctdMethod.setLength(0);
        url.setLength(0);

        //Пример лога:
        //2020-12-13 23:16:38 /products -> execution(String ru.geekbrains.spring.market.controllers.ProductController.showProducts([java.security.Principal principal][org.springframework.ui.Model model][java.lang.Integer pageNum][java.lang.Integer pageSize][java.util.Map params])
        //2020-12-13 23:16:39 /cart -> execution(String ru.geekbrains.spring.market.controllers.CartController.showCart()
        //2020-12-13 23:16:40 /cart/incr -> execution(String ru.geekbrains.spring.market.controllers.CartController.incrementProduct([java.lang.Long id])

    }
}
