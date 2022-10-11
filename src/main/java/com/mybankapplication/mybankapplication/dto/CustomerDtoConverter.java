package com.mybankapplication.mybankapplication.dto;

import com.mybankapplication.mybankapplication.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {
    public  CustomerDto convert(Customer customer){

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));
        customerDto.setDateOfBirth(customer.getDateOfBirth());

        return customerDto;
    }

}
