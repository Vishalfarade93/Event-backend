package com.event.pgateway.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.pgateway.entity.Ragisters;
import com.event.pgateway.repository.RagisterRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
	private RazorpayClient razorpayClient;

	@Value("${razorpay.key_secret}")
	private String keySecrate;

	@Autowired
	private RagisterRepository repository;

	@PostMapping("/create_order")
	public ResponseEntity<Map<String, Object>> createRequest(@RequestBody Map<String, Object> data)
			throws RazorpayException {
		try {
			int amount = Integer.parseInt(data.get("amount").toString()) * 100;
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("amount", amount);
			jsonObject.put("currency", "INR");
			jsonObject.put("receipt", "txn_" + UUID.randomUUID());
			Order order = razorpayClient.orders.create(jsonObject);

			Map<String, Object> reurnback = new HashMap<>();
			reurnback.put("orderId", order.get("id")); // this id need to pass to frontend it is very important to pass
														// otherwise checkout will not work properl
			reurnback.put("amount", order.get("amount"));
			reurnback.put("currency", order.get("currency"));
			return ResponseEntity.ok(reurnback);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}

	@CrossOrigin("http://localhost:5173")
	@PostMapping("/verify_payment")
	public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> data)
			throws InvalidKeyException, NoSuchAlgorithmException {

		try {
			String PId = data.get("razorpay_payment_id");
			String oId = data.get("razorpay_order_id");
			String sign = data.get("razorpay_signature");

			String paylode = oId + "|" + PId;
			String ecpectedString = hmac(paylode, keySecrate);

			if (ecpectedString.equals(sign)) {
				Ragisters obj = Ragisters.builder().razorpayOrderId(oId).razorpayPaymentId(PId).amount(500)
						.status("SUCCESS").createdAt(LocalDateTime.now()).build();
				repository.save(obj);
				return ResponseEntity.ok("Payment verified and saved!");
			} else {

				return ResponseEntity.badRequest().body("Invalid signature");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification error");
		}

	}

	private String hmac(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac instance = Mac.getInstance("HmacSHA256");
		SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

		instance.init(keySpec);
		byte[] hash = instance.doFinal(data.getBytes());

		// Convert to the HEx
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
