using Autofac;
using Autofac.Core;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class D
    {
        private static bool initDone = false;

        private static IContainer container;

        private static IContainer Init()
        {
            if (initDone)
            {
                return container;
            }
            ContainerBuilder builder = new ContainerBuilder();

            builder.Register(c => Console.Out).Keyed<TextWriter>("stdLog").AsSelf().SingleInstance();
            builder.Register(c =>
            {
                var stream = new StreamWriter(@"output1.txt", true, Encoding.UTF8);
                stream.AutoFlush = true;
                return stream;
            }).Keyed<TextWriter>("fileLog").AsSelf().SingleInstance();

            builder.RegisterType<OutputSTDOut>().As<IOutputPrinter>().OnActivating(c => c.Instance.Start()).Keyed<IOutputPrinter>("std");
            builder.RegisterType<OutputFileOut>().As<IOutputPrinter>().OnActivating(c => c.Instance.Start()).Keyed<IOutputPrinter>("file")
                .WithParameter(new ResolvedParameter(
                (p, c) => p.Position == 0 && p.ParameterType == typeof(IDateFormatter),
                (p, c) => c.ResolveKeyed<IDateFormatter>("short")
            ))
                .WithParameter(new ResolvedParameter(
                (p, c) => p.Position == 1 && p.ParameterType == typeof(TextWriter),
                (p, c) => c.ResolveKeyed<TextWriter>("fileLog")
            ));

            builder.RegisterType<OutputFileOut>().As<IOutputPrinter>().OnActivating(c => c.Instance.Start()).Keyed<IOutputPrinter>("fileLong")
                .WithParameter(new ResolvedParameter(
                (p, c) => p.Position == 0 && p.ParameterType == typeof(IDateFormatter),
                (p, c) => c.ResolveKeyed<IDateFormatter>("long")
            ))
                .WithParameter(new ResolvedParameter(
                (p, c) => p.Position == 1 && p.ParameterType == typeof(TextWriter),
                (p, c) => c.ResolveKeyed<TextWriter>("fileLog")
            ));

            builder.RegisterType<OutputFileOut>().As<IOutputPrinter>().OnActivating(c => c.Instance.Start()).Keyed<IOutputPrinter>("fileShort")
                .WithParameter(new ResolvedParameter(
                (p, c) => p.Position == 0 && p.ParameterType == typeof(IDateFormatter),
                (p, c) => c.ResolveKeyed<IDateFormatter>("short")
            ))
                .WithParameter(new ResolvedParameter(
                (p, c) => p.Position == 1 && p.ParameterType == typeof(TextWriter),
                (p, c) => c.ResolveKeyed<TextWriter>("fileLog")
            ));



            builder.RegisterType<DateFormatterShort>().As<IDateFormatter>().Keyed<IDateFormatter>("short");
            builder.RegisterType<DateFormatterLong>().As<IDateFormatter>().Keyed<IDateFormatter>("long").WithParameter(new TypedParameter(typeof(string), "-"));

            builder.RegisterType<PrinterContainerImpl>().As<IPrinterContainer>();


            D.container = builder.Build();
            initDone = true;
            return container;
        }

        public static IContainer Context()
        {
            return Init();
        }

    }
}
