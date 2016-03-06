package controllers;

import play.mvc.*;

import views.html.*;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import javax.inject.Inject;

public class Application extends Controller {

    @Inject
    MailerClient mailerClient;

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result ping(){
        String cid = "1234";
        Email email = new Email()
                .setSubject("Simple email")
                .setFrom("roshan kumar FROM <roshan@cementify.com>")
                .addTo("rnjai lamba TO <rnjai@cementify.com>")
                .setBodyText("A text message")
                .setBodyHtml("<html><body><p>An <b>html</b> message with cid <img src=\"cid:" + cid + "\"></p></body></html>");
        mailerClient.send(email);
        return ok("hello");
    }
}
