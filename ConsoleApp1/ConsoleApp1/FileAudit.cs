using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class FileAudit : IAudit,IDisposable
    {
        private System.IO.TextWriter writer;
        public FileAudit(string fileUrl)
        {
            writer = new System.IO.StreamWriter(fileUrl,true,Encoding.UTF8);
        }

        public void Audit(string msg)
        {
            writer.WriteLine(msg);
            writer.Flush();
        }

        public void Dispose()
        {
            writer.Flush();
            writer.Close();
            Console.WriteLine("IN DISPOSE");
        }
    }
}
