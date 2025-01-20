package org.eventhub.presentation;

import java.util.List;

import java.util.stream.Collectors;

import lombok.Builder;

import lombok.NonNull;

import org.eventhub.domain.CycleInfo;
import org.eventhub.domain.CycleInfoList;

@Builder
public record CycleInfoListRequest(
	@NonNull Long mdn,
	@NonNull String tid,
	@NonNull Long mid,
	@NonNull Integer pv,
	@NonNull Long did,
	@NonNull Integer cCnt,
	@NonNull List<SimpleCycleInfoRequest> cList
) {
	public CycleInfoList toDomain() {
		List<CycleInfo> cycleInfoList = cList.stream()
			.map(SimpleCycleInfoRequest::toDomain)
			.toList();

		return CycleInfoList.builder()
			.mdn(mdn)
			.tid(tid)
			.mid(mid)
			.pv(pv)
			.did(did)
			.cCnt(cCnt)
			.cList(cycleInfoList)
			.build();
	}
}
