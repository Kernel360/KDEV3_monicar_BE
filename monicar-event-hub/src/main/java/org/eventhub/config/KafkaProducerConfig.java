package org.eventhub.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;

import org.eventhub.infrastructure.messaging.TypeIdInterceptor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public class KafkaProducerConfig {

	@Bean
	@Primary
	@ConfigurationProperties("spring.kafka")
	public KafkaProperties kafkaProperties() {
		return new KafkaProperties();
	}

	@Bean
	public ProducerFactory<String, Object> producerFactory(KafkaProperties kafkaProperties) {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getKeySerializer());
		// props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, TypeIdInterceptor.class.getName());
		props.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());

		JsonSerializer<Object> jsonSerializer = new JsonSerializer<>();
		jsonSerializer.setAddTypeInfo(false);

		return new DefaultKafkaProducerFactory<>(props,
			new org.apache.kafka.common.serialization.StringSerializer(),
			jsonSerializer);
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate(KafkaProperties kafkaProperties) {
		return new KafkaTemplate<>(producerFactory(kafkaProperties));
	}
}
