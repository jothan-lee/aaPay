package com.per.aapay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.per.aapay.entity.Equzlize;
import com.per.aapay.entity.Receipt;

public class AAPayApplication {

    public static void main(String[] args) {
        // string name, double money
        Receipt receipt1 = new Receipt("亮亮", 0);
        Receipt receipt2 = new Receipt("瑞鹏", 1218);
        Receipt receipt3 = new Receipt("依琳", 0);
        Receipt receipt4 = new Receipt("靖宇", 556);
        //        Receipt receipt5 = new Receipt("梦醒", 0);
        Receipt receipt6 = new Receipt("园松", 241);
        Receipt receipt7 = new Receipt("思雨", 0);

        //调整加入map的顺序可以选择支付方和被支付方的匹配
        List<Receipt> receipts = Arrays.asList(receipt2, receipt3, receipt4, receipt1, receipt6, receipt7);
        List<String> nameList = receipts.stream().map(Receipt::getName).collect(Collectors.toList());
        List<Equzlize> list = getEquzlizeList(receipts, nameList);
        System.out.println("转账方案：");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(
                    list.get(i).getEquxName() + " 支付-->" + list.get(i).getBeqUzName() + " 金额:" + list.get(i)
                            .getMuchMoney() + " 元");
        }
    }


    public static List<Equzlize> getEquzlizeList(List<Receipt> reports,
                                                                              List<String> nameList) {
        List<Equzlize> list = new ArrayList<>();
        //全部金额
        float allNum = 0;
        //key-姓名，value-金额
        Map<String, Double> userToAllMoney = new HashMap<>();
        for (Receipt report : reports) {
            //全部金额
            allNum = allNum + (float) report.getMoney();
            //把各自的花销金额存入map
            if (userToAllMoney.containsKey(report.getName())) {
                userToAllMoney.put(report.getName(), userToAllMoney.get(report) + report.getMoney());
            } else {
                userToAllMoney.put(report.getName(), report.getMoney());
            }
        }

        userToAllMoney.forEach((k, v) -> {
            if (v != 0) {
                System.out.println(k + "公共花销 = " + v + "元");
            }
        });
        //        System.out.println("                               ");
        System.out.println("公共花销总计 = " + allNum + "元");
        float avg = allNum / nameList.size();
        System.out.println("人均花销 = " + allNum + "元/" + userToAllMoney.size() + "人 = " + avg + "元/人");
        Map<String, Double> heightAvgMap = new LinkedHashMap<>();//insert
        Map<String, Double> underAvgMap = new ConcurrentHashMap<>();//out
        for (int i = 0; i < nameList.size(); i++) {
            double d_value = userToAllMoney.get(nameList.get(i)) - avg;
            if (d_value > 0) {
                heightAvgMap.put(nameList.get(i), d_value);
            } else {
                underAvgMap.put(nameList.get(i), d_value);
            }
        }
        for (Map.Entry<String, Double> entry : heightAvgMap.entrySet()) {
            //            System.out.println("entry = " + entry);
            double heightAvg = entry.getValue();
            double d_money = entry.getValue();
            for (Map.Entry<String, Double> dentry : underAvgMap.entrySet()) {
                heightAvg = heightAvg - Math.abs(dentry.getValue());
                if (heightAvg < 0) {
                    //补的人高
                    Equzlize equzlize = new Equzlize();
                    equzlize.setEquxName(dentry.getKey());
                    equzlize.setBeqUzName(entry.getKey());
                    //dentry->entry all
                    equzlize.setMuchMoney(Math.abs(d_money));
                    underAvgMap.put(dentry.getKey(), underAvgMap.get(dentry.getKey()) + Math.abs(d_money));
                    list.add(equzlize);
                    break;
                } else {
                    //补的人低
                    Equzlize equzlize = new Equzlize();
                    equzlize.setEquxName(dentry.getKey());
                    equzlize.setBeqUzName(entry.getKey());
                    //dentry->entry little
                    d_money = heightAvg;
                    equzlize.setMuchMoney(Math.abs(dentry.getValue()));
                    list.add(equzlize);
                    underAvgMap.remove(dentry.getKey());
                }
            }
        }
        return list;
    }

}
