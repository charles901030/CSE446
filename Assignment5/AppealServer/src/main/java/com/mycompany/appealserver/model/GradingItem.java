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
public enum GradingItem {
    @XmlEnumValue(value="assignment1")
    Assignment1,
    @XmlEnumValue(value="assignment2")
    Assignment2,
    @XmlEnumValue(value="assignment3")
    Assignment3,
    @XmlEnumValue(value="assignment4")
    Assignment4,
    @XmlEnumValue(value="quiz1")
    Quiz1,
    @XmlEnumValue(value="quiz2")
    Quiz2,
    @XmlEnumValue(value="quiz3")
    Quiz3,
    @XmlEnumValue(value="paper")
    Paper,
    @XmlEnumValue(value="midterm")
    Midterm,
    @XmlEnumValue(value="final")
    Final
}