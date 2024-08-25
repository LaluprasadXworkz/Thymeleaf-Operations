package com.xworkz.thymeleafExample.service.impl;

import com.xworkz.thymeleafExample.entity.VehicleEntity;
import com.xworkz.thymeleafExample.repositroy.VehicleRepository;
import com.xworkz.thymeleafExample.service.VehicleService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Override
    public List<VehicleEntity> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    @Override
    public VehicleEntity saveVehicle(VehicleEntity vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<VehicleEntity> saveAll(List<VehicleEntity> vehicle){
        return vehicleRepository.saveAll(vehicle);
    }
    @Override
    public boolean deleteVehicleById(int id){
        if (id != 0) {
            vehicleRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public Page<VehicleEntity> pagePagination(int pageNo,int pageSize){
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);
        return this.vehicleRepository.findAll(pageable);
    }

    @Override
    public void pageChanges(int num, Model mdl, int pSize) {
        Page<VehicleEntity> page=pagePagination(num,pSize);
        List<VehicleEntity> listVehicles=page.getContent();
        mdl.addAttribute("currentPage",num);
        mdl.addAttribute("totalPages",page.getTotalPages());
        mdl.addAttribute("totalItems",page.getTotalElements());
        mdl.addAttribute("listVehicles",listVehicles);
    }
    @Override
    public List<VehicleEntity> findByNumberPlate(String numberPlate) {
        return vehicleRepository.findByNumberPlate(numberPlate);
    }
    @Override
    public List<String> getNumberPlateSuggestions(String query) {
        return vehicleRepository.findBySNumberPlate(query);
    }

    @Override
    public List<VehicleEntity> findByDateRange(LocalDate fromDate, LocalDate toDate) {
        return vehicleRepository.findingMfDate(fromDate, toDate);
    }

    @Override
    public void excelToDb() {
        List<VehicleEntity> list = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(
                    new File("C:\\Users\\lalup\\OneDrive\\Desktop\\vehicle.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)){
            System.out.println("INvoking excelToDb");
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (row.getRowNum() == 0) {
                    continue;
                }

                VehicleEntity vehicle = new VehicleEntity();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            vehicle.setId((int)( cell.getNumericCellValue()));
                            System.out.println(cell.getNumericCellValue());
                            break;
                        case 1:
                            vehicle.setNumberOfWheel((int)(cell.getNumericCellValue()));
                            System.out.println(cell.getNumericCellValue());
                            break;
                        case 2:
                            vehicle.setNumberPlate(cell.getStringCellValue());
                            System.out.println(cell.getStringCellValue());
                            break;
                        case 3:
                            vehicle.setVehicleName(cell.getStringCellValue());
                            System.out.println(cell.getStringCellValue());
                            break;

                        case 4:
                            vehicle.setOwnerName(cell.getStringCellValue());
                            System.out.println(cell.getStringCellValue());
                            break;
                        case 5:
                            vehicle.setMfDate(cell.getLocalDateTimeCellValue().toLocalDate());
                            System.out.println(cell.getLocalDateTimeCellValue().toLocalDate());
                            break;
                        default:

                            break;
                    }
                }
                list.add(vehicle);
            }
            workbook.close();
            fileInputStream.close();

            saveAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (VehicleEntity vehicle : list) {
            System.out.println(vehicle);
        }
    }

    @Override
    public void dbTOExcel() {
        try {
            List<VehicleEntity> resultList = getAllVehicles();

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("abc");
            System.out.println("Invoking DB to Excel");

            String[] headers = {"ID", "Vehicle Name", "Number Plate", "Owner Name", "Number of Wheels", "Manufacture Date"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            int rowNum = 1;
            for (VehicleEntity vehicle : resultList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(vehicle.getId());
                row.createCell(1).setCellValue(vehicle.getVehicleName());
                row.createCell(2).setCellValue(vehicle.getNumberPlate());
                row.createCell(3).setCellValue(vehicle.getOwnerName());
                row.createCell(4).setCellValue(vehicle.getNumberOfWheel());
                row.createCell(5).setCellValue(vehicle.getMfDate().toString());
            }
            try (FileOutputStream outputStream = new
                    FileOutputStream("C:\\Users\\lalup\\OneDrive\\Desktop\\abc.xlsx")) {
                workbook.write(outputStream);
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public void dbTOExcel() {
//        try {
//            List<VehicleEntity> resultList=getAllVehicles();
//
//            XSSFWorkbook workbook=new XSSFWorkbook();
//            XSSFSheet sheet=workbook.createSheet("abc");
//            System.out.println("INvoking DB to excel");
//            int rowNum=1;
//            for(VehicleEntity vehicle:resultList){
//                Row row= sheet.createRow(rowNum++);
//                Cell cell1=row.createCell(0);
//                cell1.setCellValue(vehicle.getId());
//                System.out.println(cell1);
//
//                Cell cell2=row.createCell(1);
//                cell2.setCellValue(vehicle.getVehicleName());
//                System.out.println(cell2);
//
//                Cell cell3=row.createCell(2);
//                cell3.setCellValue(vehicle.getNumberPlate());
//                System.out.println(cell3);
//
//                Cell cell4=row.createCell(3);
//                cell4.setCellValue(vehicle.getOwnerName());
//                System.out.println(cell4);
//
//                Cell cell5=row.createCell(4);
//                cell5.setCellValue(vehicle.getNumberOfWheel());
//                System.out.println(cell5);
//
//                Cell cell6=row.createCell(5);
//                cell6.setCellValue(vehicle.getMfDate());
//                System.out.println(cell6+" "+vehicle.getMfDate());
//            }
//
//            try(FileOutputStream outputStream=new
//                    FileOutputStream("C:\\Users\\lalup\\OneDrive\\Desktop\\abc.xlsx")){
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

}