package org.collector.config;

import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CycleInfoRequestDeserializer extends JsonDeserializer<CycleInfoRequest> {

	public CycleInfoRequestDeserializer() {
		super(CycleInfoRequest.class);
	}
}
