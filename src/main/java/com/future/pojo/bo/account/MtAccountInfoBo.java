package com.future.pojo.bo.account;

import lombok.Data;

@Data
public class MtAccountInfoBo {

        /*账户*/
        public int user;
        /*名称*/
        public String name;

        /*余额*/
        public double balance;
        /*信用*/
        public double credit;
        /*占用保障金*/
        public double margin;
        /*可用保障金*/
        public double freeMargin;
        /*净值*/
        public double equity;
        /*浮动盈亏*/
        public double profit;
        /*杠杆*/
        public int leverage;

    }