package org.eventhub.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CycleInfoList {
	private long mdn;
	private String tid;
	private long mid;
	private int pv;
	private long did;
	private int cCnt;
	private List<CycleInfo> cList;
}
