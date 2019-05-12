using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class DateFormatterLong : IDateFormatter
    {
        private string form;

        public DateFormatterLong(string sep)
        {
            this.form = "yyyy" + sep + "MM" + sep + "dd HH" + sep + "mm" + sep + "ss";

        }
        public string Format(DateTime date)
        {
            return date.ToString(form);
        }
    }
}
