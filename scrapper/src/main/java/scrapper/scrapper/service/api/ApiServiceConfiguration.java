package scrapper.scrapper.service.api;

import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApiServiceConfiguration {

    private final ApplicationContext context;

    @Bean
    public ApiService apiService() {
        List<Class<? extends ApiService>> services = new ArrayList<>(new Reflections(
                ClasspathHelper.forClass(ApiService.class))
                .getSubTypesOf(ApiService.class));

        ApiService firstService = context.getBean(services.removeFirst());
        ApiService currentService = firstService;

        for (Class<? extends ApiService> service : services) {
            currentService = currentService.setNextService(context.getBean(service));
        }
        return firstService;
    }
}
