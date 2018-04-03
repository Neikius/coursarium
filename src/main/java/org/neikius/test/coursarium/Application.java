package org.neikius.test.coursarium;

import org.neikius.test.coursarium.repository.model.Placeholder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackageClasses = Placeholder.class)
@EnableSwagger2
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("v1")
        .apiInfo(apiInfo())
        .select()
        .paths(regex("/v1/.*"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Coursarium API")
        .description("Coursarium, a simple students and courses thing")
        .license("GPLv3")
        .licenseUrl("https://www.gnu.org/licenses/gpl-3.0.txt")
        .version("2.0")
        .build();
  }
}
