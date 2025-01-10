package org.collector;

import org.common.TestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@SpringBootApplication(scanBasePackages = {"org.collector", "org.common"})
@RequiredArgsConstructor
public class CollectorApplication {

    private final TestBean testBean;

    @PostConstruct
    public void dependencyTest() {
        testBean.dependencyTest();
    }

    public static void main(String[] args) {
        SpringApplication.run(CollectorApplication.class, args);
    }
}
