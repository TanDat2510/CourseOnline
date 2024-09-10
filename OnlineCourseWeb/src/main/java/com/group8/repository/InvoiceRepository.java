/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.repository;

import com.group8.pojo.Invoice;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thang
 */
public interface InvoiceRepository {
    void addUpInvoice(Invoice invoice);

    Invoice checkrequestId(String RequestId);

    List<Invoice> getAllInvoice(Map<String, String> params);

    Invoice getInvoiceById(int id);
    
    Invoice calculateTotalAmount(int id);
}
