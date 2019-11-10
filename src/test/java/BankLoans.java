import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
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
    public static void payInfo1(){
        // 输出入参
        logger.info("年利率:{}, 月利率:{}, 贷款本金:{}, 还款总月数:{} ", yearRate, monthRate, loansMoney, totalMonths);

        // 计算月供。即：每月本息金额  = (本金×月利率×(1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
        double monthPay = (loansMoney * monthRate * Math.pow(1 + monthRate, totalMonths)) / (Math.pow(1 + monthRate, totalMonths) - 1);
        logger.info("每月本息金额:{}", monthPay);

        // 月支付本金
        double monthCapital = 0;
        // 遍历 “还款总月数” 计算每个月的支付利息、本金
        for (int monthIndex = 1; monthIndex < totalMonths + 1; monthIndex++) {
            // 每月本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
            monthCapital = (loansMoney * monthRate * (Math.pow((1 + monthRate), monthIndex - 1))) / (Math.pow(1 + monthRate, totalMonths) - 1);
            //月利息/月收益 = 月本息 - 月本金
            double monthIncome = monthPay - monthCapital;
            logger.info("第" + monthIndex + "月, 本金:{}, 月利息:{}", monthCapital, monthIncome);
        }
    }

    public static void payInfo2(){
        // 输出入参
        logger.info("年利率:{}, 月利率:{}, 贷款本金:{}, 还款总月数:{} ", yearRate, monthRate, loansMoney, totalMonths);
        // 每月利息  = 剩余本金 x 月利率
        double monthInterest;
        // 月支付本金
        double monthPayCapital = 0;
        //总利息
        double totalInterest = 0;
        // 剩余 “贷款本金” 初次则为 “贷款本金”
        double remainBorrowMoney = loansMoney;
        // 遍历 “还款总月数” 计算每个月的支付利息、本金
        for (int index = 1; index < totalMonths + 1; index++) {
            // 剩余 “贷款本金” = 贷款本金 - 已还款支付本金
            remainBorrowMoney = remainBorrowMoney - monthPayCapital;
            // 每月利息  = 剩余本金 * 月利率
            monthInterest = remainBorrowMoney * monthRate;
            // 月支付本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
            monthPayCapital = (loansMoney * monthRate * (Math.pow((1 + monthRate), index - 1))) / (Math.pow(1 + monthRate, totalMonths) - 1);
            //输出每个月的 “本金”、“利息”、“本息”
            logger.info("第{}月本金:{} ,利息:{}, 月供=本金+利息:{}", index, monthPayCapital, monthInterest, (monthPayCapital+monthInterest));
            //累加每月利息
            totalInterest = totalInterest + monthInterest;
        }
        String rate = totalInterest / totalMonths * 12 / loansMoney * 100 + "%";
        logger.info("年利率:{}, 总利息:{}", rate, totalInterest);
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

    public static void main(String[] args) {
        // 首次还款日期【年月】
        LocalDate firstPayDate = Jdk8DateUtils.parseLocalDate("2018-12-01");
        //生成所有还款日期和对应的期数
        Map<LocalDate, Integer> allTermDateMap = getALLTermDateMap(firstPayDate);

        //提前还款信息。Map<key:date, value:money>
        Map<LocalDate, Double> repaymentMap = new LinkedHashMap(16){{
            //第一次提前还款的 “还款日期” 和 “金额”
            put(Jdk8DateUtils.parseLocalDate("2019-01-21"), 20_0000d);
        }};

        // 计算
        payInfo3(repaymentMap, allTermDateMap);
    }
    /**
     * @param repaymentMap 提前信息。key: date提前还款日期, value: money 提前还款的金额
     * @param allTermDateMap 所有期信息。key: date还款日期, value: 期数
     *
     */
    public static void payInfo3(Map<LocalDate, Double> repaymentMap, Map<LocalDate, Integer> allTermDateMap){
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
            // 暂无提前还款的记录
            if (repaymentMap == null || repaymentMap.size() <=0){
                // 正常还款，未提前还款
                // 每月利息  = 剩余本金 * 月利率
                monthInterest = remainBorrowMoney * monthRate;
                // 月支付本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
                monthPayCapital = (loansMoney * monthRate * (Math.pow((1 + monthRate), payTerm - 1))) / (Math.pow(1 + monthRate, totalMonths) - 1);
                //输出每个月的 “本金”、“利息”、“本息”
                logger.info("第{}月本金:{} ,利息:{}, 月供:{}", payTerm, monthPayCapital, monthInterest, (monthPayCapital + monthInterest));
                continue;
            }
            for (Map.Entry<LocalDate, Double> entry : repaymentMap.entrySet()) {
                // 根据repaymentDate计算出是哪个月份、总天数、过去天数、剩余天数
                LocalDate repaymentDate = entry.getKey();
                Double repaymentMoney = entry.getValue();
                //  如果年月相同则视为同期
                boolean isSameYearAndMonth = Jdk8DateUtils.isSameYearAndMonth(payDate, repaymentDate);
                if (!isSameYearAndMonth){
                    // TODO 正常还款
                    // 每月利息  = 剩余本金 * 月利率
                    monthInterest = remainBorrowMoney * monthRate;
                    // 月支付本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
                    monthPayCapital = (loansMoney * monthRate * (Math.pow((1 + monthRate), payTerm - 1))) / (Math.pow(1 + monthRate, totalMonths) - 1);
                    //输出每个月的 “本金”、“利息”、“本息”
                    double monthPay = monthPayCapital + monthInterest;
                    logger.info("第{}月,日期:{},月供:{},本金:{},利息:{}", payTerm, payDate, monthPay, monthPayCapital, monthInterest);
                    continue;
                }
                //提前还款。 根据repaymentDate计算出是哪个月份、总天数、过去天数、剩余天数
                logger.info("==========================提前还款====================================");
                //本月总天数
                int monthDays = Jdk8DateUtils.getMonthDays(repaymentDate);
                //本月过去了多少天
                int pastDays = Jdk8DateUtils.getCurrentDay(repaymentDate) -1;
//                int pastDays = Jdk8DateUtils.getCurrentDay(repaymentDate);
                // 本月剩余天数
                int remainDays = monthDays - pastDays;
                // 提前还款当月过去的天数产生的利息 = (每月利息 = 剩余本金 * 月利率) / 当月天数 * 本月过去的天数
                double monthFirstHalfInterest = remainBorrowMoney * monthRate / monthDays * pastDays;

                logger.info("==========================还款当月后的天数利息====================================");

                //剩余本金 = 剩余本金 - 提前还款额度
                remainBorrowMoney = remainBorrowMoney - repaymentMoney;
                //还款当月还款后的天产生的利息
                double monthLastHalfInterest = remainBorrowMoney * monthRate / monthDays * remainDays;
                //还款当月产生的利息和 = 还款当月前的天数利息 + 还款当月后的天数利息
                monthInterest = monthFirstHalfInterest + monthLastHalfInterest;

                // 月支付本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
                monthPayCapital = (loansMoney * monthRate * (Math.pow((1 + monthRate), payTerm - 1))) / (Math.pow(1 + monthRate, totalMonths) - 1);
                //输出每个月的 “本金”、“利息”、“本息”
                double monthPay = monthPayCapital + monthInterest;
                logger.info("第{}月,日期:{},月供:{},本金:{},利息:{}", payTerm, payDate, monthPay, monthPayCapital, monthInterest);
            }
        }
    }


    public static void main1(String[] args) {
        try {
//          payInfo1(loansMoney, totalMonths, yearRate, monthRate);
//          payInfo2(loansMoney, totalMonths, yearRate, monthRate);

            //测试
            int month = Jdk8DateUtils.getMonth(new Date());
            System.out.println(month);

            int monthDays = Jdk8DateUtils.getMonthDays(new Date());
            System.out.println(monthDays);

            int date = Jdk8DateUtils.getCurrentDay(new Date());
            System.out.println(date);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void execute2(){
//        double invest = 110_0000;     //贷款本金
//        double yearRate = 0.0588;   //年利率
//        double monthRate = yearRate / 12;   //月利率
//        int month = 360;  //还款月数
//
//        logger.info("本金-->" + invest + "   年利率--->" + yearRate * 100 + "%" + "  期限--->" + month + "个月"+ "  月利率--->" + monthRate);
//
//        // 每月本息金额(即月供)  = (本金×月利率×(1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
//        double monthIncome = (invest * monthRate * Math.pow(1 + monthRate, month)) / (Math.pow(1 + monthRate, month) - 1);
//        logger.info("每月本息金额 : " + monthIncome);
//        logger.info("---------------------------------------------------");
//
//        // 每月本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
//        double monthCapital = 0;
//        for (int i = 1; i < month + 1; i++) {
//            monthCapital = (invest * monthRate * (Math.pow((1 + monthRate), i - 1))) / (Math.pow(1 + monthRate, month) - 1);
//            logger.info("第" + i + "月本金： " + monthCapital + "; 利息："+ (monthIncome - monthCapital));
//        }
//        logger.info("---------------------------------------------------");
//
//
//        // 每月利息  = 剩余本金 x 贷款月利率
//        double monthInterest = 0;
//        double capital = invest;
//        double tmpCapital = 0;
//        double totalInterest = 0;
//        for (int i = 1; i < month + 1; i++) {
//            capital = capital - tmpCapital;
//            monthInterest = capital * monthRate;
//            tmpCapital = (invest * monthRate * (Math.pow((1 + monthRate), i - 1))) / (Math.pow(1 + monthRate, month) - 1);
//            logger.info("第" + i + "月本金： " + tmpCapital +"； 利息："+monthInterest + "; 本金+利息："+(tmpCapital+monthInterest));
//            totalInterest = totalInterest + monthInterest;
//        }
//
//        logger.info("-------------------------------------------------");
//        logger.info("总利息：--->" + totalInterest);
//
//        logger.info("-------------------------------------------------");
//        logger.info("年利率：--->" + totalInterest / month * 12 / invest * 100 + "%");
//
//
//    }
    /**
     * 获取每月本息金额
     * 计算方式
     * 每月本息金额  = (本金×月利率×(1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
     *
     * @param invest   本金
     * @param yearRate 年利率
     * @param month    还款月
     * @return 每月本息金额
     */
    public double getMonthIncome(double invest, double yearRate, int month) {
        double monthRate = yearRate / 12;   //月利率
        double monthIncome = (invest * monthRate * Math.pow(1 + monthRate, month)) / (Math.pow(1 + monthRate, month) - 1);
        return monthIncome;
    }


}
