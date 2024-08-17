/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group8.fomatter;

import com.group8.pojo.User;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author thang
 */
public class UserFormatter implements Formatter<User> {

    @Override
    public String print(User cate, Locale locale) {
        return String.valueOf(cate.getId());
    }

    @Override
    public User parse(String cateId, Locale locale) throws ParseException {
        User c = new User();
        c.setId(Integer.parseInt(cateId));
        
        return c;
    }
    
}
