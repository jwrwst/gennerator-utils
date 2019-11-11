import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类描述：
 *
 * @author wang
 * 2019/11/8 12:51
 */
public class BankLoans {
    static Logger logger = LoggerFactory.getLogger(BankLoans.class);
    //还款总月数, 即 30年 * 12个月 = 360个月
    static int totalMonths = 360;
    //年利率
    static double yearRate = 5.88/100;
    //月利率
    static double monthRate = yearRate / 12;
    //贷款本金
    static double loansMoney = 110_0000;
    /**
     *  年利率：5.88%
     *  月利率：5.88% / 12 = 0.0049
     *  本金：110W
     *
     *  月支付本息(即月供) = (本金×月利率×(1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
     *  月支付本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
     *
     *  月利息 = 月本息 - 月本金
     *      或
     *  月利息 = 剩余本金 * 月利率
     */
    public static void main(String[] args) {
        // 首次还款日期【年月】
        LocalDate firstPayDate = Jdk8DateUtils.parseLocalDate("2018-12-01");
        //生成所有还款日期和对应的期数
        Map<LocalDate, Integer> allTermDateMap = getALLTermDateMap(firstPayDate);

        //提前还款信息。Map<key:date, value:money>
        Map<LocalDate, Double> repaymentMap = new LinkedHashMap(){{
            //第一次提前还款的 “还款日期” 和 “金额”。
            put(Jdk8DateUtils.parseLocalDate("2019-01-21"), 20_0000d);
            put(Jdk8DateUtils.parseLocalDate("2020-01-15"), 20_0000d);
        }};
        // 计算
        payInfo4(repaymentMap, allTermDateMap);
    }

    public static void payInfo4(Map<LocalDate, Double> repaymentMap, Map<LocalDate, Integer> allTermDateMap){
        // 输出入参
        logger.info("年利率:{}, 月利率:{}, 贷款本金:{}, 还款总月数:{} ", yearRate, monthRate, loansMoney, totalMonths);
        // 每月利息  = 剩余本金 x 月利率
        double monthInterest;
        // 月支付本金
        double monthPayCapital = 0;
        // 剩余 “贷款本金” 初次则为 “贷款本金”
        double remainBorrowMoney = loansMoney;

        for (Map.Entry<LocalDate, Integer> entryInfo : allTermDateMap.entrySet()) {
            // 剩余 “贷款本金” = 贷款本金 - 已还款支付本金
            remainBorrowMoney = remainBorrowMoney - monthPayCapital;
            // 获取还款日期和对应的期数
            LocalDate payDate = entryInfo.getKey();
            Integer payTerm = entryInfo.getValue();
            for (Map.Entry<LocalDate, Double> entry : repaymentMap.entrySet()) {
                // 根据repaymentDate计算出是哪个月份、总天数、过去天数、剩余天数
                LocalDate repaymentDate = entry.getKey();
                Double repaymentMoney = entry.getValue();
                // 提前还款日期的下个月计算还款月的信息
                LocalDate nextRepaymentDate = repaymentDate.plusMonths(1);
                // 如果年月相同则视为同期
                boolean isSameYearAndMonth = Jdk8DateUtils.isSameYearAndMonth(payDate, nextRepaymentDate);
                // 正常还款
                if (!isSameYearAndMonth){
                    // 每月利息  = 剩余本金 * 月利率
                    monthInterest = remainBorrowMoney * monthRate;
                    // 月供 = (贷款额度 * 月利率 * (1+月利率)^还款总月数) / ((1+月利率)^还款总月数 -1)
                    double monthPayMoney = (loansMoney * monthRate * (Math.pow((1 + monthRate), totalMonths))) / (Math.pow(1 + monthRate, totalMonths) -1);
                    // 月支付本金 = 月供 - 月利息
                    monthPayCapital = monthPayMoney - monthInterest;
                    logger.info("第{}月, 日期:{}, 月供:{}, 本金:{}, 利息:{}", payTerm, payDate, monthPayMoney, monthPayCapital, monthInterest);
                    continue;
                }
                logger.info("==========================提前还款====================================");
                // 总还款月份减少
                totalMonths = totalMonths - (payTerm-1);
                //本月总天数
                int monthDays = Jdk8DateUtils.getMonthDays(repaymentDate);
                //本月过去了多少天
                int pastDays = Jdk8DateUtils.getCurrentDay(repaymentDate) -1;
                // 本月剩余天数
                int remainDays = monthDays - pastDays;

                // 提前还款当月过去的天数产生的利息 = (每月利息 = 剩余本金 * 月利率) / 当月天数 * 本月过去的天数
                double monthHalfInterestBefore = (remainBorrowMoney * monthRate) / monthDays * pastDays;

                // 剩余本金 = 剩余本金 - 提前还款额度; 且贷款总额度减少
                loansMoney = remainBorrowMoney = remainBorrowMoney - repaymentMoney;
                // 还款当月还款后的天产生的利息
                double monthHalfInterestAfter = remainBorrowMoney * monthRate / monthDays * remainDays;
                // 还款当月产生的利息和 = 还款当月前的天数利息 + 还款当月后的天数利息
                monthInterest = monthHalfInterestBefore + monthHalfInterestAfter;

                // TODO 946.38 该计算公式算出来的钱少二十多块钱
                // 月支付本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
                monthPayCapital = (remainBorrowMoney * monthRate * (Math.pow((1 + monthRate), (payTerm - 1)))) / (Math.pow((1 + monthRate), totalMonths) - 1);

                //输出每个月的 “本金”、“利息”、“本息”
                double monthPay = monthPayCapital + monthInterest;
                logger.info("第{}月, 日期:{}, 月供:{}, 本金:{}, 利息:{}", payTerm, payDate, monthPay, monthPayCapital, monthInterest);
            }
        }
    }

