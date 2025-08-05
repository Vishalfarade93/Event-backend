package com.event.pgateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Configuration
public class RagistersConfig {

	@Value("${razorpay.key_id}")
	private String keyId;

	@Value("${razorpay.key_secret}")
	private String keySecrate;

	@Bean
	public RazorpayClient rzarpayClient() throws RazorpayException {
		return new RazorpayClient(keyId, keySecrate);
	}

}
