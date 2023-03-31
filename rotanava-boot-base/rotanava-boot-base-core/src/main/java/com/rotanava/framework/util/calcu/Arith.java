package com.rotanava.framework.util.calcu;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
 * 确的浮点数运算，包括加减乘除和四舍五入。
 * <p>
 * BigDecimal.ROUND_UP 最后一位如果大于0，则向前进一位，正负数都如此。
 * BigDecimal.ROUND_DOWN 最后一位不管是什么都会被舍弃。
 * BigDecimal.ROUND_CEILING 如果是正数，按ROUND_UP处理，如果是负数，按照ROUND_DOWN处理。例如7.1->8; -7.1->-7;所以这种近似的结果都会>=实际值。
 * BigDecimal.ROUND_FLOOR 跟BigDecimal_ROUND_CEILING相反。例如7.1->7;-7.1->-8。这种处理的结果<=实际值。
 * BigDecimal.ROUND_HALF_DOWN 如果最后一位<=5则舍弃，如果>5， 向前进一位。如7.5->7;7.6->8;-7.5->-7
 * BigDecimal.ROUND_HALF_UP 如果最后一位<5则舍弃，如果>=5， 向前进一位。反之舍弃。如7.5->8;7.4->7;-7.5->-8
 * BigDecimal.ROUND_HALF_EVEN 如果倒数第二位是奇数，按照BigDecimal.ROUND_HALF_UP处理，如果是偶数，按照BigDecimal.ROUND_HALF_DOWN来处理。如7.5->8;8.5->8;7.4->7;-7.5->-8
 */
public class Arith {
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;
    private static final String pattern = "###,###.##";
    private static final DecimalFormat df = new DecimalFormat(pattern);

    //这个类不能实例化
    private Arith() {
    }


    public static boolean isInt(double a)
    {
        double b = a;
        int b1 = (int)a;
        if(b % b1 == 0)
            return true;
        else
            return false;
    }


    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static BigDecimal add2(BigDecimal v1, BigDecimal v2) {
        return v1.add(v2);
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static BigDecimal sub2(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
//        Collections.shuffle();
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，BigDecimal.ROUND_DOWN 最后一位不管是什么都会被舍弃。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double abs(double v1, double v2) {
        return abs(v1, v2, DEF_DIV_SCALE);
    }


    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，BigDecimal.ROUND_DOWN 最后一位不管是什么都会被舍弃。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double abs(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 格式化：最多显示两位小数，没有小数则不显示
     *
     * @param value
     * @return
     */
    public static String format(double value) {
        return df.format(value);
    }


}