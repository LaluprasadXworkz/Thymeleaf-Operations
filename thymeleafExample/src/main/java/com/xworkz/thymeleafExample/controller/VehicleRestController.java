package com.xworkz.thymeleafExample.controller;

import com.xworkz.thymeleafExample.entity.VehicleEntity;
import com.xworkz.thymeleafExample.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {
        @Autowired
        private VehicleService vehicleService;
        @GetMapping("/autoSuggestNumberPlate")
        public List<String> autoSuggestNumberPlate(@RequestParam("query") String query) {
                return vehicleService.getNumberPlateSuggestions(query);
        }

}
