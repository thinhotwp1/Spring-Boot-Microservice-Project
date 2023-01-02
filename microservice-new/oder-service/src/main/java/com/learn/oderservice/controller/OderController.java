package com.learn.oderservice.controller;


import com.learn.oderservice.dto.OderRequest;
import com.learn.oderservice.service.OderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oder")
public class OderController {

    private final OderService oderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOder(@RequestBody OderRequest oderRequest){
        oderService.placeOder(oderRequest);
        return "Oder Placed Sucessful";
    }
}
