using Autofac.Features.AttributeFilters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class PrinterContainerImpl : IPrinterContainer
    {

        private IOutputPrinter defaultPrinter;

        public PrinterContainerImpl([KeyFilter("std")]IOutputPrinter defaultPrinter)
        {
            this.defaultPrinter = defaultPrinter;
        }

        public IOutputPrinter GetDefaultPrinter()
        {
            return this.defaultPrinter;
        }
    }
}
