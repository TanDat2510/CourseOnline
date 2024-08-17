/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.fomatter;

import com.group8.pojo.Instructor;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author thang
 */
public class InstructorFormatter implements Formatter<Instructor> {

    @Override
    public String print(Instructor ins, Locale locale) {
        return String.valueOf(ins.getId());
    }

    @Override
    public Instructor parse(String insId, Locale locale) throws ParseException {
        Instructor c = new Instructor();
        c.setId(Integer.parseInt(insId));
        
        return c;
    }
    
}
