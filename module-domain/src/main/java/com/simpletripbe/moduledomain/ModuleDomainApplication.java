package com.simpletripbe.moduledomain;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.simpletripbe.moduledomain")
@EntityScan("com.simpletripbe.moduledomain")
@EnableJpaRepositories("com.simpletripbe.moduledomain")
@AutoConfiguration
public class ModuleDomainApplication {
}