package org.emulator.device.infrastructure.external.command;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import lombok.Builder;

import org.emulator.device.domain.CycleInfo;
import org.emulator.device.infrastructure.external.command.vo.FixedVehicleInfo;

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
	public static CycleInfoListCommand from(List<CycleInfo> cycleInfoList) {
		FixedVehicleInfo fixedInfo = FixedVehicleInfo.getInstance();
		List<CycleInfoCommand> cycleList = cycleInfoList.stream()
			.map(CycleInfoCommand::from)
			.collect(Collectors.toCollection(ArrayList::new)
			);

		return CycleInfoListCommand.builder()
			.mdn(fixedInfo.getMdn())
			.tid(fixedInfo.getTid())
			.mid(fixedInfo.getMid())
			.pv(fixedInfo.getPv())
			.did(fixedInfo.getDid())
			.cCnt(cycleList.size())
			.cList(cycleList)
			.build();
	}
}
