using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    interface IOutputPrinter
    {

        void printDouble(double d);

        void printInt(int i);

        void printString(string str);

        void printDate(DateTime date);
    }
}
