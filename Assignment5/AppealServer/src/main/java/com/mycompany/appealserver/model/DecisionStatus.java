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
public enum DecisionStatus {
    @XmlEnumValue(value="unretrieved")
    Unretrieved,
    @XmlEnumValue(value="retrieved")
    Retrieved 
}