package com.ananth.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableConfigurationProperties
@RequestMapping("/basepath/v1")
public class ApiController {

	@RequestMapping("/info")
	public String getInfo() {
		StringBuilder sb = new StringBuilder(
				"This is an API for ApiController. \n");
		sb.append("Version:: v1.0 \n");
		return sb.toString();
	}

}
