package com.mybankapplication.mybankapplication.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Customer {
    @Id
    private String id;
    private String name;
    private Integer dateOfBirth;
    private  City city;
    private String address;

}
