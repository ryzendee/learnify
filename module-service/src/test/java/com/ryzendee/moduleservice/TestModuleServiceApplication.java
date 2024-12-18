package com.ryzendee.moduleservice;

import org.springframework.boot.SpringApplication;

public class TestModuleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ModuleServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
