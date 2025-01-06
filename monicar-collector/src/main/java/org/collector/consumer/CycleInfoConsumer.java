package org.collector.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CycleInfoConsumer {

	@KafkaListener(
		topics = { "cycleInfo-json-topic" },
		groupId = "consumer-group"
	)
	public void accept(ConsumerRecord<String, CycleInfoRequest> message) {
		System.out.println("[Main Consumer] Message arrived! - " + message.value());
	}
}
