package org.eventhub.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CycleInfo {
	private LocalDateTime intervalAt;
	private GpsStatus gcd;
	private long lat;
	private long lng;
	private int ang;
	private int spd;
	private int sum;
}
