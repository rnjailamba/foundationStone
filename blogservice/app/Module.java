import com.cementify.blogservice.services.BlogService;
import com.cementify.blogservice.services.BlogServiceImp;
import com.cementify.blogservice.utils.MongoClientInstance;
import com.cementify.blogservice.utils.MongoHandler;
import com.google.inject.AbstractModule;
import play.Configuration;
import play.Environment;


/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {
    private final Environment environment;
    private final Configuration configuration;

    public Module(
            Environment environment,
            Configuration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }


    @Override
    public void configure() {
        bind(BlogService.class).to(BlogServiceImp.class);
        bind(MongoClientInstance.class).asEagerSingleton();
       // bind(MongoHandler.class).asEagerSingleton();
    }

}
