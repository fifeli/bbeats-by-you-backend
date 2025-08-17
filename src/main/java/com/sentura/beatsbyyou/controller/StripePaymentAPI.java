package com.sentura.beatsbyyou.controller;

import com.sentura.beatsbyyou.dto.ResponseDto;
import com.sentura.beatsbyyou.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment/stripe")
public class StripePaymentAPI {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-charge")
    @ResponseBody
    public ResponseDto createCharge(String email, String token, int amount) {
        //validate data
        if (token == null) {
            return new ResponseDto("Stripe payment token is missing. Please, try again later.","500",null);
        }

        //create charge
        String chargeId = stripeService.createCharge(email, token, amount); //$9.99 USD
        if (chargeId == null) {
            return new ResponseDto("An error occurred while trying to create a charge. this happened when already use the token","500",null);
        }

        // You may want to store charge id along with order information

        return new ResponseDto("Success! " ,chargeId);
    }
}
