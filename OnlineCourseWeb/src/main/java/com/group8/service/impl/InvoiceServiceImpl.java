/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.service.impl;

import com.group8.dto.EnrollmentDTO;
import com.group8.dto.InvoiceStatsDTO;
import com.group8.pojo.Enrollment;

import com.group8.pojo.Invoice;
import com.group8.repository.InvoiceRepository;
import com.group8.service.InvoiceService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TAN DAT
 */
@Service("InvoiceDetailService")
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Override
    public List<Invoice> getAllInvoice(Map<String, String> params) {
        return this.invoiceRepo.getAllInvoice(params);
    }


    @Override
    public List<EnrollmentDTO> getInvoiceById(int id) {
        Invoice invoice = this.invoiceRepo.getInvoiceById(id);

        // Chuyển đổi PersistentSet thành List
        List<Enrollment> enrollments = new ArrayList<>(invoice.getEnrollmentSet());

        List<EnrollmentDTO> enrollmentDTOs = new ArrayList<>();
        

        for (Enrollment enrollment : enrollments) {
            EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
            enrollmentDTO.setId(enrollment.getId());
            enrollmentDTO.setReferenceCode(invoice.getReferenceCode());
            enrollmentDTO.setPayerName(invoice.getPayerName());
            enrollmentDTO.setPayerEmail(invoice.getPayerEmail());
            enrollmentDTO.setUserId(enrollment.getUserId());
            enrollmentDTO.setFirstName(enrollment.getUserId().getFirstName());
            enrollmentDTO.setPayerPhone(invoice.getPayerPhone());
            enrollmentDTO.setLastName(enrollment.getUserId().getLastName());
            enrollmentDTO.setCourseId(enrollment.getCourseId());
            enrollmentDTO.setTitle(enrollment.getCourseId().getTitle());
            enrollmentDTO.setPrice(enrollment.getCourseId().getPrice());
            enrollmentDTO.setCreatedDate(new Date());
            enrollmentDTO.setUpdatedDate(new Date());
            enrollmentDTOs.add(enrollmentDTO);
        }


        return enrollmentDTOs;
    }

    @Override
    public Invoice checkrequestId(String RequestId) {
        return this.invoiceRepo.checkrequestId(RequestId);
    }

    @Override
    public void addUpInvoice(Invoice invoice) {
        this.invoiceRepo.addUpInvoice(invoice);
    }

    @Override
    public List<InvoiceStatsDTO> calculateTotalAmount(int id) {
        Invoice invoice = this.invoiceRepo.calculateTotalAmount(id);

        // Chuyển đổi PersistentSet thành List
        List<Enrollment> enrollments = new ArrayList<>(invoice.getEnrollmentSet());

        List<InvoiceStatsDTO> invoiceStatsDTOs = new ArrayList<>();
        

        double totalAmount = 0;
        for (Enrollment enrollment : enrollments) {
            
            totalAmount += enrollment.getCourseId().getPrice();
            InvoiceStatsDTO invoiceStatsDTO = new InvoiceStatsDTO();
            invoiceStatsDTO.setId(enrollment.getId());
            invoiceStatsDTO.setPrice(enrollment.getCourseId().getPrice());
            invoiceStatsDTOs.add(invoiceStatsDTO);
        }
        
        for (InvoiceStatsDTO invoiceStatsDTO : invoiceStatsDTOs) {
        invoiceStatsDTO.setTotalAmount(totalAmount);
    }
        return invoiceStatsDTOs;
    }


}
