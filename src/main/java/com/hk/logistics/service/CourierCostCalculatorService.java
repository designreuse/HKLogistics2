package com.hk.logistics.service;

import com.hk.logistics.domain.Awb;
import com.hk.logistics.domain.Courier;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CourierCostCalculatorService {

	List<Courier> getBestAvailableCourierList(String destinationPincode, String sourcePincode, boolean cod, Long srcWarehouse,
                                              Double amount, Double weight, boolean ground, boolean cardOnDelivery, String channel, String vendor, String productVariantId, String store, LocalDate orderPlacedDate, Boolean isHKFulfilled);

	Map<Courier, Awb> getCourierAwbMap(String destinationPincode, String sourcePincode, Long warehouse,
                                       boolean groundShipped, boolean cod, Double amount, Double weight, String vendorCode,
                                       LocalDate orderPlacedDate, String channel, String variantId, Long storeId, Boolean isHkFulfilled);

}
