package org.eventhub.infrastructure.messaging.command;

import java.util.List;

import lombok.Builder;

import org.eventhub.domain.CycleInfoList;

@Builder
public record CycleInfoListCommand(
	Long mdn,
	String tid,
	Long mid,
	Integer pv,
	Long did,
	Integer cCnt,
	List<CycleInfoCommand> cList
) {
	public static CycleInfoListCommand from(CycleInfoList cycleInfoList) {
		List<CycleInfoCommand> cycleInfoCommandList = cycleInfoList.getCList().stream()
			.map(CycleInfoCommand::from)
			.toList();

		return CycleInfoListCommand.builder()
			.mdn(cycleInfoList.getMdn())
			.tid(cycleInfoList.getTid())
			.mid(cycleInfoList.getMid())
			.pv(cycleInfoList.getPv())
			.did(cycleInfoList.getDid())
			.cCnt(cycleInfoList.getCCnt())
			.cList(cycleInfoCommandList)
			.build();
	}
}

