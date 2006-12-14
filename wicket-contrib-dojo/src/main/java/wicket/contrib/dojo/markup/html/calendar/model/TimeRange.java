/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.dojo.markup.html.calendar.model;

import java.io.Serializable;
import java.text.NumberFormat;

public class TimeRange implements Serializable, Comparable<TimeRange> {
	/* offsets in seconds since 00:00 */
    /* time in seconde */
    private int startTime = -1;
    private int endTime = -1;
    private int day = -1;
    
    private static final int SUNDAY = 0;
    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 3;
    private static final int THURSDAY = 4;
    private static final int FRIDAY = 5;
    private static final int SATURDAY = 6;
    
    public TimeRange() {
    }
    
    public TimeRange(int startTime, int endTime) {
    	this.startTime = startTime;
    	this.endTime = endTime;
    }
    
    public int getStartTime() {
        return startTime;
    }
    
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    
    public int getEndTime() {
        return endTime;
    }
    
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    
    public void setStartTimeHour(int hour) {
        int min;
        if (startTime == -1)
            min = 0;
        else
            min = this.startTime % 3600;
        this.startTime = (hour % 24) * 3600 + min;
    }
    
    public void setStartTimeMin(int min) {
        int hour;
        if (startTime == -1)
            hour = 0;
        else
            hour = (this.startTime / 3600) * 3600;
        this.startTime = hour + (min % 60) * 60;
    }
    
    public void setEndTimeHour(int hour) {
        int min;
        if (endTime == -1)
            min = 0;
        else
            min = this.endTime % 3600;
        this.endTime = (hour % 24) * 3600 + min;
    }
    
    public void setEndTimeMin(int min) {
        int hour;
        if (endTime == -1)
            hour = 0;
        else
            hour = (this.endTime / 3600) * 3600;
        this.endTime = hour + (min % 60) * 60;
    }
    
    public int getStartTimeHour() {
        if (startTime == -1)
            return -1;
        return this.startTime / 3600;
    }
    
    public int getStartTimeMin() {
        if (startTime == -1)
            return -1;
        int min = this.startTime % 3600;
        return min / 60;
    }
    
    public int getEndTimeHour() {
        if (endTime == -1)
            return -1;
        return this.endTime /3600;
    }
    
    public int getEndTimeMin() {
        if (endTime == -1)
            return -1;
        int min = this.endTime % 3600;
        return min /60;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TimeRange otherTimeRange= (TimeRange) o;
        return hashCode() == otherTimeRange.hashCode();
    }

    @Override
    public int hashCode() {
        int result;
        result = new Integer(startTime).hashCode();
        result = 29 * result + new Integer(endTime).hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return "[from " + format(getStartTimeHour())
            + ":" + format(getStartTimeMin())
            + " to " + format(getEndTimeHour())
            + ":" + format(getEndTimeMin()) + "]";
    }

    /** Comparaison just using the startTime */
    public int compareTo(TimeRange o) {
        if(day < o.day){
        	return -1;
        }else if (day > o.day){
        	return 1;
        }else{
	    	if(startTime < o.startTime) {
	            return -1;
	        } else if(startTime > o.startTime) {
	            return 1;
	        } else {
	            return 0;
	        }
        }
    }
    
    public static String format(Integer value) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumIntegerDigits(2);
        nf.setMinimumIntegerDigits(2);
        return nf.format(value);
    }

    public boolean intersect(TimeRange range) {
        
        /* The same day */
        if(this.startTime <= this.endTime) {
            /* The same day */
            if(range.startTime <= range.endTime) {
                if(this.startTime <= range.startTime && range.startTime <= this.endTime) {
                    return true;
                }
                if(range.startTime <= this.startTime && this.startTime <= range.endTime) {
                    return true;
                }
            } else { // between two days
                if(range.startTime <= this.endTime || this.startTime <= range.endTime) {
                    return true;
                }
            }
        } else { // between two days
            /* The same day */
            if(range.startTime <= range.endTime) {
                if(this.startTime <= range.endTime || range.startTime <= this.endTime) {
                    return true;
                }
            } else { // between two days
                return true;
            }
        }
        return false;
    }

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

}
