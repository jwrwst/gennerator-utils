import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类描述：
 *
 * @author wang
 * 2019/11/8 12:51
 */
public class Test {
    static Logger logger = LoggerFactory.getLogger(Test.class);
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
    public static void payInfo(double totalBorrowMoney, int totalMonths, double yearRate, double monthRate){
        // 输出入参
        logger.info("年利率:{}, 月利率:{}, 贷款本金:{}, 还款总月数:{} ", yearRate, monthRate, totalBorrowMoney, totalMonths);

        // 计算月供。即：每月本息金额  = (本金×月利率×(1＋月利率)＾还款月数)÷ ((1＋月利率)＾还款月数-1))
        double monthPay = (totalBorrowMoney * monthRate * Math.pow(1 + monthRate, totalMonths)) / (Math.pow(1 + monthRate, totalMonths) - 1);
        logger.info("每月本息金额:{}", monthPay);

        // 月支付本金
        double monthCapital = 0;
        // 遍历 “还款总月数” 计算每个月的支付利息、本金
        for (int monthIndex = 1; monthIndex < totalMonths + 1; monthIndex++) {
            // 每月本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
            monthCapital = (totalBorrowMoney * monthRate * (Math.pow((1 + monthRate), monthIndex - 1))) / (Math.pow(1 + monthRate, totalMonths) - 1);
            //月利息/月收益 = 月本息 - 月本金
            double monthIncome = monthPay - monthCapital;
            logger.info("第" + monthIndex + "月, 本金:{}, 月利息:{}", monthCapital, monthIncome);
        }
        logger.info("---------------------------------------------------");


        // 每月利息  = 剩余本金 x 月利率
        double monthInterest;
        // 月支付本金
        double monthPayCapital = 0;
        //总利息
        double totalInterest = 0;
        // 剩余 “贷款本金” 初次则为 “贷款本金”
        double remainBorrowMoney = totalBorrowMoney;
        // 遍历 “还款总月数” 计算每个月的支付利息、本金
        for (int index = 1; index < totalMonths + 1; index++) {
            // 剩余 “贷款本金” = 贷款本金 - 已还款支付本金
            remainBorrowMoney = remainBorrowMoney - monthPayCapital;
            // 每月利息  = 剩余本金 * 月利率
            monthInterest = remainBorrowMoney * monthRate;
            // 月支付本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
            monthPayCapital = (totalBorrowMoney * monthRate * (Math.pow((1 + monthRate), index - 1))) / (Math.pow(1 + monthRate, totalMonths) - 1);
            //输出每个月的 “本金”、“利息”、“本息”
            logger.info("第{}月本金:{} ,利息:{}, 月供=本金+利息:{}", index, monthPayCapital, monthInterest, (monthPayCapital+monthInterest));
            //累加每月利息
            totalInterest = totalInterest + monthInterest;
        }
        String rate = totalInterest / totalMonths * 12 / totalBorrowMoney * 100 + "%";
        logger.info("年利率:{}, 总利息:{}", rate, totalInterest);
    }



    public static void main(String[] args) {
        //年利率
        double yearRate = 0.0588;
        //月利率
        double monthRate = yearRate / 12;
        //还款总月数, 即 30年 * 12个月 = 360个月
        int totalMonths = 360;
        //贷款本金
        double loansMoney = 110_0000;
        payInfo(loansMoney, totalMonths, yearRate, monthRate);
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
