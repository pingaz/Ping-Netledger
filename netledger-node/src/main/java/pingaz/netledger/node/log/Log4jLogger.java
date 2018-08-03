package pingaz.netledger.node.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.net.URI;

/**
 * @author ping
 */
public class Log4jLogger implements Logger{
    private final org.apache.logging.log4j.Logger log;

    public Log4jLogger(String name){
        this.log = org.apache.logging.log4j.LogManager.getLogger(name);
    }

    @Override
    public void debug(String msg) {
        log.debug(msg);
    }

    @Override
    public void debug(String msg, Object... params) {
        log.debug(String.format(msg, params));
    }

    @Override
    public void info(String msg) {
        log.info(msg);
    }

    @Override
    public void info(String msg, Object... params) {
        log.info(String.format(msg, params));
    }

    @Override
    public boolean isDebug() {
        return log.isDebugEnabled();
    }

    @Override
    public void error(String msg, Throwable throwable) {
        log.error(msg, throwable);
    }

    @Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
    @Order(50)
    public static class CustomConfigurationFactory extends ConfigurationFactory {

        static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
            builder.setConfigurationName(name);
            builder.setStatusLevel(Level.ERROR);
            builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).
                    addAttribute("level", Level.DEBUG));
            AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", "CONSOLE").
                    addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
            appenderBuilder.add(builder.newLayout("PatternLayout").
                    addAttribute("pattern", "%d %-5level %logger[%L] - %msg%n%throwable"));
            appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
                    Filter.Result.NEUTRAL).addAttribute("marker", "FLOW"));
            builder.add(appenderBuilder);
            builder.add(builder.newLogger("org.apache.logging.log4j", Level.DEBUG).
                    add(builder.newAppenderRef("Stdout")).
                    addAttribute("additivity", false));
            builder.add(builder.newRootLogger(Level.ERROR).add(builder.newAppenderRef("Stdout")));
            return builder.build();
        }

        @Override
        public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
            return getConfiguration(loggerContext, source.toString(), null);
        }

        @Override
        public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
            ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
            return createConfiguration(name, builder);
        }

        @Override
        protected String[] getSupportedTypes() {
            return new String[] {"*"};
        }
    }
}
