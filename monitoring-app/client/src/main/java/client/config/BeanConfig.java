package client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
    @Value("${rest.equipmnet.host}")
    private String host;

    @Value("${rest.user}")
    private String user;

    @Value("${rest.password}")
    private String password;

    @Value("${ssl.host.verifier.hack}")
    private String sslHost;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> {
            if (hostname.equals(sslHost)) {
                return true;
            }
            return false;
        });

        return builder.basicAuthorization(user, password).rootUri(host).build();
    }
}
