package org.collector.producer;

import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class CycleInfoProducer {
	private final KafkaTemplate<String, CycleInfoRequest> kafkaTemplate;

	public void sendMessage(CycleInfoRequest message) {
		log.info("send message: {}", message);
		kafkaTemplate.send("cycleInfo-json-topic", String.valueOf(message.mdn()), message);
	}
}
