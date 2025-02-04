package ru.darin.coordinates.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${geoCenter.openapi.dev-url}")
    private String devUrl;

    public static final String DESCRIPTION_GEOCENTER_APP = "Сервис предназначен для:" +
            " \n- сбора данных о границах областей и федеральных округов (массив координат);" +
            " \n- расчета географического центра для областей и федеральных округов на основе собранных данных.";

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("swd86@mail.ru");
        contact.setName("Sergey Darin");

        Info info = new Info()
                .title("Руководство использования сервиса API")
                .version("1.0")
                .contact(contact)
                .description(DESCRIPTION_GEOCENTER_APP);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
