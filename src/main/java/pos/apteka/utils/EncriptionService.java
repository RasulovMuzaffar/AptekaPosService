package pos.apteka.utils;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import pos.apteka.model.Client;
import pos.apteka.model.Extension;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EncriptionService {
    private String getMD5(String st) {
        String md5Hex = DigestUtils.md5Hex(st);

        return md5Hex;
    }

    private String getSHA512(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
//        return DigestUtils.sha512Hex(str + sdf.format(new Date()));
        return DigestUtils.sha512Hex(str);
    }

    public String getMD(String st) {
        return getMD5(st);
    }

    public String getModificationSHA512(Client client, Extension ext) {

        System.out.println(client.getName() + " , " + ext.getStartDate());
        String s = getModifSha(client.getName(), ext.getStartDate());
        String mwd = getModifWithDays(s, ext.getDays());
        return mwd;
        //////////////////////////////

//        char[] chars = getSHA512(client.getName()).toCharArray();
//        char[] dStr = convertTimestampToStr(ext.getStartDate()).toCharArray();
//        char[] days = ("" + ext.getDays()).toCharArray();
//
//        System.out.println("getSHA512 " + getSHA512(client.getName()));
//        System.out.println("convertTimestampToStr " + convertTimestampToStr(ext.getStartDate()));
//
//        StringBuilder sb = new StringBuilder();
//
//        System.out.println(chars.length + " " + dStr.length);
//
//        int i = 0, k = 0;
//        for (char c : chars) {
//            if (i % 20 == 0 && i != 0) {
//                sb.append(dStr[k]);
//                k++;
//            }
//            i++;
//            sb.append(c);
//        }
//
//        StringBuilder sb2 = new StringBuilder();
//        i = 0;
//        k = 0;
//        for (char c : chars) {
//            if (i % days.length * 10 == 0 && i != 0 && i < days.length) {
//                sb2.append(days[k]);
//                k++;
//            }
//            i++;
//            sb2.append(c);
//        }
//        sb.append("с" + days.length);
//        return sb.toString();
    }

    private String convertTimestampToStr(Timestamp t) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        return sdf.format(t);
    }

    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        return sdf.format(new Date());
    }

    private String getModifSha(String str, Timestamp startDate) {
        char[] charsS = getSHA512(str).toCharArray();
        char[] charsD = convertTimestampToStr(startDate).toCharArray();
        int j = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charsS.length; i++) {
            if (i % 20 == 0 && i != 0 && j < charsD.length) {
                sb.append(String.valueOf(charsD[j]));
                j++;
            }
            sb.append(String.valueOf(charsS[i]));
        }
        return sb.toString();
    }

    private String getModifWithDays(String s, int d) {
        char[] charsS = s.toCharArray();
        char[] charsD = String.valueOf(d).toCharArray();
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < charsS.length; i++) {
            if (i % (charsD.length * 10) == 0 && i != 0 && j < charsD.length) {
                sb.append(charsD[j]);
                j++;
            }
            sb.append(charsS[i]);
        }
        sb.append("с" + charsD.length);
        return sb.toString();
    }
}
