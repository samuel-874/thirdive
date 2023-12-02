package com.jme.shareride.service.vehicleServices;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.dto.*;
import com.jme.shareride.entity.enums.Role;
import com.jme.shareride.entity.others.ImageData;
import com.jme.shareride.entity.transport.About;
import com.jme.shareride.entity.transport.Category;
import com.jme.shareride.entity.transport.Vehicle;
import com.jme.shareride.entity.user_and_auth.Location;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.google.service.DistanceCalculationService;
import com.jme.shareride.external.google.service.model.DistanceInfo;
import com.jme.shareride.external.google.service.model.Element;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.requests.transport.vehicle.VehicleRegRequest;
import com.jme.shareride.service.categoryServices.CategoryRepository;
import com.jme.shareride.service.imageDataServices.ImageDataRepository;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService {

        private final AboutRepository aboutRepository;
        private final VehicleRepository vehicleRepository;
        private final CategoryRepository categoryRepository;
        private final UserRepository userRepository;
        private final ImageDataRepository imageDataRepository;
        private final DistanceCalculationService distanceCalculationService;
        private final JwtService jwtService;



        @Override
        public ResponseEntity addVehicle(HttpServletRequest request, VehicleRegRequest vReg) {
        Vehicle vehicle = new Vehicle();
        UserEntity driver = extractUser(request);
        if (driver.getRoles() != Role.ROLE_DRIVER) {
            return ResponseHandler.handle(401, "Only drivers can access this endpoint", null);
        }
        ImageData imageData = imageDataRepository.findByName(vReg.getImage()).get();

        vehicle.setImage(imageData);
        vehicle.setVehicleName(vReg.getVehicleName());
        vehicle.setChargePerHour(vReg.getChargePerHour());
        vehicle.setDriver(driver);
        About about = new About();
        about.setMaxPower(vReg.getMaxPower());
        about.setFuelDurability(vReg.getFuelDurability());
        about.setMaxSpeed(vReg.getMaxSpeed());
        about.setMph(vReg.getMph());
        about.setColor(vReg.getColor());
        about.setFuelType(vReg.getFuelType());
        about.setGearType(vReg.getGearType());
        about.setSeatCount(vReg.getSeatCount());

        Category category = categoryRepository.findByCategoryName(vReg.getCategory().toLowerCase());
        vehicle.setCategory(category);
        aboutRepository.save(about);
        vehicle.setAbout(about);

        vehicleRepository.save(vehicle);

        VehicleDetails vehicleDetails = mapVehicleToDetails(vehicle);
        return ResponseHandler.handle(201, "Vehicle has been added", vehicleDetails);
    }


        @Override
        public ResponseEntity findAllVehicles(Pageable pageable) {
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);
        if (!vehicles.hasContent() || vehicles.isEmpty()) {
            return ResponseHandler.handle(404, "No vehicle found", null);
        }
        List<VehicleDetails> vehicleDetails = vehicles.stream().map(vehicle -> mapVehicleToDetails(vehicle)).collect(Collectors.toList());


        String vehicleCategoryName = vehicles.getContent().get(0).getCategory().getCategoryName();
        return ResponseHandler.handle(200, "Available " + vehicleCategoryName + "s for ride", vehicleDetails);
    }

        @Override
        public ResponseEntity findVehicleByCategory(HttpServletRequest request, String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        Page<Vehicle> vehicles = vehicleRepository.findVehicleByCategory(pageable, category);
        //todo get user's address
        UserEntity user = extractUser(request);
        Location usersLocation = user.getLocation();
        String usersAddress = mapLocationToAddress(usersLocation);
        List<VehicleDto> vehicleDto = vehicles.stream().map(vehicle -> mapVehicleToDto(vehicle)).collect(Collectors.toList());

        for (VehicleDto vehicle : vehicleDto) {
            UserEntity driver = vehicle.getDriver();
            Location driversLocation = driver.getLocation();
            String driversAddress = mapLocationToAddress(driversLocation);

            Element element = distanceCalculationService.calculate_location(usersAddress, driversAddress);
            DistanceInfo distance = element.getDistance();

            int distanceInInt = Integer.valueOf(distance.getValue());
            if(distanceInInt < 1000 ){
                vehicle.setDistanceBetweenUserAndDriver(String.valueOf(Integer.valueOf(distance.getValue())) + "Km");
                DistanceInfo duration = element.getDuration();
                vehicle.setTimeDifferenceBetweenUserAndDriver(duration.getText());
                vehicle.setDistance(Integer.valueOf(distance.getValue()));
            }
            vehicle.setDistanceBetweenUserAndDriver(String.valueOf(Integer.valueOf(distance.getValue()) * 1000) + "M");
            DistanceInfo duration = element.getDuration();
            vehicle.setTimeDifferenceBetweenUserAndDriver(duration.getText());
            vehicle.setDistance(Integer.valueOf(distance.getValue()));
        }

        vehicleDto.sort(Comparator.comparing(VehicleDto::getDistance));

        return ResponseHandler.handle(200, "Available " + category.getCategoryName() + " near you ", vehicleDto);
    }

        @Override
        public ResponseEntity findVehicleById(long id) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        VehicleDetails vDetails = mapVehicleToDetails(vehicle);

        return ResponseHandler.handle(200, ""+ vehicle.getCategory().getCategoryName() + " fetched successfully", vDetails);
    }


        public static VehicleDto mapVehicleToDto(Vehicle vehicle) {
        VehicleDto vehicleDto = VehicleDto.builder()
                .id(vehicle.getId())
                .vehicleName(vehicle.getVehicleName())
                .driver(vehicle.getDriver())
                .maxPower(vehicle.getAbout().getMaxPower())
                .fuelDurability(vehicle.getAbout().getFuelDurability())
                .maxSpeed(vehicle.getAbout().getMaxSpeed())
                .mph(vehicle.getAbout().getMph())
                .imageData(vehicle.getImage())
                .color(vehicle.getAbout().getColor())
                .chargePerHour(vehicle.getChargePerHour())
                .fuelType(vehicle.getAbout().getFuelType())
                .gearType(vehicle.getAbout().getGearType())
                .seatCount(vehicle.getAbout().getSeatCount())
                .build();
        return vehicleDto;
    }

        public static VehicleDetails mapVehicleToDetails(Vehicle vehicle) {
        VehicleDetails vehicleDetails = VehicleDetails.builder()
                .id(vehicle.getId())
                .vehicleName(vehicle.getVehicleName())
                .driver(vehicle.getDriver())
                .maxPower(vehicle.getAbout().getMaxPower())
                .fuelDurability(vehicle.getAbout().getFuelDurability())
                .maxSpeed(vehicle.getAbout().getMaxSpeed())
                .mph(vehicle.getAbout().getMph())
                .imageData(vehicle.getImage())
                .color(vehicle.getAbout().getColor())
                .chargePerHour(vehicle.getChargePerHour())
                .fuelType(vehicle.getAbout().getFuelType())
                .gearType(vehicle.getAbout().getGearType())
                .seatCount(vehicle.getAbout().getSeatCount())
                .build();
        return vehicleDetails;
    }

        public static VehicleRegistrationDto
        mapVehicleToRegistrationDto(
                Vehicle vehicle
        ) {
            VehicleRegistrationDto vRegDto
                    = VehicleRegistrationDto.builder()
                    .id(vehicle.getId())
                    .vehicleName(vehicle.getVehicleName())
                    .driver(vehicle.getDriver())
                    .maxPower(vehicle.getAbout().getMaxPower())
                    .fuelDurability(vehicle.getAbout().getFuelDurability())
                    .maxSpeed(vehicle.getAbout().getMaxSpeed())
                    .mph(vehicle.getAbout().getMph())
                    .color(vehicle.getAbout().getColor())
                    .image(vehicle.getImage())
                    .chargePerHour(vehicle.getChargePerHour())
                    .fuelType(vehicle.getAbout().getFuelType())
                    .gearType(vehicle.getAbout().getGearType())
                    .seatCount(vehicle.getAbout().getSeatCount())
                    .build();
            return vRegDto;
        }


        public static Vehicle mapVehicleDtoToVehicle(
                VehicleDto vehicleDto
        ) {
            Vehicle vehicle = Vehicle.builder()
                    .id(vehicleDto.getId())
                    .image(vehicleDto.getImageData())
                    .vehicleName(vehicleDto.getVehicleName())
                    .build();
            return vehicle;
        }

        public UserEntity extractUser(
                HttpServletRequest servletRequest
        ) {
            String authHeader = servletRequest.getHeader("Authorization");
            if (authHeader == null) {
                throw new UsernameNotFoundException("User must be logged In");
            }

            String jwt = authHeader.substring(7);
            String username = jwtService.extractUsername(jwt);

            UserEntity user = userRepository.findByEmailOrPhoneNumber(username, username);
            return user;
        }

        public static String mapLocationToAddress(Location location1) {
        String locationStreet = location1.getStreet();
        String locationDistrict = location1.getDistrict();
        String locationCity = location1.getCity();
        String address = locationStreet + "," + locationDistrict + "," + locationCity;
        return address;
    }

        public static DisplayInList mapVDtoToDisplayObject(
                List<VehicleDto> vehicleDto
        ) {
            DisplayInList displayObject = null;
            for (VehicleDto vehicleDto1 : vehicleDto) {
                displayObject = DisplayInList.builder()
                        .id(vehicleDto1.getId())
                        .vehicleName(vehicleDto1.getVehicleName())
                        .fuelType(vehicleDto1.getFuelType())
                        .gearType(vehicleDto1.getGearType())
                        .seatCount(vehicleDto1.getSeatCount())
                        .distanceBetweenUserAndDriver(vehicleDto1.getDistanceBetweenUserAndDriver())
                        .timeDifferenceBetweenUserAndDriver(vehicleDto1.getTimeDifferenceBetweenUserAndDriver())
                        .imageData(vehicleDto1.getImageData())
                        .build();
            }

            return displayObject;
        }

        public static DisplayInList mapVDtoToDisplayObject(
                VehicleDto vehicleDto1
        ) {
        DisplayInList displayObject =  DisplayInList.builder()
                    .id(vehicleDto1.getId())
                    .vehicleName(vehicleDto1.getVehicleName())
                    .fuelType(vehicleDto1.getFuelType())
                    .gearType(vehicleDto1.getGearType())
                    .seatCount(vehicleDto1.getSeatCount())
                    .distanceBetweenUserAndDriver(vehicleDto1.getDistanceBetweenUserAndDriver())
                    .timeDifferenceBetweenUserAndDriver(vehicleDto1.getTimeDifferenceBetweenUserAndDriver())
                    .imageData(vehicleDto1.getImageData())
                    .build();
                return displayObject;
    }

        public static VDIS mapVDtoToDisplayObject(Vehicle vehicleDto1) {
        VDIS displayObject =  VDIS.builder()
                    .id(vehicleDto1.getId())
                    .vehicleName(vehicleDto1.getVehicleName())
                    .imageData(vehicleDto1.getImage())
                    .build();
                return displayObject;
    }


}
