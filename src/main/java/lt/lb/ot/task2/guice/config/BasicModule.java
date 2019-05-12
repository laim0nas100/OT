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
import com.google.inject.util.Providers;
import java.io.IOException;
import lt.lb.commons.F;
import lt.lb.commons.Log;
import lt.lb.commons.Log.LogStream;
import lt.lb.ot.task2.guice.config.impl.DateFormatterLong;
import lt.lb.ot.task2.guice.config.impl.DateFormatterShort;
import lt.lb.ot.task2.guice.config.impl.OutputFileOut;
import lt.lb.ot.task2.guice.config.impl.OutputSTDOut;

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

        this.bind(OutputPrinter.class).annotatedWith(Names.named("std")).to(OutputSTDOut.class).asEagerSingleton();

        this.install(new LogModule(Names.named("fileShort")) {
            @Override
            void bindConcreteLog() {
                OutputFileOut printer = new OutputFileOut();
                try {
                    printer.setLog(BasicModule.logFile());
                } catch (IOException ex) {
                    this.addError(ex);
                }
                printer.setDateFormat(BasicModule.dateFormatterShort());
                bind(OutputPrinter.class).toProvider(Providers.of(printer)); // guice auto-injects otherwise
            }
        });

        this.install(new LogModule(Names.named("fileLong")) {
            @Override
            void bindConcreteLog() {
                OutputFileOut printer = new OutputFileOut();

                F.unsafeRunWithHandler(this::addError, () -> {
                    printer.setLog(BasicModule.logFile());
                });
                printer.setDateFormat(BasicModule.dateFormatterLong());
                bind(OutputPrinter.class).toProvider(Providers.of(printer));  // guice auto-injects otherwise
            }
        });

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

    @Provides
    @Singleton
    @Named("logFile")
    public static Log logFile() throws IOException {
        Log log = new Log();
        log.display = false;
        Log.changeStream(log, LogStream.FILE, "log.txt");
        return log;
    }

    @Provides
    @Singleton
    @Named("logSTD")
    public static Log logSTD() throws IOException {
        Log log = new Log();
        Log.changeStream(log, LogStream.STD_OUT);
        return log;
    }

}
