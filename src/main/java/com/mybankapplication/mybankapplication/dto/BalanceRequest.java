package com.mybankapplication.mybankapplication.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceRequest {
    private String id;
    private Double amount;

}
