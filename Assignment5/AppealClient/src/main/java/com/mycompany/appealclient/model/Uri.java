/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient.model;

/**
 *
 * @author shaosh
 */
public class Uri {
    public static final String BASEURI = "http://localhost:8080/AppealServer/webresources";
    public static final String RELURI = BASEURI + "/relation";
    public static final String APPEAL_NAMESPACE = BASEURI + "/schemas";
    public static final String BADSTARTURI = BASEURI + "/badstart";
    public static final String APPEALURI = BASEURI + "/appeal";
    public static final String PENDINGURI = APPEALURI + "/pending";
    public static final String SUBMITTEDURI = APPEALURI + "/submitted";
    public static final String DECISIONURI = BASEURI + "/decision";
    public static final String GRADEBOOKURI = BASEURI + "/gradebook";
    public static final String REMINDERURI = BASEURI + "/reminder";
    public static final String DAP_NAMESPACE = APPEAL_NAMESPACE + "/dap";
    public static final String APPEAL_MEDIATYPE = "application/vnd.appeal+xml";
    public static final String SELF_REL_VALUE = "self";
    
    
}

