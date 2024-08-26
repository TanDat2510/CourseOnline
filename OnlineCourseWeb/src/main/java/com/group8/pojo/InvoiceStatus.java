/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.pojo;

/**
 *
 * @author TAN DAT
 */
public enum InvoiceStatus {
    NOT_YET,
    PAID;
    
    @Override
    public String toString() {
        return name(); // Đảm bảo rằng `name()` trả về tên của enum
    }
    
}
