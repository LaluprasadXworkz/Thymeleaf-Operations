package com.xworkz.thymeleafExample.service;

import com.xworkz.thymeleafExample.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

public interface VehicleService {
    public List<VehicleEntity> getAllVehicles();
    public VehicleEntity saveVehicle(VehicleEntity vehicle);
    public boolean deleteVehicleById(int id);
    public Page<VehicleEntity> pagePagination(int pageNo, int pageSize);
    public void pageChanges(int num, Model mdl, int pSize);
    public List<VehicleEntity> findByNumberPlate(String numberPlate);
    public List<String> getNumberPlateSuggestions(String query);
    public List<VehicleEntity> findByDateRange(LocalDate fromDate, LocalDate toDate);
    public void excelToDb();
    public void dbTOExcel();
}
