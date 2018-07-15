package io.pozhidaev.lomonosov.domain;

import lombok.*;

import java.util.List;

@Builder
public class RentUrlQuery extends AbstractUrlQuery{

    private Long areaId;
    private String city;
    private Integer homeTypeId;
    private Float fromRooms;
    private Float untilRooms;
    private Float fromPrice;
    private Float untilPrice;
    private Integer priceType;
    private Float fromFloor;
    private Float toFloor;
    private String enterDate;
    private String info;
    private Integer page;
    private Integer priceOnly;
    private Integer imgOnly;

    RentUrlQuery(Long areaId, String city, Integer homeTypeId, Float fromRooms, Float untilRooms, Float fromPrice, Float untilPrice, Integer priceType, Float fromFloor, Float toFloor, String enterDate, String info, Integer page, Integer priceOnly, Integer imgOnly) {
        this.areaId = areaId;
        this.city = city;
        this.homeTypeId = homeTypeId;
        this.fromRooms = fromRooms;
        this.untilRooms = untilRooms;
        this.fromPrice = fromPrice;
        this.untilPrice = untilPrice;
        this.priceType = priceType;
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.enterDate = enterDate;
        this.info = info;
        this.page = page;
        this.priceOnly = priceOnly;
        this.imgOnly = imgOnly;
        init();
    }

    private void init() {
        this.path = "Nadlan/rent.php";
        this.nodes = List.of(
                new UrlQueryNode(false, "AreaID", areaId),
                new UrlQueryNode(false, "City", city),
                new UrlQueryNode(false, "HomeTypeID", homeTypeId),
                new UrlQueryNode(false, "fromRooms", fromRooms),
                new UrlQueryNode(false, "untilRooms", untilRooms),
                new UrlQueryNode(false, "fromPrice", fromPrice),
                new UrlQueryNode(false, "untilPrice", untilPrice),
                new UrlQueryNode(false, "PriceType", priceType),
                new UrlQueryNode(false, "FromFloor", fromFloor),
                new UrlQueryNode(false, "ToFloor", toFloor),
                new UrlQueryNode(false, "EnterDate", enterDate),
                new UrlQueryNode(false, "Info", info),
                new UrlQueryNode(true, "Page", page),
                new UrlQueryNode(true, "PriceOnly", priceOnly),
                new UrlQueryNode(true, "ImgOnly", imgOnly)
        );
    }
}
