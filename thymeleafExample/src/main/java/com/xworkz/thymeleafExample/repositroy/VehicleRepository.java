package com.xworkz.thymeleafExample.repositroy;

import com.xworkz.thymeleafExample.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRepository  extends JpaRepository<VehicleEntity, Integer> {
  @Query("select v from VehicleEntity v where v.numberPlate like :numberPlate% or v.numberPlate like %:numberPlate or v.numberPlate like %:numberPlate%")
  List<VehicleEntity> findByNumberPlate(@Param("numberPlate") String numberPlate);

  @Query("select v.numberPlate from VehicleEntity v where v.numberPlate like :numberPlate% or v.numberPlate like %:numberPlate or v.numberPlate like %:numberPlate%")
  List<String> findBySNumberPlate(@Param("numberPlate") String numberPlate);

  @Query("SELECT v FROM VehicleEntity v WHERE v.mfDate BETWEEN :fromDate AND :toDate ORDER BY v.mfDate ASC")
  List<VehicleEntity> findingMfDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

}
