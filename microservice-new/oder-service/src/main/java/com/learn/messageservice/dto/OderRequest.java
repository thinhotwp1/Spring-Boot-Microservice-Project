package com.learn.messageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OderRequest {
    private List<OderLineItemsDto> oderLineItemsDtoList;
}