    // map<key:年月，value:期数>
    public static Map<LocalDate, Integer> getALLTermDateMap(LocalDate firstPayDate){
        // map<key:期数，value:年月>
        Map<LocalDate, Integer> map = new LinkedHashMap<>(totalMonths);
        for (int index =1; index <= totalMonths; index ++){
            map.put(firstPayDate, index);
            firstPayDate = firstPayDate.plusMonths(1);
        }
        return map;
    }

    public static void execute2(){
        double invest = 110_0000;     //贷款本金
        double yearRate = 0.0588;   //年利率
        double monthRate = yearRate / 12;   //月利率
        int month = 360;  //还款月数

        logger.info("本金-->" + invest + "   年利率--->" + yearRate * 100 + "%" + "  期限--->" + month + "个月"+ "  月利率--->" + monthRate);

        // 每月本息金额(即月供)  = (本金×月利率×(1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
        double monthIncome = (invest * monthRate * Math.pow(1 + monthRate, month)) / (Math.pow(1 + monthRate, month) - 1);
        logger.info("每月本息金额 : " + monthIncome);
        logger.info("---------------------------------------------------");

        // 每月本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
        double monthCapital = 0;
        for (int i = 1; i < month + 1; i++) {
            monthCapital = (invest * monthRate * (Math.pow((1 + monthRate), i - 1))) / (Math.pow(1 + monthRate, month) - 1);
            logger.info("第" + i + "月本金： " + monthCapital + "; 利息："+ (monthIncome - monthCapital));
        }
        logger.info("---------------------------------------------------");


        // 每月利息  = 剩余本金 x 贷款月利率
        double monthInterest = 0;
        double capital = invest;
        double tmpCapital = 0;
        double totalInterest = 0;
        for (int i = 1; i < month + 1; i++) {
            capital = capital - tmpCapital;
            monthInterest = capital * monthRate;
            tmpCapital = (invest * monthRate * (Math.pow((1 + monthRate), i - 1))) / (Math.pow(1 + monthRate, month) - 1);
            logger.info("第" + i + "月本金： " + tmpCapital +"； 利息："+monthInterest + "; 本金+利息："+(tmpCapital+monthInterest));
            totalInterest = totalInterest + monthInterest;
        }

        logger.info("-------------------------------------------------");
        logger.info("总利息：--->" + totalInterest);

        logger.info("-------------------------------------------------");
        logger.info("年利率：--->" + totalInterest / month * 12 / invest * 100 + "%");


    }


}
