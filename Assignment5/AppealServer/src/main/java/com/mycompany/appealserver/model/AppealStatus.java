/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

import javax.xml.bind.annotation.XmlEnumValue;
/**
 *
 * @author shaosh
 */
public enum AppealStatus {
    @XmlEnumValue(value="pending")
    Pending,
    @XmlEnumValue(value="submitted")
    Submitted,
    @XmlEnumValue(value="reminded")
    Reminded,
    @XmlEnumValue(value="inprogress")
    Inprogress,
    @XmlEnumValue(value="processed")
    Processed,    
    @XmlEnumValue(value="deleted")
    Deleted    
}