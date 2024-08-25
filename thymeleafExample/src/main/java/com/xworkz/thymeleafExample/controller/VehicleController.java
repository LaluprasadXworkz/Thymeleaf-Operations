package com.xworkz.thymeleafExample.controller;

import com.xworkz.thymeleafExample.entity.VehicleEntity;

import com.xworkz.thymeleafExample.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

//@RestController
@Controller
//@RequestMapping("/")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/")
    public String home(){

        return "index";
    }

    @GetMapping("/saveForm")
    public String redirectToNewVehicle(Model model) {
        model.addAttribute("vehicle", new VehicleEntity());
        return "new_vehicle";
    }
    @PostMapping("/saveVehicle")
    public String saveVehicle(@ModelAttribute("vehicle") VehicleEntity vehicle ,Model model) {
        vehicleService.saveVehicle(vehicle);
        return "redirect:/viewList";
    }

    @GetMapping("/viewList")
    public String listOfVehicle(Model model){
        return pagePagination(1,model);
    }

    @GetMapping("/delete")
    public String deleteVehicleById(@RequestParam("id") int id,Model model){
        boolean isDelete=vehicleService.deleteVehicleById(id);
            List<VehicleEntity> listVehicles = vehicleService.getAllVehicles();
            model.addAttribute("listVehicles", listVehicles);
            return "viewVehicle";
    }

    @GetMapping("/page/{pageNo}")
    public String pagePagination(@PathVariable(value = "pageNo")int pageNo,Model model){
        int pageSize=10;
        vehicleService.pageChanges(pageNo,model,pageSize);
        return "viewVehicle";
    }

    @PostMapping("/searchNumberPlate") ///{numberPlate}
    public String searchByNumberPlate(@RequestParam("numberPlate") String numberPlate, Model model) {
        List<VehicleEntity> vehicles = vehicleService.findByNumberPlate(numberPlate);
        model.addAttribute("listVehicles", vehicles);
        return "viewVehicle";
    }

    @PostMapping("/searchByDate")
    public String searchByDate(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate, @RequestParam("toDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate, Model model) {
        System.out.println(fromDate);
        List<VehicleEntity> vehicles = vehicleService.findByDateRange(fromDate, toDate);
        model.addAttribute("listVehicles", vehicles);
        return "viewVehicle";
    }

    @GetMapping("/excelToDb")
    public String excelToDb(Model model){
        vehicleService.excelToDb();
        return pagePagination(1,model);
    }

    @GetMapping("/dbTOExcel")
    public String excelData(Model model){
        vehicleService.dbTOExcel();
        return pagePagination(1,model);
    }

//    @GetMapping("/uploadForm")
//    public String showUploadForm() {
//        return "upload";
//    }
//
//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
//        String fileName = file.getOriginalFilename();
//        try {
//            // Save the file locally
//            File localFile = new File("uploads/" + fileName);
//            file.transferTo(localFile);
//
//            // Import data from the saved file
//            vehicleService.saveFromLocalFile(localFile.getAbsolutePath());
//
//            model.addAttribute("message", "Data from Excel file saved to database successfully!");
//        } catch (IOException e) {
//            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
//        } catch (Exception e) {
//            model.addAttribute("message", "Failed to import data: " + e.getMessage());
//        }
//        return "importResult";
//    }

}
