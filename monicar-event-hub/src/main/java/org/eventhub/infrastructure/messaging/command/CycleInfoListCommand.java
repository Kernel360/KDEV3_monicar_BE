package org.eventhub.infrastructure.messaging.command;

import java.util.List;

import lombok.Builder;

@Builder
public record CycleInfoListCommand(
	long mdn,
	String tid,
	long mid,
	int pv,
	long did,
	int cCnt,
	List<CycleInfoCommand> cList
) {

}

