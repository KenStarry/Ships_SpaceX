package com.example.shipsspacex;

//  Modal class for storing our data
public class ShipsModalClass {

    private String ship_status;
    private String ship_name;
    private String ship_type;
    private String ship_image;

    //  Constructor
    public ShipsModalClass(String ship_status, String ship_name, String ship_type, String ship_image) {
        this.ship_status = ship_status;
        this.ship_name = ship_name;
        this.ship_type = ship_type;
        this.ship_image = ship_image;
    }

    //  Getters and setters
    public String getShip_status() {
        return ship_status;
    }

    public void setShip_status(String ship_status) {
        this.ship_status = ship_status;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public String getShip_type() {
        return ship_type;
    }

    public void setShip_type(String ship_type) {
        this.ship_type = ship_type;
    }

    public String getShip_image() {
        return ship_image;
    }

    public void setShip_image(String ship_image) {
        this.ship_image = ship_image;
    }
}
