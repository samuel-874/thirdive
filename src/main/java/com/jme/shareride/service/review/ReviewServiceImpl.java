package com.jme.shareride.service.review;

import com.jme.shareride.Others.ResponseHandler;
import com.jme.shareride.dto.ReviewRequest;
import com.jme.shareride.entity.user_and_auth.review.Rating;
import com.jme.shareride.entity.user_and_auth.review.Review;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import com.jme.shareride.external.jwt.JwtService;
import com.jme.shareride.service.userServices.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            JwtService jwtService,
            UserRepository userRepository,
            RatingRepository ratingRepository) {
        this.reviewRepository = reviewRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public ResponseEntity reviewDriver(
            HttpServletRequest request,
            ReviewRequest reviewRequest
    ) {
        UserEntity customer = extractUser(request);
        UserEntity driver =
                userRepository.findById(reviewRequest.getDriversId()).get();
        //creating a new review object
        Review review = new Review();
        review.setUser(customer);
        review.setDriver(driver);
        review.setUserRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        reviewRepository.save(review);

        //getting or creating a new rating object for driver and increasing the totalCount
        Rating rating = ratingRepository.findByDriver(driver).orElseGet(()-> createNewRateObject(driver));
        rating.setDriver(driver);

        if(reviewRequest.getRating() == 1){
            rating.setOneStar(rating.getOneStar() + 1);
        }
        else if(reviewRequest.getRating() == 2){
            rating.setTwoStars(rating.getTwoStars() + 1);
        }
        else if(reviewRequest.getRating() == 3){
            rating.setThreeStars(rating.getThreeStars() + 1);
        }
          else if(reviewRequest.getRating() == 4){
            rating.setFourStars(rating.getFourStars() + 1);
        }
          else if(reviewRequest.getRating() == 5){
            rating.setFiveStars(rating.getFiveStars() + 1);
        }
          int averageRating = (rating.getOneStar() + (rating.getTwoStars() * 2) +(rating.getThreeStars() * 3) + (rating.getFourStars() *4) +(rating.getFiveStars() * 5))/ reviewRepository.findByDriver(driver).size();
            rating.setAverageRate(averageRating);
        ratingRepository.save(rating);

        return ResponseHandler.handle(201, "Thanks for rating", null);
    }

    public ResponseEntity reviewDriver(
            long userId,
            ReviewRequest reviewRequest
    ) {
        UserEntity customer =
                userRepository.findById(userId).get();
        UserEntity driver =
                userRepository.findById(
                        reviewRequest.getDriversId()).get();
        //creating a new review object
        Review review = new Review();
        review.setUser(customer);
        review.setDriver(driver);
        review.setUserRating(
                reviewRequest.getRating());
        review.setComment(
                reviewRequest.getComment());
        reviewRepository.save(review);

        //getting or creating a new rating object for driver and increasing the totalCount
        Rating rating =
                ratingRepository.findByDriver(driver).orElseGet(
                        ()-> createNewRateObject(driver));
        rating.setDriver(driver);

        if(reviewRequest.getRating() == 1){
            rating.setOneStar(rating.getOneStar() + 1);
        }
        else if(reviewRequest.getRating() == 2){
            rating.setTwoStars(rating.getTwoStars() + 1);
        }
        else if(reviewRequest.getRating() == 3){
            rating.setThreeStars(rating.getThreeStars() + 1);
        }
          else if(reviewRequest.getRating() == 4){
            rating.setFourStars(rating.getFourStars() + 1);
        }
          else if(reviewRequest.getRating() == 5){
            rating.setFiveStars(rating.getFiveStars() + 1);
        }


          int averageRating = (
                    rating.getOneStar()
                  + (rating.getTwoStars() * 2)
                  +(rating.getThreeStars() * 3)
                  + (rating.getFourStars() *4)
                  +(rating.getFiveStars() * 5))
                  / reviewRepository.findByDriver(driver).size();
            rating.setAverageRate(averageRating);
        ratingRepository.save(rating);

        return ResponseHandler.handle(
                201,
                "Thanks for rating",
                null);
    }

    private Rating createNewRateObject(
            UserEntity user){
        Rating rating = new Rating();
        rating.setDriver(user);
        return ratingRepository.save(rating);
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
}
