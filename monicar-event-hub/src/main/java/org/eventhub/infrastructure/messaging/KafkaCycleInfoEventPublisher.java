package org.eventhub.infrastructure.messaging;

import org.eventhub.infrastructure.messaging.command.CycleInfoListCommand;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaCycleInfoEventPublisher implements CycleInfoEventPublisher{

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	@Async
	public void publishEvent(CycleInfoListCommand message) {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "publish message. . .");
		kafkaTemplate.send("cycleInfo-json-topic", String.valueOf(message.mdn()), message);
	}
}
