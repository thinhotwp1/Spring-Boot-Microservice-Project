package com.learn.messageservice.service;


import com.learn.messageservice.dto.InventoryResponse;
import com.learn.messageservice.dto.OderLineItemsDto;
import com.learn.messageservice.dto.OderRequest;
import com.learn.messageservice.model.Message;
import com.learn.messageservice.model.Oder;
import com.learn.messageservice.model.OderLineItems;
import com.learn.messageservice.repository.OderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OderService {

    private final OderRepository oderRepository;
    private final WebClient.Builder webClientBuilder;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange exchange_direct;

    public String placeOder(OderRequest oderRequest){
        Oder oder = new Oder();
        Message message = new Message();
        String id = UUID.randomUUID().toString();
        oder.setOderNumber(id);
        message.setOderNumber(id);
        List<OderLineItems> oderLineItemsList = oderRequest.getOderLineItemsDtoList().stream().map(oderLineItemsDto -> mapToDto(oderLineItemsDto)).toList();
        oder.setOderLineItems(oderLineItemsList);
        List<String> skuCodes = new ArrayList<>();

        for (OderLineItems oderLineItems : oder.getOderLineItems()) {
            String s = oderLineItems.getSkuCode()+","+ oderLineItems.getQuantity().toString();
            skuCodes.add(s);
        }

        // Call Inventory Service, and place oder if product is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::getIsInStock);

        if(allProductsInStock) {
            oderRepository.save(oder);
            rabbitTemplate.convertAndSend(exchange_direct.getName(),"routing.A",message);
            return "Oder Placed Sucessful";
        } else {
            return "Product in list is not in stock, please try again later!";
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
