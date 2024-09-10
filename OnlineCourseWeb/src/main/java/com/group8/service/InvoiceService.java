/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service;

import com.group8.pojo.Invoice;
import java.util.Map;
import java.util.List;
import com.group8.dto.EnrollmentDTO;
import com.group8.dto.InvoiceStatsDTO;

/**
 *
 * @author thang
 * @author TAN DAT
 */
public interface InvoiceService {

    Invoice checkrequestId(String RequestId);

    void addUpInvoice(Invoice invoice);

    List<Invoice> getAllInvoice(Map<String, String> params);

    List<EnrollmentDTO> getInvoiceById(int id);

    List<InvoiceStatsDTO> calculateTotalAmount(int id);
}
