package org.emulator.device.infrastructure.messaging;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.emulator.device.application.port.CycleInfoEventPublisher;
import org.emulator.device.infrastructure.external.command.CycleInfoListCommand;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaCycleInfoEventPublisher implements CycleInfoEventPublisher {
	private final KafkaTemplate<String, CycleInfoListCommand> kafkaTemplate;

	@Override
	@Async
	public void publishEvent(CycleInfoListCommand message) {
		log.info("[Thread: {}] {}", Thread.currentThread().getName(), "publish message. . .");
		kafkaTemplate.send("cycleInfo-json-topic", String.valueOf(message.mdn()), message);
	}
}
