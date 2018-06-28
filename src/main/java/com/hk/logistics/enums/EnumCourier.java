package com.hk.logistics.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum EnumCourier {

  DotZot_Express(10L, "DotZot Express"),
  DTDC_Lite(11L, "DTDC Lite"),
  DTDC_COD(12L, "DTDC COD"),
  DotZot_Economy(13L, "DotZot Economy"),
  BookMyPacket(20L, "Book My Packet"),
  Speedpost(30L, "Speed Post"),
  Delhivery(40L, "Delhivery"),
  Delhivery_Surface(41L, "Delhivery-Surface"),
  Chhotu(45L, "Chhotu-Delhi-NCR"),
  OfficePickup(50L, "Office Pickup"),
  ShipDelight(60L, "Shiclassp Delight"),
  BlueDart(70L, "Blue Dart"),
  BlueDart_COD(71L, "Blue Dart COD"),
  FirstFLight(80L, "First Flight"),
  FirstFLight_COD(85L, "First Flight COD"),
  Other(100L, "Other"),
  IndiaEarthMovers(110L, " India Earth Movers"),
  Safexpress_Chhotu(120L, " Safexpress - Chhotu"),
  Safexpress_Delhivery(130L, " Safexpress - Delhivery"),
  Safexpress(170L, " Safexpress"),
  Quantium(180L, "Quantium"),
  Smile_EExpress(190L, "Smile EExpress"),
  Smile_Express_DSP(195L, "Smile Express DSP"),
  EarthMoversPune(200L, "Earth Movers Pune"),
  IndiaOnTime(210L, "IndiaOn Time"),
  MuditaCargo(220L, "Mudita Cargo"),
  HK_Delivery(500L, "HealthKart Delivery"),
  FedEx(600L, "FedEx"),
  FedEx_Surface(610L, "FedEx Surface"),
  Gati(650L, "Gati"),
  Star_Track(660L, "Star Track"),
  Xpress_Logistics(661L, "Xpress Logistics"),
  JP_CargoVan_Delhi(662L, "JP Cargo Van - Delhi"),
  GoJavas(671L, "GoJavas"),
  BlueDart_Surface(674L, "BlueDart-Surface"),
  Grofers(673L, "Grofers"),
  MIGRATE(-1L, "MIGRATE"),
  HKL(677L, "HKL"),
  ECOM_EXPRESS(679L, "Ecom Express"),
  AJ_EXPRESS(680L, "AJ Express"),
  RED_EXPRESS(678L, "Red Express"),
  GOJAVAS_A(681L, "Gojavas A"),
  GOJAVAS_MP(682L, "Gojavas MP"),
  INNOVEX(687L, "Innovex"),
  HKREACH(689L, "HK-Reach"),
  Fedex_MP(690L, "Fedex MP"),
  BlueDart_MP(691L, "Blue Dart MP"),
  HKL_WH(692L, "HKL-WH"),
  Delhiveryy(693L, "Delhiveryy"),   //will be used only for WH and CFA orders (MP and B2C shipments will be catered by Delhivery MP)
  Delhiveryy_Surface(694L, "Delhiveryy-Surface"),
  Daakiyaa(698L, "Daakiyaa"),
  JV_Express(708L, "JV Express"),
  Holisol_Logistics(710L, "Holisol Logistics"),
  EKART(712L, "eKart"),
  Roadrunnr(714L, "Roadrunnr"),
  GoPigeon(719L, "GoPigeon"),
  Xpressbees(721L, "Xpressbees"),
  V_Xpress(724L, "V Xpress"),
  Paketts(725L,"Paketts"),
  Xpressbees_Marketplace(726L,"Xpressbees Marketplace"),
  SecuraEx(727L,"SecuraEx");

  private String name;
  private Long id;

  EnumCourier(Long id, String name) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public Long getId() {
    return id;
  }


  public static EnumCourier getEnumCourierFromCourierId(Long id) {
    for (EnumCourier enumCourier : values()) {
      if (enumCourier.getId().equals(id))
        return enumCourier;
    }
    return null;
  }

  public static List<Long> getCourierIDs(List<EnumCourier> enumCourierList) {
    List<Long> courierIds = new ArrayList<Long>();
    for (EnumCourier enumCourier : enumCourierList) {
      courierIds.add(enumCourier.getId());
    }
    return courierIds;
  }
  public static List<EnumCourier> getAllCouriers() {
    return Arrays.asList(values());
  }
  public static List<Long> getDTDCCouriers() {
    List<Long> dtdcCourierIds = new ArrayList<Long>();
    dtdcCourierIds.add(EnumCourier.DTDC_COD.getId());
    dtdcCourierIds.add(EnumCourier.DTDC_Lite.getId());
    dtdcCourierIds.add(EnumCourier.DotZot_Economy.getId());
    dtdcCourierIds.add(EnumCourier.DotZot_Express.getId());
    return dtdcCourierIds;
  }

  public static List<Long> getBlueDartCouriers() {
    List<Long> blueDartCourierIds = new ArrayList<Long>();
    blueDartCourierIds.add(EnumCourier.BlueDart.getId());
    return blueDartCourierIds;
  }

  public static List<Long> getDelhiveryCourierIds() {
    List<Long> delhiveryCourierIds = new ArrayList<Long>();
    delhiveryCourierIds.add(EnumCourier.Delhivery.getId());
    delhiveryCourierIds.add(EnumCourier.Delhivery_Surface.getId());
    return delhiveryCourierIds;
  }

  public static List<Long> getFedexCouriers() {
    List<Long> fedexCourierIds = new ArrayList<Long>();
    fedexCourierIds.add(EnumCourier.FedEx.getId());
    fedexCourierIds.add(EnumCourier.FedEx_Surface.getId());
    return fedexCourierIds;
  }

  public static List<Long> getShouldTrackCourierIds() {
    List<Long> courierIds = getDelhiveryCourierIds();
    courierIds.add(EnumCourier.BlueDart.getId());
    courierIds.add(EnumCourier.BlueDart_Surface.getId());
    courierIds.add(EnumCourier.GoJavas.getId());
    return courierIds;
  }

  public static List<Long> getLiquidDisableCourierIds() {
    List<Long> liquidDisableCourierIds = new ArrayList<Long>();
    liquidDisableCourierIds.add(EnumCourier.GoJavas.getId());
    liquidDisableCourierIds.add(EnumCourier.FedEx.getId());
    liquidDisableCourierIds.add(EnumCourier.ECOM_EXPRESS.getId());
    liquidDisableCourierIds.add(EnumCourier.AJ_EXPRESS.getId());
    liquidDisableCourierIds.add(EnumCourier.Gati.getId());

    return liquidDisableCourierIds;
  }

  public static List<Long> getHKFulfilledCourierIds() {
    return Arrays.asList(HKL.getId(), HKL_WH.getId());
  }

}

