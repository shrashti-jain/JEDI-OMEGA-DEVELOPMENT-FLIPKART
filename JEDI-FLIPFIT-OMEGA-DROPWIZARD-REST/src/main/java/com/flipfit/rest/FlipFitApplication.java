package com.flipfit.rest;

import com.flipfit.restControllers.AdminController;
import com.flipfit.restControllers.GymOwnerController;
import com.flipfit.restControllers.UserController;
import io.dropwizard.core.Application;
import io.dropwizard.core.Configuration;
import io.dropwizard.core.setup.Environment;
import com.flipfit.restControllers.GymCustomerController;
// Import other controllers as you create them

public class FlipFitApplication extends Application<FlipFitConfiguration> {

    public static void main(String[] args) throws Exception {
        // This starts the server
        new FlipFitApplication().run(args);
    }

    @Override
    public void run(FlipFitConfiguration configuration, Environment environment) {
        environment.jersey().register(new GymOwnerController());
        environment.jersey().register(new AdminController());
        environment.jersey().register(new UserController());
        // Register your REST controllers here
        environment.jersey().register(new GymCustomerController());

        // As you create more controllers, add them here:
        // environment.jersey().register(new GymOwnerController());
        // environment.jersey().register(new AdminController());
    }
}