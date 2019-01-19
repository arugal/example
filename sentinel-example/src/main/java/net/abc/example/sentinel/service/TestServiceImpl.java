package net.abc.example.sentinel.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

/**
 * @author: zhangwei
 * @date: 19:28/2019-01-19
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    @SentinelResource(value = "test", blockHandler = "handlerException", blockHandlerClass = {ExceptionUtil.class})
    public void test() {
        System.out.println("Test");
    }

    @Override
    @SentinelResource(value = "hello", blockHandler = "exceptionHandler")
    public String hello(long s) {
        return String.format("Hello at %d", s);
    }

    public String exceptionHandler(long s, BlockException ex){
        ex.printStackTrace();
        return "Oops, error occurend at " + s;
    }
}
