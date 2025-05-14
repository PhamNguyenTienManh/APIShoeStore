package com.example.demo.DTO.Response;


import java.time.LocalDateTime;

public class OrderHistoryResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double orderTotal;
    private String productName;
    private String image;
    private Integer quantity;
    private Integer size;
    private String commune;
    private String detailedAddress;
    private String district;
    private String province;

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public OrderHistoryResponse(Long orderId, LocalDateTime orderDate, Double orderTotal,
                                String productName, Integer quantity) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.productName = productName;
        this.quantity = quantity;
    }

    public OrderHistoryResponse(Long orderId, LocalDateTime orderDate, Double orderTotal, String productName, Integer quantity, String commune, String detailedAddress, String district, String province) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.productName = productName;
        this.quantity = quantity;
        this.commune = commune;
        this.detailedAddress = detailedAddress;
        this.district = district;
        this.province = province;
    }

    public OrderHistoryResponse(Long orderId, LocalDateTime orderDate, Double orderTotal,
                                String productName, String image, Integer quantity, Integer size,
                                String commune, String detailedAddress, String district, String province) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.productName = productName;
        this.image = image;
        this.quantity = quantity;
        this.size = size;
        this.commune = commune;
        this.detailedAddress = detailedAddress;
        this.district = district;
        this.province = province;
    }

    public OrderHistoryResponse(Long orderId, LocalDateTime orderDate, Double orderTotal,
                                String productName, Integer quantity, Integer size,
                                String commune, String detailedAddress, String district, String province) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.productName = productName;
        this.quantity = quantity;
        this.size = size;
        this.commune = commune;
        this.detailedAddress = detailedAddress;
        this.district = district;
        this.province = province;
    }

}
