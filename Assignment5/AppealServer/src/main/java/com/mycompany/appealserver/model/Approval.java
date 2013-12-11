/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealserver.model;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author shaosh
 */
public enum Approval {
    @XmlEnumValue(value="yes")
    Yes,
    @XmlEnumValue(value="no")
    No    
}
