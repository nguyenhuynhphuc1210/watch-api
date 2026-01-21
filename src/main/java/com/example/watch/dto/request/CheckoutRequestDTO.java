package com.example.watch.dto.request;

import com.example.watch.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequestDTO {

    private String shippingAddress;
    private String note;

    private PaymentMethod paymentMethod;

    private List<CheckoutItemDTO> items;
}
