package com.github.sunnus3.example.example.fegin;

import com.github.sunnus3.example.eureka.ExampleApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: zhangwei
 * @date: 18:40/2019-02-18
 */
@FeignClient(name = "eureka-producer",
        fallback = HystrixExampleApiFallBack.class)
public interface HystrixExampleApiRemote extends ExampleApi {

}
