package com.github.arugal.example.flink;

import lombok.*;

/**
 * @author zhangwei
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayTariffView implements java.io.Serializable {

	private static final long serialVersionUID = 4603288631810317034L;

	private Integer meterId;
	private Double rawPap;

}
