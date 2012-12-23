/////////////////////////////////////////////////////////////////////////////
//
// Project ProjectForge Community Edition
//         www.projectforge.org
//
// Copyright (C) 2001-2012 Kai Reinhard (k.reinhard@micromata.de)
//
// ProjectForge is dual-licensed.
//
// This community edition is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as published
// by the Free Software Foundation; version 3 of the License.
//
// This community edition is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
// Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, see http://www.gnu.org/licenses/.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import junit.framework.Assert;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.parameter.Value;

import org.junit.Test;
import org.projectforge.common.DateFormats;
import org.projectforge.common.DateHelper;
import org.projectforge.common.RecurrenceFrequency;

public class ICal4JUtilsTest
{

  @Test
  public void recurTests()
  {
    final TimeZone timeZone = DateHelper.EUROPE_BERLIN;
    final Recur recur = new Recur();
    recur.setFrequency(ICal4JUtils.getCal4JFrequencyString(RecurrenceFrequency.WEEKLY));
    recur.setUntil(getDate("2013-01-31", timeZone));
    recur.setInterval(2);
    final DateList dateList = recur.getDates(getDate("2013-01-01", timeZone),
        getDate("2012-01-02", timeZone), getDate("2013-03-31", timeZone), Value.TIME);
    Assert.assertEquals(3, dateList.size());
    final DateFormat df = new SimpleDateFormat(DateFormats.ISO_TIMESTAMP_MINUTES);
    df.setTimeZone(timeZone);
    Assert.assertEquals("2013-01-01 00:00", df.format(dateList.get(0)));
    Assert.assertEquals("2013-01-15 00:00", df.format(dateList.get(1)));
    Assert.assertEquals("2013-01-29 00:00", df.format(dateList.get(2)));
  }

  @Test
  public void testSqlDate()
  {
    final net.fortuna.ical4j.model.Date date = ICal4JUtils.getICal4jDate(DateHelper.parseIsoDate("2012-12-22", DateHelper.EUROPE_BERLIN),
        DateHelper.EUROPE_BERLIN);
    Assert.assertEquals("20121222", date.toString());
  }

  private net.fortuna.ical4j.model.Date getDate(final String dateString, final TimeZone timeZone)
  {
    final java.util.Date date = DateHelper.parseIsoDate(dateString, timeZone);
    return ICal4JUtils.getICal4jDate(date, timeZone);
  }
}
