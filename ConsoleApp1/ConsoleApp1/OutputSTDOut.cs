using Autofac;
using Autofac.Features.AttributeFilters;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class OutputSTDOut : IOutputPrinter, IStartable

    {

        protected IDateFormatter formatter;
        protected TextWriter writer;

        public OutputSTDOut([KeyFilter("long")] IDateFormatter formatter,[KeyFilter("stdLog")] TextWriter writer)
        {
            this.formatter = formatter;
            this.writer = writer;
        }

        public void printDate(DateTime date)
        {

            writer.WriteLine(formatter.Format(date));
        }

        public void printDouble(double d)
        {
            writer.WriteLine(d);
        }

        public void printInt(int i)
        {
            writer.WriteLine(i);
        }

        public void printString(string str)
        {
            writer.WriteLine(str);
        }

        public void Start()
        {
            Console.WriteLine("Started OutputSTDOut");
        }
    }
}
