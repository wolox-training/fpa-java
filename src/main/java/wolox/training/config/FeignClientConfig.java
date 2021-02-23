package wolox.training.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("wolox.training")
public class FeignClientConfig {
}
