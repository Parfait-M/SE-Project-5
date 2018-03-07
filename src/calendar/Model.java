package calendar;

import java.time.*;
import java.util.*;

import se.reminder.Alarm;

import java.io.*;

class Model {

  private static interface Sort { public int s(Alarm a, Alarm b); }

  private static void sort(Alarm[] as, Sort s, int lo, int hi) {
    if (lo < hi) {
      Alarm part = as[hi];
      int p = lo;

      for (int i = lo; i < hi; ++i) {
        if (s.s(part,as[i]) >= 0) {
          Alarm t = as[i];
          as[i] = as[p];
          as[p] = t;
          ++p;
        }
      }

      Alarm t = as[hi];
      as[hi] = as[p];
      as[p] = t;

      sort(as,s,lo,p-1);
      sort(as,s,p+1,hi);
    }
  }

  public static Alarm[] sortByDateTime(Alarm[] as) {
    sort(as,
      (a,b) -> a.getDate_time().compareTo(b.getDate_time()),
      0,
      as.length-1);
    return as;
  }
  public static Alarm[] sortByTime(Alarm[] as) {
    sort(as,
      (a,b) -> a.getTime().compareTo(b.getTime()),
      0,
      as.length-1);
    return as;
  }
  public static Alarm[] sortByName(Alarm[] as) {
    sort(as,
      (a,b) -> a.getName().compareTo(b.getName()),
      0,
      as.length-1);
    return as;
  }

  public static ArrayList<Alarm> matchDate(ArrayList<Alarm> as, LocalDate d) {
    String ds = d.toString();
    ArrayList<Alarm> bs = new ArrayList<Alarm>();
    for (Alarm a : as) {
      if (a.getDate().toString().equals(ds)) {
        bs.add(a);
      }
    }
    return bs;
  }
  public static ArrayList<Alarm> matchMonth(ArrayList<Alarm> as, int m, int y) {
    ArrayList<Alarm> bs = new ArrayList<Alarm>();
    for (Alarm a : as) {
      if (m == a.getMonth() && y == a.getYear()) {
        bs.add(a);
      }
    }
    return bs;
  }
  public static ArrayList<Alarm> matchYear(ArrayList<Alarm> as, int y) {
    ArrayList<Alarm> bs = new ArrayList<Alarm>();
    for (Alarm a : as) {
      if (y == a.getYear()) {
        bs.add(a);
      }
    }
    return bs;
  }

  private static String randString(Random r) {
    int i = r.nextInt(26+26+5);
    if (i >= 26 + 26) return "";
    else if (0 <= i && i < 26) return randString(r) + (char)('a' + i);
    else return randString(r) + (char)('A' + i - 26);
  }
  private static Alarm[] rand(int l, Random r) {
    Alarm[] a = new Alarm[l];
    for (int i = 0; i < a.length; ++i) {
      String s = randString(r);
      int year = 1950 + r.nextInt(100);
      int month = 1 + r.nextInt(12);
      int day = 1 + r.nextInt(28);
      int hour = r.nextInt(23);
      int min = r.nextInt(59);

      a[i] = new Alarm(s, year, month, day, hour, min);
    }
    return a;
  }
  private static void print(Alarm[] a) {
    for (int i = 0; i < a.length; ++i) {
      System.out.printf("%s %s\n", a[i].getDate_time(), a[i].getName());
    }
  }

  public static void main(String[] a) {
    Random r = new Random();

    int l = Integer.parseInt(a[0]);

    Alarm[] array = rand(l,r);
    ArrayList<Alarm> list = new ArrayList<Alarm>(Arrays.asList(array));

    int year = 1950 + r.nextInt(100);
    int month = 1 + r.nextInt(12);
    int day = 1 + r.nextInt(28);
    LocalDate ld = LocalDate.of(year, month, day);

    switch(a[1]) {
    case "date":
      System.out.printf("Date: %s\n", ld);

      list = matchDate(list,ld);
      array = new Alarm[list.size()];
      list.toArray(array);
      break;

    case "month":
      System.out.printf("Year-Month: %d-%d\n", year, month);
      list = matchMonth(list,month,year);
      array = new Alarm[list.size()];
      list.toArray(array);
      break;

    case "year":
      System.out.printf("Year: %d\n", year);

      list = matchYear(list,year);
      array = new Alarm[list.size()];
      list.toArray(array);
      break;

    default: break;
    }

    switch(a[2]) {
      case "name": print(sortByName(array)); return;
      case "time": print(sortByTime(array)); return;
      case "date": print(sortByDateTime(array)); return;
      default: print(array); return;
    }
  }
}
