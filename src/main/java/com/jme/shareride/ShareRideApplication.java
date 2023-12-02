package com.jme.shareride;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jme.shareride.entity.transport.UpcomingEvent;
import com.jme.shareride.mail.MailSenderService;
import com.jme.shareride.requests.contactus.ContactSupportRequest;
import com.jme.shareride.service.review.ReviewServiceImpl;
import com.jme.shareride.service.upcominingeventservices.UpcomingEventService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@AllArgsConstructor
public class ShareRideApplication {

	private final MailSenderService mailSenderService;
	private final ReviewServiceImpl reviewService;

	public static void main(String[] args) throws UnknownHostException {

		SpringApplication.run(ShareRideApplication.class, args);

	}

		@Bean
		public FirebaseMessaging fireBaseMessaging()throws IOException{
			GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
					new ClassPathResource("share-ride-339ed-firebase-adminsdk-e4xn5-14c14b8169.json").getInputStream());
			FirebaseOptions firebaseOptions = FirebaseOptions.builder()
					.setCredentials(googleCredentials).build();
			FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions,"Share Ride");

			return FirebaseMessaging.getInstance(firebaseApp);
		}









//	@EventListener(ApplicationReadyEvent.class)
//	void testMail() throws UnsupportedEncodingException //, MessagingException
//	{
//		ContactSupportRequest contactSupportRequest = new ContactSupportRequest("samuel","abiosamuel666@gmail.com","09154065907","Hi! i have been hearing about Ride Share and i just want to make enquiry if its true that you company is better than Uber!");
//		mailSenderService.sendContactEmail(contactSupportRequest);
//	}


//		@EventListener(ApplicationReadyEvent.class)
//		void testRating(){
//			ReviewRequest reviewRequest = new ReviewRequest();
//			reviewRequest.setDriversId(2);
//			reviewRequest.setComment("Bad driver! but clean car nice");
//			reviewRequest.setRating(4);
//
//			reviewService.reviewDriver(1, reviewRequest);
//
//		}

	//		@EventListener(ApplicationReadyEvent.class)

}
