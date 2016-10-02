/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ijse.thogakade.ctrl.validators;

import com.ijse.thogakade.bo.Customer;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CustomerValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return Customer.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer c = (Customer) o;

        //Validate Empty values
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custName", "customer.custName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "custAddress", "customer.custAddress.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "customer.city.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "province", "customer.province.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "customer.postalCode.empty");

        // Validate DOB
        if (c.getDob() != null && !c.getDob().toString().trim().isEmpty()) {
            if (checkDOB(c.getDob())) {
                errors.reject("dob", "customer.dob.valid");
            }
        }
        
        // Validate Salary
        if (Double.valueOf(c.getSalary()) != null && !Double.valueOf(c.getSalary()).toString().trim().isEmpty()) {
            if (checkSalary(c.getSalary())) {
                errors.reject("salary", "customer.salary.valid");
            }
        }

    }
    
    private boolean checkSalary(double salary) {
        Pattern p = Pattern.compile("[0-9]+(\\.[0-9][0-9]?)?");
        Matcher m = p.matcher(salary+"");
        return m.matches();
    }
    
    private boolean checkDOB(Date dob) {
        Pattern p = Pattern.compile("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$");
        Matcher m = p.matcher(dob.toString());
        return m.matches();
    }
}
