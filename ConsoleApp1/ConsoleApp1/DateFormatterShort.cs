using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class DateFormatterShort : IDateFormatter
    {
        public string Format(DateTime date)
        {
            return date.ToString("yyyy-MM-dd");
        }
    }
}
