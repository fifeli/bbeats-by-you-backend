package com.sentura.beatsbyyou.service;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.param.SubscriptionCancelParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    private Logger logger = LoggerFactory.getLogger(StripeService.class);

    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;

    public StripeService() {
    }

    public String createCustomer(String email, String token) {
        String id = null;
        try {
            Stripe.apiKey = stripeSecretKey;
            Map<String, Object> customerParams = new HashMap<>();
            // add customer unique id here to track them in your web application
            customerParams.put("description", "Customer for " + email);
            customerParams.put("email", email);

            customerParams.put("source", token); // ^ obtained with Stripe.js
            //create a new customer
            Customer customer = Customer.create(customerParams);
            id = customer.getId();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return id;
    }

    public String createSubscription(String customerId, String plan, String coupon) {
        String id = null;
        try {
            Stripe.apiKey = stripeSecretKey;
            Map<String, Object> item = new HashMap<>();
            item.put("plan", plan);

            Map<String, Object> items = new HashMap<>();
            items.put("0", item);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", customerId);
            params.put("items", items);

            //add coupon if available
            if (!coupon.isEmpty()) {
                params.put("coupon", coupon);
            }

            Subscription sub = Subscription.create(params);
            id = sub.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public boolean cancelSubscription(String subscriptionId) {
        boolean status;
        try {
            Stripe.apiKey = stripeSecretKey;
            Subscription sub = Subscription.retrieve(subscriptionId);
            sub.cancel((SubscriptionCancelParams) null);
            status = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            status = false;
        }
        return status;
    }

    public Coupon retrieveCoupon(String code) {
        try {
            Stripe.apiKey = stripeSecretKey;
            return Coupon.retrieve(code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String createCharge(String email, String token, int amount) {
        String id = null;
        try {
            Stripe.apiKey = stripeSecretKey;
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "usd");
            chargeParams.put("description", "Charge for " + email);
            chargeParams.put("source", token); // ^ obtained with Stripe.js

            //create a charge
            Charge charge = Charge.create(chargeParams);
            id = charge.getId();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return id;
    }
}