package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;


public class Util {
    private static final String connectionURL = "jdbc:mysql://localhost:3306/mysql?useSSL=false&serverTimezone=UTC";
    private static final String passwordUser = "Matrix%1989";
    private static final String userName = "root";
    private static final Environment env = null;
    private static SessionFactory newSession;

    static  {
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(env.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(env.URL, connectionURL);
            settings.put(env.USER, userName);
            settings.put(env.PASS, passwordUser);
            settings.put(env.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(env.SHOW_SQL, "true");
            settings.put(env.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            newSession = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return newSession;
    }
}

