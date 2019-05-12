using Autofac.Features.AttributeFilters;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class OutputFileOut : IOutputPrinter, Autofac.IStartable
    {

        protected IDateFormatter formatter;
        protected TextWriter writer;

        public OutputFileOut(IDateFormatter formatter, TextWriter writer)
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
            Console.WriteLine("Started OutputFileOut");
        }
    }
}
