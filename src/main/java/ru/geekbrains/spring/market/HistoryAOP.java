package ru.geekbrains.spring.market;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class HistoryAOP {

    private List<String> sessionHistory;

    @PostConstruct
    private void init(){
        sessionHistory = new ArrayList<>();
    }
}
