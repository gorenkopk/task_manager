package ua.edu.sumdu.j2se.gorenko.pavel.tasks.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Date;

/**
 * Task Class
 */
public class Task implements Cloneable, Serializable {

        private String title;
        private Date start;
        private Date end;
        private Date time;
        private int interval;
        private boolean active;
        private boolean repeated;

    public Task() {

    }

    public Task(String title, Date time) {
            this.title = title;
            this.setTime(time);
        }

    public Task(String title, Date start, Date end, int interval) {
            this.title = title;
            this.start = start;
            this.end = end;
            this.interval = interval;
            this.repeated = true;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public Date getTime() {
            return ((isRepeated()) ? start : time);
        }

        public void setTime(Date time) {
            try {
                if (time == null) {
                    throw new IOException("Invalid time value");
                } else {
                    this.time = time;
                    if (isRepeated()) {
                        this.repeated = false;
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public Date getStartTime() {
            return ((isRepeated()) ? start : time);
        }

        public Date getEndTime() {
            return ((isRepeated()) ? end : time);
        }

        public int getRepeatInterval() {
            return ((isRepeated()) ? interval : 0);
        }

        public void setTime(Date start, Date end, int interval) {
            try {
                if (interval < 1) {
                    throw new IOException("Inerval can't be 0 or less");
                } else {
                    this.repeated = ((isRepeated()) ? false : true);
                    this.start = start;
                    this.end = end;
                    this.interval = interval;
                }
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

        public boolean isRepeated() {
            return repeated;
        }

        public Date nextTimeAfter(Date current) {
            if (!isActive())
                return null;

            if (!isRepeated())
                return (current.before(time) ? time : null);

            if (current.before(start))
                return start;

            if (current.after(end))
                return null;

            long period = end.getTime() - start.getTime();
            long generalNumberOfIntervals = period / (interval * 1000);
            long numberOfIntervalsBeforeCurrent = (current.getTime() - start.getTime()) / (interval * 1000);

            if (current.getTime() >= (generalNumberOfIntervals * (interval * 1000) + start.getTime())) {
                return null;
            } else {
                return new Date(((numberOfIntervalsBeforeCurrent + 1) * (interval * 1000)) + start.getTime());
            }
        }

        @Override
        public String toString() {
            return String.format(" [title: %s, start: %ts, end: %ts, interval: %d, " +
                            "time: %ts, active: %s, repeated: %s]"
                    , title, start, end, interval, time, active, repeated);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Task task = (Task) o;
            return  time.equals(task.time) &&

                    active == task.active &&
                    repeated == task.repeated &&
                    Objects.equals(title, task.title);
        }

        @Override
        public int hashCode() {
            int hash = getTitle().hashCode() * 31 + getStartTime().hashCode() * 43
                    + getEndTime().hashCode() * 21 + getRepeatInterval() * 13;
            return hash;
        }

        public Task clone() throws CloneNotSupportedException {
            try {
                return (Task) super.clone();
            }
            catch (CloneNotSupportedException e) {
                System.out.println(e);
            }
            return null;
        }


}
