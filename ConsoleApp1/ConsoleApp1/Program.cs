using Autofac;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            using(var scope = D.Context().BeginLifetimeScope())
            {
                for (int i = 0; i < 10; i++)
                {
                    scope.Resolve<IPrinterContainer>().GetDefaultPrinter().printDate(DateTime.Now);
                }

            }

            Console.WriteLine("After loop");



           
            Console.ReadKey();
        }
    }
}
