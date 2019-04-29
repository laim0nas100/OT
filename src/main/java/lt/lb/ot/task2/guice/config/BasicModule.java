/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.ot.task2.guice.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lt.lb.commons.Log;
import lt.lb.commons.Log.LogStream;
import lt.lb.ot.task2.guice.config.impl.DateFormatterLong;
import lt.lb.ot.task2.guice.config.impl.DateFormatterShort;
import lt.lb.ot.task2.guice.config.impl.OutputFileOut;
import lt.lb.ot.task2.guice.config.impl.OutputSTDOut;
import lt.lb.ot.task2.guice.config.impl.PrinterContainerImpl;

/**
 *
 * @author Lemmin
 */
public class BasicModule extends AbstractModule {

    private static Matcher<TypeLiteral<?>> typeMatcher(Class cls) {
        return new AbstractMatcher<TypeLiteral<?>>() {
            @Override
            public boolean matches(TypeLiteral<?> t) {
                return cls.equals(t.getRawType());
            }
        };
    }

    @Override
    protected void configure() {

        
        this.bindConstant().annotatedWith(Names.named("dateSep")).to("-");
        this.bind(Log.class).annotatedWith(Names.named("logFile")).toProvider(() -> {
            Log log = new Log();
            log.display = false;
            try {
                Log.changeStream(log, LogStream.FILE, "log.txt");
            } catch (IOException ex) {
                this.addError(ex);
            }
            return log;
        }).asEagerSingleton();
        this.bind(Log.class).annotatedWith(Names.named("logStd")).toProvider(() -> {
            Log log = new Log();
            return log;
        }).asEagerSingleton();

        this.bind(OutputPrinter.class).annotatedWith(Names.named("std")).to(OutputSTDOut.class).asEagerSingleton();
        this.bind(OutputPrinter.class).annotatedWith(Names.named("file")).to(OutputFileOut.class).asEagerSingleton();

        //guice does not support lifecycle management, so no shutdown hook
        //init hax
        this.bindListener(typeMatcher(OutputFileOut.class), new TypeListener() {
            @Override
            public <I> void hear(final TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
                typeEncounter.register(new InjectionListener<I>() {
                    @Override
                    public void afterInjection(Object i) {
                        OutputFileOut m = (OutputFileOut) i;
                        m.init();
                    }
                });
            }
        });

        this.bindListener(typeMatcher(OutputSTDOut.class), new TypeListener() {
            @Override
            public <I> void hear(final TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
                typeEncounter.register(new InjectionListener<I>() {
                    @Override
                    public void afterInjection(Object i) {
                        OutputSTDOut m = (OutputSTDOut) i;
                        m.init();
                    }
                });
            }
        });
        super.configure();
    }

    @Provides
    @Named("short")
    public static DateFormatter dateFormatterShort() {
        return new DateFormatterShort();
    }

    @Provides
    @Singleton
    @Named("long")
    public static DateFormatter dateFormatterLong() {
        return new DateFormatterLong("-");
    }

}
