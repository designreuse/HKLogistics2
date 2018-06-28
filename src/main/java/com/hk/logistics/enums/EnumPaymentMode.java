package com.hk.logistics.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum EnumPaymentMode {
    ONLINE_PAYMENT(1000L, "Online Payment"),
    FREE_CHECKOUT(5L, "Free"),
    NEFT(20L, "NEFT"),
    ChequeDeposit(25L, "Cheque Deposit"),
    CashDeposit(30L, "Cash Deposit"),
    COD(40L, "COD"),
    COUNTER_CASH(50L, "Counter Cash"),
    SUBSCRIPTION_PAYMENT(95L, "Subscription Payment"),
    OFFLINE_CARD_PAYMENT(100L, "Offline Credit/Debit Card"),
    REWARD_POINT(110L, "Reward Points"),
    CREDIT(130L, "15 days credit"),
    SIS(140L, "SIS"),
    COD_TO_ONLINE(150L, "Flipped from COD to Online"),
    CARD_ON_DELIVERY(160L, "Card On Delivery"),
    PAY_AT_STORE(170L,"PAY AT STORE");

    private String name;
    private Long id;
    private Double reconciliationCharges;

    EnumPaymentMode(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static EnumPaymentMode getPaymentModeFromId(Long id) {
        for (EnumPaymentMode paymentMode : values()) {
            if (paymentMode.getId().equals(id)) return paymentMode;
        }
        return null;
    }

    public static List<EnumPaymentMode> getWorkingPaymentModes() {
        return Arrays.asList(EnumPaymentMode.CashDeposit,
                EnumPaymentMode.ChequeDeposit,
                EnumPaymentMode.ONLINE_PAYMENT,
                EnumPaymentMode.COD,
                EnumPaymentMode.CARD_ON_DELIVERY,
                EnumPaymentMode.COUNTER_CASH,
                EnumPaymentMode.FREE_CHECKOUT,
                EnumPaymentMode.NEFT,
                EnumPaymentMode.SUBSCRIPTION_PAYMENT,
                EnumPaymentMode.COD_TO_ONLINE,
                EnumPaymentMode.CREDIT,
                EnumPaymentMode.SIS,
                EnumPaymentMode.PAY_AT_STORE);
    }

    public static List<Long> getAuthorizationPendingPaymentModes() {
        return Arrays.asList(
                EnumPaymentMode.CashDeposit.getId(),
                EnumPaymentMode.ChequeDeposit.getId(),
                EnumPaymentMode.COUNTER_CASH.getId(),
                EnumPaymentMode.NEFT.getId()
        );
    }

    public static List<Long> getPrePaidPaymentModes() {
        return Arrays.asList(
                EnumPaymentMode.SUBSCRIPTION_PAYMENT.getId(),
                EnumPaymentMode.FREE_CHECKOUT.getId(),
                EnumPaymentMode.ONLINE_PAYMENT.getId(),
                EnumPaymentMode.COD_TO_ONLINE.getId(),
                EnumPaymentMode.PAY_AT_STORE.getId());
    }

    public static List<Long> getCodPaymentModes() {
      return Arrays.asList(EnumPaymentMode.COD.getId(),
          EnumPaymentMode.CARD_ON_DELIVERY.getId());
    }

    public static List<Long> getMagicPaymentModes() {
        return Arrays.asList(EnumPaymentMode.COD.getId(), EnumPaymentMode.ONLINE_PAYMENT.getId());
    }


    public static List<Long> getPaymentModeIDs(List<EnumPaymentMode> enumPaymentModes) {
        List<Long> paymenLModeIds = new ArrayList<Long>();
        for (EnumPaymentMode enumPaymentMode : enumPaymentModes) {
            paymenLModeIds.add(enumPaymentMode.getId());
        }
        return paymenLModeIds;
    }

    public static List<Long> getReconciliationModeIds() {
        return Arrays.asList(
                EnumPaymentMode.ONLINE_PAYMENT.getId(),
                EnumPaymentMode.COD_TO_ONLINE.getId()
        );
    }
}

