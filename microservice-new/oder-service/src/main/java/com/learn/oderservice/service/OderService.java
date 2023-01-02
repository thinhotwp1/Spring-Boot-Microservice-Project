package com.learn.oderservice.service;


import com.learn.oderservice.dto.OderLineItemsDto;
import com.learn.oderservice.dto.OderRequest;
import com.learn.oderservice.model.Oder;
import com.learn.oderservice.model.OderLineItems;
import com.learn.oderservice.repository.OderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OderService {

    private final OderRepository oderRepository;

    public void placeOder(OderRequest oderRequest){
        Oder oder = new Oder();
        oder.setOderNumber(UUID.randomUUID().toString());
        List<OderLineItems> oderLineItemsList = oderRequest.getOderLineItemsDtoList().stream().map(oderLineItemsDto -> mapToDto(oderLineItemsDto)).toList();
        oder.setOderLineItems(oderLineItemsList);
        oderRepository.save(oder);
        log.info("save oder id: {}",oder.getId());
    }

    private OderLineItems mapToDto(OderLineItemsDto oderLineItemsDto) {
        OderLineItems oderLineItems = new OderLineItems();
        oderLineItems.setId(oderLineItemsDto.getId());
        oderLineItems.setQuantity(oderLineItemsDto.getQuantity());
        oderLineItems.setSkuCode(oderLineItemsDto.getSkuCode());
        oderLineItems.setPrice(oderLineItemsDto.getPrice());
        return oderLineItems;
    }
}
