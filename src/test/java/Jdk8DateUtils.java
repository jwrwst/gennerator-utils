import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 类描述：
 *
 * @author wang
 * 2019/11/9 10:40
 */
public class Jdk8DateUtils implements Serializable {
    private static final long serialVersionUID = -7875756894714524316L;

    /**
     * 获取月月份
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        // 根据repaymentDate计算出是哪个月份、总天数、过去天数、剩余天数
        LocalDate localDate =  date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getMonthValue();
    }

    /**
     * 获取指定日期的当天
     * @param date
     * @return
     */
    public static int getCurrentDay(Date date) {
        // 根据repaymentDate计算出是哪个月份、总天数、过去天数、剩余天数
        LocalDate localDate =  date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfMonth();
    }

    /**
     * 获取指定日期的月总天数
     * @param date
     * @return
     */
    public static int getMonthDays(Date date) {
        // 根据repaymentDate计算出是哪个月份、总天数、过去天数、剩余天数
        LocalDate localDate =  date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monthLastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return monthLastDay.getDayOfMonth();
    }

    /**
     * date 转 LocalDate
     * @param date
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        java.util.Date date = Date.from(instant);
        return date;
    }



}
