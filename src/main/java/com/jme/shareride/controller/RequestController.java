package com.jme.shareride.controller;

import com.jme.shareride.dto.ReviewRequest;
import com.jme.shareride.requests.ComplainRequest;
import com.jme.shareride.requests.auth.LocationUpdateRequest;
import com.jme.shareride.requests.chat.ChatRequest;
import com.jme.shareride.requests.contactus.ContactSupportRequest;
import com.jme.shareride.requests.transport.category.CategoryRequest;
import com.jme.shareride.requests.transport.others.DeliveryInfoRequest;
import com.jme.shareride.requests.transport.others.NotificationRequest;
import com.jme.shareride.requests.transport.rent.BookLaterRequest;
import com.jme.shareride.requests.transport.rent.RequestForRent;
import com.jme.shareride.requests.transport.vehicle.VehicleRegRequest;
import com.jme.shareride.service.OrderServices.OrderService;
import com.jme.shareride.service.WalletServices.WalletService;
import com.jme.shareride.service.cancelledservices.CancelService;
import com.jme.shareride.service.completeOrderservices.CompletedService;
import com.jme.shareride.service.categoryServices.CategoryService;
import com.jme.shareride.service.chatservices.ChatService;
import com.jme.shareride.service.chatservices.MessageService;
import com.jme.shareride.service.devileryinfoservices.DeliveryInfoService;
import com.jme.shareride.service.historyservcies.HistoryService;
import com.jme.shareride.service.notificationServices.NotificationService;
import com.jme.shareride.service.rentservices.RentService;
import com.jme.shareride.service.review.ReviewService;
import com.jme.shareride.service.review.complain.ComplainService;
import com.jme.shareride.service.transactionservices.TransactionService;
import com.jme.shareride.service.upcominingeventservices.UpcomingEventService;
import com.jme.shareride.service.userServices.UserService;
import com.jme.shareride.service.vehicleServices.VehicleService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RequestController {
    @Autowired
    private HttpServletRequest servletRequest;
   private final UserService userService;
   private final ComplainService complainService;
   private final NotificationService notificationService;
   private final ReviewService reviewService;
   private final VehicleService vehicleService;
   private final UpcomingEventService eventService;
   private final HistoryService historyService;
   private final DeliveryInfoService deliveryInfoService;
   private final CompletedService completedService;
//   private final AuthenticationManager authenticationManager;
   private final TransactionService transactionService;
   private final WalletService walletService;
   private final CancelService cancelService;
   private final MessageService messageService;
   private final ChatService chatService;

    private final RentService rentService;
    private final OrderService orderService;
   private final CategoryService categoryService;






    @PostMapping("/category")
    public ResponseEntity addCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.addCategory(servletRequest,categoryRequest);
    }

        @GetMapping("/ip")
        public String getUserIP() {
            String ipAddress = servletRequest.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = servletRequest.getRemoteAddr();
            }
            return ipAddress;
        }

    @PostMapping("/confirmLocation")
    public ResponseEntity saveDeliveryInfo(
            @RequestBody DeliveryInfoRequest infoRequest
    )
    {
        return deliveryInfoService.saveInfo(infoRequest, servletRequest);
    }

    @PostMapping("/become-driver")
    public ResponseEntity becomeDriver(){
        return userService.becomeDriver(servletRequest);
    }
    @PostMapping("/vehicle")
    public ResponseEntity addVehicle(
            @RequestBody VehicleRegRequest vRegRequest
    )
    {
        return vehicleService.addVehicle(servletRequest,vRegRequest);
    }


    @GetMapping("/all-vehicles")
    public ResponseEntity findAllVehicle(Pageable pageable){
        return vehicleService.findAllVehicles(pageable);
    }

    @GetMapping("/all-vehicles-by-category")
    public ResponseEntity findVehiclesByProduct(
            @RequestParam String category,
            Pageable pageable
    )
    {
        return  vehicleService.findVehicleByCategory(servletRequest, category,pageable);
    }


    @GetMapping("/{vehicleId}")
    public ResponseEntity getOneVehicle(
            @PathVariable long vehicleId
    )
    {
        return vehicleService.findVehicleById(vehicleId);
    }



     @PostMapping("/confirmBooking")
    public ResponseEntity confirmBooking(
            @RequestBody RequestForRent rentRequest
        ){
        return rentService.confirmBooking(servletRequest,rentRequest);
    }

    @PostMapping("/contactUs")
    public ResponseEntity contactSupport(
            @RequestBody ContactSupportRequest supportRequest
    ) throws MessagingException,
            UnsupportedEncodingException
    {
        return messageService.contactSupport(supportRequest);
    }

    @PostMapping("/review")
    public ResponseEntity reviewDriver(
            @RequestBody ReviewRequest reviewRequest
    ){
        return reviewService.reviewDriver(servletRequest, reviewRequest);
    }



    @PostMapping("/notify")
    public ResponseEntity notify(
            @RequestBody NotificationRequest notificationRequest
    ){
        return notificationService.sendNotification(notificationRequest);
    }

    @GetMapping("/notification")
    public ResponseEntity ReadNotification()
    {
        return notificationService.getNotification(servletRequest);
    }

    @PostMapping("/complain")
    public ResponseEntity lodgeComplain(
            @RequestBody ComplainRequest complainRequest
    ){
        return complainService.fileAComplain(servletRequest, complainRequest);
    }

    @PostMapping("/fundAccount")
    public ResponseEntity addFund(@RequestParam int amount){
        return walletService.addMoney(servletRequest, amount);
    }



    @PostMapping("/deductCharge")
    public ResponseEntity deductMoney(@RequestParam int amount){
        //todo editing required
        return walletService.deductMoney(servletRequest, amount);
    }

    @GetMapping("/transactions")
    public ResponseEntity allTransactions(){
        return transactionService.findAllUsersTransaction(servletRequest);
    }

    @GetMapping("/transaction")
    public ResponseEntity transactionDetails(@RequestParam long id){
        return transactionService.transactionDetails(id);
    }


    @GetMapping("/wallet")
    public ResponseEntity viewWallet(){
        return walletService.getUsersWallet(servletRequest);
    }

    @PostMapping("/confirm")
    public ResponseEntity confirmRide(
            @RequestParam int vat
    ){
        return orderService.confirmRide(servletRequest,vat);
    }

    @GetMapping("/balance")
    public int currentBalance(){
        return walletService.getBalance(servletRequest);
    }

    @PostMapping("/completeOrder")
    public ResponseEntity completedOrder(){
        return completedService.completeOrder(servletRequest);
    }

    @GetMapping("/completedOrderDetails")
    public ResponseEntity completedOrderDetails(@RequestParam("cpId") long completedOrdersId){
        return completedService.completedOrderDetails(completedOrdersId);
    }

    @GetMapping("/completedOrders")
    public ResponseEntity findCompletedOrder(){
        return completedService.findAllUsersCompletedOrders(servletRequest);
    }

    @PostMapping("/updateLocation")
    public ResponseEntity updateLocation(
            @RequestBody LocationUpdateRequest updateRequest
    ){
        return userService.updateLocation(servletRequest, updateRequest);
    }

    @DeleteMapping("/cancelOrder")
    public ResponseEntity cancelOrder(){
        return cancelService.cancelOrder(servletRequest);
    }

    @GetMapping("/cancelledOrders")
    public ResponseEntity getCanceledOrdered() {
        return cancelService.findAllCancelledOrders(servletRequest);
    }

    @GetMapping("/cancelledOrder")
    public ResponseEntity getCanceledOrderHistory(@RequestParam("cohId") long cancelOrderedHistoryId){
        return cancelService.cancelOrder(cancelOrderedHistoryId);
    }







    @PostMapping("/bookLater")
    public ResponseEntity bookLater(
            @RequestBody BookLaterRequest bookLaterRequest
    ){
        return eventService.bookLater(servletRequest, bookLaterRequest);
    }

    @GetMapping("/upcomingEvents")
    public ResponseEntity allEvents(){
        return eventService.allUpcomingEvents(servletRequest);
    }

    @GetMapping("/upcomingEvent")
    public ResponseEntity upComingEventDetails(@RequestParam long id){
        return eventService.eventDetails(id);
    }


    //todo test all endpoints



    @PostMapping("/send")
    public ResponseEntity sendMessage(
            @RequestBody ChatRequest chatRequest
    ){
        return messageService.sendMessage(chatRequest, servletRequest);
    }


    @PostMapping("/chat")
    public ResponseEntity chat(
            @RequestParam long recipientId)
    {
        return chatService.createChat(servletRequest, recipientId);
    }

    @GetMapping("/history")
    public ResponseEntity viewOrderHistory(){
        return historyService.viewHistory(servletRequest);
    }

}