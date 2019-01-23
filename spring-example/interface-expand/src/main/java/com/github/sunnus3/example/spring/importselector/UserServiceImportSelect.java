package com.github.sunnus3.example.spring.importselector;

import com.github.sunnus3.example.spring.UserServiceImpl;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author: zhangwei
 * @date: 16:06/2019-01-22
 */
public class UserServiceImportSelect implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> map = importingClassMetadata.getAnnotationAttributes(EnableUserService.class.getName(), true);
        map.forEach((key, value) -> {
            System.out.println("key is:"+key+" value is:"+value);
        });
        return new String[]{UserServiceImpl.class.getName()};
    }
}
