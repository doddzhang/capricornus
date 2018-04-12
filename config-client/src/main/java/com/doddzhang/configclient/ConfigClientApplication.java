package com.doddzhang.configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 *
 * /{application}/{profile}[/{label}]
 /{application}-{profile}.yml
 /{label}/{application}-{profile}.yml
 /{application}-{profile}.properties
 /{label}/{application}-{profile}.properties
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
}
