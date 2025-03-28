package org.desp.vote.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StreakRewardDto {
    private String id;
    private String type;
    private int amount;
}
