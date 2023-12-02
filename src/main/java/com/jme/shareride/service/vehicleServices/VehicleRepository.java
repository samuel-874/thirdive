package com.jme.shareride.service.vehicleServices;

import com.jme.shareride.entity.transport.Category;
import com.jme.shareride.entity.transport.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface VehicleRepository extends JpaRepository<Vehicle,Long>, PagingAndSortingRepository<Vehicle,Long> {
//    Page<Vehicle> findByProduct(Product product, Pageable pageable);

    Vehicle findByVehicleName(String vehicleName);

    Page<Vehicle> findVehicleByCategory(Pageable pageable, Category category);
}
