package com.github.xhrg.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SSEController {

	@GetMapping("/sse")
	public SseEmitter createConnect() {
		// 设置超时时间，0表示不过期。默认30秒
		SseEmitter sseEmitter = new SseEmitter(0L);
		new Thread() {
			public void run() {
				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
						sseEmitter.send(SseEmitter.event().name("message").data("你好" + i).reconnectTime(5000));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
		return sseEmitter;
	}
}
