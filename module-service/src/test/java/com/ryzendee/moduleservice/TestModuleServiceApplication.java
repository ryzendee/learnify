package com.ryzendee.moduleservice;

import com.ryzendee.moduleservice.testutils.base.BaseTestcontainersTest;
import org.springframework.boot.SpringApplication;

public class TestModuleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ModuleServiceApplication::main).with(BaseTestcontainersTest.class).run(args);
    }

}
