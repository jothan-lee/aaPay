package com.per.aapay.entity;

public  class Equzlize {
        private String equxName;//谁
        private String beqUzName;//给谁
        private Double muchMoney;//给多少

        public String getEquxName() {
            return equxName;
        }

        public void setEquxName(String equxName) {
            this.equxName = equxName;
        }

        public String getBeqUzName() {
            return beqUzName;
        }

        public void setBeqUzName(String beqUzName) {
            this.beqUzName = beqUzName;
        }

        public Double getMuchMoney() {
            return muchMoney;
        }

        public void setMuchMoney(Double muchMoney) {
            this.muchMoney = muchMoney;
        }
        //第一组训练数据
        //   1      2        3      4         5
        //     3      3        4      2         0
        //               2.4
        //    1     2         3
        //    0.6   0.6      1.6

        //   4          5
        //  -0.4       -2.4
        //第二组训练数据
        //  1     2     3     4      5     6
        //  4     5     6     7      8     9
        //            6.5
        // -2.5 -1.5  -0.5   0.5     1.5  2.5

        /**
         1->give-->4:---->0.5
         1->give-->5:---->1.5
         1->give-->6:---->0.5
         2->give-->6:---->1.5
         3->give-->6:---->0.5
         */
    }