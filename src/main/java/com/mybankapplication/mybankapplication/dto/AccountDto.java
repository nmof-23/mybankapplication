package com.mybankapplication.mybankapplication.dto;

import com.mybankapplication.mybankapplication.model.Currency;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountDto {

    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;

}
