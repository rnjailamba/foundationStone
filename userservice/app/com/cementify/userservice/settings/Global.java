package com.cementify.userservice.settings;

/**
 * Created by roshan on 19/01/16.
 */

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;

import java.lang.reflect.Method;

public class Global extends GlobalSettings {

    public void onStart(Application app) {
        Logger.info("Application has started");
    }

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

    public Action onRequest(Http.Request request, Method actionMethod) {
        Logger.info("before each request..." + request.toString());
        return super.onRequest(request, actionMethod);
    }

}

