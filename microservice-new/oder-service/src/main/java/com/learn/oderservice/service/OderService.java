package com.learn.oderservice.service;


import com.learn.oderservice.dto.InventoryResponse;
import com.learn.oderservice.dto.OderLineItemsDto;
import com.learn.oderservice.dto.OderRequest;
import com.learn.oderservice.model.Oder;
import com.learn.oderservice.model.OderLineItems;
import com.learn.oderservice.repository.OderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OderService {

    private final OderRepository oderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOder(OderRequest oderRequest){
        Oder oder = new Oder();
        oder.setOderNumber(UUID.randomUUID().toString());
        List<OderLineItems> oderLineItemsList = oderRequest.getOderLineItemsDtoList().stream().map(oderLineItemsDto -> mapToDto(oderLineItemsDto)).toList();
        oder.setOderLineItems(oderLineItemsList);

        List<String> skuCodes = oder.getOderLineItems().stream().map(OderLineItems::getSkuCode).toList();
        // Call Inventory Service, and place oder if product is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getIsInStock);

        if(allProductsInStock) {
            oderRepository.save(oder);
            log.info("save oder id: {}", oder.getId());
        } else {
            throw new IllegalArgumentException("Product in list is not in stock, please try again later!");
        }
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
