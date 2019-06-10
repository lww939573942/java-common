package cn.company.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author donaldhan
 * 汉语拼音工具类
 */
public class PinYinUtils {
    private final static Logger logger = LoggerFactory.getLogger(PinYinUtils.class);

    /**
     * 获取汉语拼音大写字符串
     *
     * @param pinyinString 汉语字符
     *
     * @return 汉语拼音大写字符串
     *
     * @throws BadHanyuPinyinOutputFormatCombination 拼音异常
     */
    public static String getPinYinUppercase(String pinyinString) throws BadHanyuPinyinOutputFormatCombination {
        chineseStringCheck(pinyinString);

        /*这里需要添加一个分号作为结尾，不然语句最后两个字会合并到一起，比如：陕西省，转换以后就是SHAN,XISHENG*/
        pinyinString = pinyinString + ";";

        HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
        pyFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String hanYuPinyin = PinyinHelper.toHanYuPinyinString(pinyinString, pyFormat, ",", false);
        if (hanYuPinyin.endsWith(",")) {
            hanYuPinyin = hanYuPinyin.substring(0, hanYuPinyin.length() - 1);
        }

        return hanYuPinyin;
    }

    /**
     * 获取汉语拼音大写首字母
     *
     * @param pinyinString 汉语字符
     *
     * @return 汉语拼音大写首字母
     *
     * @throws BadHanyuPinyinOutputFormatCombination 拼音异常
     */
    public static String getPinYinFirstLetters(String pinyinString) throws BadHanyuPinyinOutputFormatCombination {
        chineseStringCheck(pinyinString);

        /*这里需要添加一个分号作为结尾，不然语句最后两个字会合并到一起，比如：陕西省，转换以后就是SHAN,XISHENG*/
        pinyinString = pinyinString + ";";

        StringBuilder stringBuilder = new StringBuilder();
        HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
        pyFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] strings = PinyinHelper.toHanYuPinyinString(pinyinString, pyFormat, ",", false).split(",");
        Arrays.stream(strings).forEach(obj -> {
            stringBuilder.append(obj.substring(0, 1)).append(",");
        });

        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }

    /**
     * 检查是否含有中文字符
     *
     * @param pinyinString 待检查字符
     *
     * @return 是否含有中文字符
     */
    private static void chineseStringCheck(String pinyinString) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(pinyinString);
        if (!matcher.find()) {
            logger.error("汉语拼音转换异常,{},没有中文字符", pinyinString);
            throw new RuntimeException("汉语拼音转换异常," + pinyinString + ",中不含有中文字符");
        }
    }
}