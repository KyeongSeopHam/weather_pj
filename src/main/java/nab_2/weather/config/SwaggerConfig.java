package nab_2.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**any() 모든 api가 나오게하겠따.
//none() : 아무것도 x
 ant() 앤트패턴: 특정한패턴 ex)   /read/**      "/read/**" 로 시작하는패턴들은다보여주겠따
 admin (백엔드개발자만 쓰기위한api도 있을수있따. )
*/
 @Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("nab_2.weather"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("날씨 일기 프로젝트 ^^ ")
                .description("날씨 일기를 CRUD 할 수 있는 백엔드 API 입니다.!!")
                .version("1.0")
                .build();
    }

}



