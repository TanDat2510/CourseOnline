/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.controller;

import com.group8.dto.EnrollmentDTO;
import com.group8.pojo.Instructor;
import com.group8.pojo.Invoice;
import com.group8.service.InstructorService;
import com.group8.service.InvoiceService;
import com.group8.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TAN DAT
 */
@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private Environment env;

    @RequestMapping("/invoice")
    public String viewInvoice(Model model, @RequestParam Map<String, String> params) {
        if (params.get("page") == null) {
            params.put("page", "1");
        }
        List<Invoice> invoice = this.invoiceService.getAllInvoice(params);
        int total = this.invoiceService.getAllInvoice(null).size();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!entry.getKey().equals("page") && entry.getValue() != null && !entry.getValue().isEmpty()) {
                params.remove("page");
                total = this.invoiceService.getAllInvoice(params).size();
                break;
            }
        }
        int PAGE_MAX = Integer.parseInt(env.getProperty("page.size.invoice"));
        int pageTotal = (int) Math.ceil((double) total / PAGE_MAX);
        model.addAttribute("invoice", invoice);
        model.addAttribute("pageTotal", pageTotal);
        System.out.println("HELO" + pageTotal);
        return "invoice";
    }

    @GetMapping("/invoice/view-details/{invoiceId}")
    public String detailsViewInvoice(Model model, @PathVariable(value = "invoiceId") int id) {
//        List<EnrollmentDTO> enrollmentDTOs = invoiceService.getInvoiceById(id);
        model.addAttribute("enrollmentDTO", this.invoiceService.getInvoiceById(id));

        System.out.println("HelloAA" + this.invoiceService.getInvoiceById(id));

        return "invoice-details";
    }

    

}
