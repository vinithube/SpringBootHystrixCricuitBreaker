package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RequestMapping("/main")
@EnableHystrix
@EnableHystrixDashboard
public class HystrixApplication {

	//github URL - https://github.com/Netflix/Hystrix
	public static void main(String[] args) {
		SpringApplication.run(HystrixApplication.class, args);
	}

	@Autowired
	private RestTemplate template;

	@GetMapping("/getEmpHystrix")
	@HystrixCommand(fallbackMethod = "defaultEmployee")
	public String getEmployeeDetailWithHystrix(){

		return template.getForObject("http://localhost:8181/emp/empDetail",String.class);
	}

	public String defaultEmployee(){
		return "I am from fall back";
	}

	@GetMapping("/getEmpWithoutHystrix")
	public String getEmployeeDetailWithOutHystrix(){
		return "I am without Hystrix Command";
	}

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
}
