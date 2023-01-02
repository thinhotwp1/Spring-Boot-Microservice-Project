package com.learn.oderservice.dto;

import com.learn.oderservice.model.OderLineItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OderRequest {
    private List<OderLineItemsDto> oderLineItemsDtoList ;
}
