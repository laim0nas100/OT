using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class ConsoleAudit : IAudit
    {
        public void Audit(string msg)
        {
            Console.WriteLine(msg);
        }
    }
}
