/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.appealclient;

import com.mycompany.appealclient.model.Appeal;
import com.mycompany.appealclient.model.AppealItem;
import com.mycompany.appealclient.model.AppealRepresentation;
import com.mycompany.appealclient.model.AppealStatus;
import com.mycompany.appealclient.model.DecisionRepresentation;
import com.mycompany.appealclient.model.GradingItem;
import com.mycompany.appealclient.model.Id;
import com.mycompany.appealclient.model.Image;
import com.mycompany.appealclient.model.Justification;
import com.mycompany.appealclient.model.Name;
import com.mycompany.appealclient.model.Reminder;
import com.mycompany.appealclient.model.ReminderRepresentation;
import com.mycompany.appealclient.model.Subject;
import com.mycompany.appealclient.model.Uri;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shaosh
 */
public class Case {
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    
    
    public static void Case(){}
    
    public static void HappyCase(){
        LOG.info("Happy Case Start********************************************************************************************");
        LOG.info("Step 1: Student build an appeal");
        AppealItem item = new AppealItem(new Justification("Most of my functions work, but I only got such a low grade.") , new Image("capture.jpg"));
        ArrayList<AppealItem> itemList = new ArrayList();
        AppealItem item2 = new AppealItem(new Justification("It is not reasonable to reduce points here.") , new Image("capture2.jpg"));
        itemList.add(item);
        itemList.add(item2);
        Appeal appeal = new Appeal(new Name("ShihuanShao"), new Subject("Assignment1"), GradingItem.Assignment1, itemList);
        AppealRepresentation ar = new AppealRepresentation(appeal, AppealStatus.Pending);
        String xmlString = ar.AppealRepresentationToXml();
        LOG.info("The appeal to send:");
        LOG.info(xmlString);
        LOG.info("============================================================================================================");
        
        LOG.info("Step 2: Student send the appeal to the server");
        AppealRepresentation returnAr = Action.CreateAppeal(xmlString); 
//        LOG.info("The returned appeal representation:");
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
        LOG.info("Step 3: Student update the appeal to tone down the language");
        Justification newJustification = new Justification("It is a little bit unreasonable to reduce point here.");
        returnAr.getAppeal().getAppealItems().get(1).setJustification(newJustification);
        xmlString = returnAr.AppealRepresentationToXml();
        URI uriUpdate = null;
        try {
            LOG.info("uriUpdate: " + returnAr.getUpdate());
            uriUpdate = new URI(returnAr.getUpdate());
        } 
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.UpdateAppeal(xmlString, uriUpdate);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
        LOG.info("Step 4: Student get the appeal to confirm no errors");
        URI uriGet = null;
        try {
            LOG.info("uriGet: " + returnAr.getSelf());
            uriGet = new URI(returnAr.getSelf());
        } 
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.GetAppeal(uriGet);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
    
        LOG.info("Step 5: Student submit the appeal and it can't be modified or deleted any longer");
        URI uriSubmit = null;
        try{
            LOG.info("uriSubmit: " + returnAr.getSubmit());
            uriSubmit = new URI(returnAr.getSubmit());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.SubmitAppeal(uriSubmit);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
    
        LOG.info("Step 6: Professor get reminders to process");
        ReminderRepresentation rr = Action.GetReminders();
        if(rr != null){
            ArrayList<Reminder> reminders = new ArrayList();
            ArrayList<DecisionRepresentation> drs = Action.ProcessReminders(reminders);
            if(drs != null){
//                LOG.info("Response entity in representation:");
//                for(int i = 0; i < drs.size(); i++){
//                    LOG.info(drs.get(i).toString());
//                }
            }
            else{
                LOG.info("null");
                return;
            }
        }
        else{
            LOG.info("No reminders");
        }
        LOG.info("============================================================================================================");
        
        LOG.info("Step 7: Professor get an appeal to process");
        try{
            uriSubmit = new URI(Uri.SUBMITTEDURI);
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        LOG.info("Professor is trying to retrieve an appeal");
        AppealRepresentation returnArToProf = Action.GetAppealtoProcess(uriSubmit);
        while(returnArToProf != null)
        {
            
            String submittedId = returnArToProf.getId().toString();
            String decisionString = Action.ProcessAppeal(returnArToProf);
            try{
                uriSubmit = new URI(Uri.SUBMITTEDURI + "/" + submittedId);
            }
            catch (URISyntaxException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            DecisionRepresentation returnDrToProf = Action.SubmitDecision(decisionString, uriSubmit);
            if(returnDrToProf != null){
//                LOG.info("Response entity in representation:");
//                LOG.info(returnDrToProf.toString());
            }
            else{
                LOG.info("null");
                return;
            }
            
            try{
                uriSubmit = new URI(Uri.SUBMITTEDURI);
            }
            catch (URISyntaxException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            LOG.info("Professor is trying to retrieve an appeal");
            returnArToProf = Action.GetAppealtoProcess(uriSubmit);
        }
        LOG.info("============================================================================================================");
        
        LOG.info("Step 8: Student get the submitted appeal");
        try{
            LOG.info("uriSubmit: " + returnAr.getSelf());
            uriSubmit = new URI(returnAr.getSelf());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.GetAppeal(uriSubmit);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
        LOG.info("Step 9: Student get the decision");
        URI uriDecision = null;
        try{
            LOG.info("uriDecision: " + returnAr.getDecision());
            uriDecision = new URI(returnAr.getDecision());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        DecisionRepresentation returnDr = Action.GetDecision(uriDecision);
        if(returnDr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnDr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
    
        LOG.info("Step 10: Student get the grade");
        if(returnDr.getDecision().getApproval().toString().equalsIgnoreCase("no")){
            LOG.info("Grade remains the same");
            return;
        }
        URI uriGrade = null;
        try {
            LOG.info("uriGrade: " + returnDr.getGrade());;
            uriGrade = new URI(returnDr.getGrade());
        } 
        catch (URISyntaxException ex) {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        Id id = returnDr.getId();
        NameItemGrade returnNig = null;
        try {
            returnNig = Action.GetGrade(uriGrade, id);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if(returnNig != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnNig.toString());
        }
        else{
            LOG.info("null");
            LOG.info("Happy Case End********************************************************************************************");
            LOG.info("\n\n");
            return;
        }
        LOG.info("Happy Case End********************************************************************************************");
        LOG.info("\n\n");
    }
    
    public static void AbandonedCase(){
        LOG.info("Abandoned Case Start********************************************************************************************");
        LOG.info("Abandoned Case");
        LOG.info("Step 1: Student build an appeal");
        AppealItem item = new AppealItem(new Justification("Most of my functions work, but I only got such a low grade.") , new Image("capture.jpg"));
        ArrayList<AppealItem> itemList = new ArrayList();
        AppealItem item2 = new AppealItem(new Justification("It is not reasonable to reduce points here.") , new Image("capture2.jpg"));
        itemList.add(item);
        itemList.add(item2);
        Appeal appeal = new Appeal(new Name("ShihuanShao"), new Subject("Assignment1"), GradingItem.Assignment1, itemList);
        AppealRepresentation ar = new AppealRepresentation(appeal, AppealStatus.Pending);
        String xmlString = ar.AppealRepresentationToXml();
        LOG.info("The appeal to send:");
        LOG.info(xmlString);
        LOG.info("============================================================================================================");
        
        LOG.info("Step 2: Student send the appeal to the server");
        Action Action = new Action();
        AppealRepresentation returnAr = Action.CreateAppeal(xmlString); 
        URI uriGet = null;
        try {
            LOG.info("uriSelf: " + returnAr.getSelf());
            uriGet = new URI(returnAr.getSelf());
        } 
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
//        LOG.info("The returned appeal representation:");
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
    
        LOG.info("Step 3: Student delete the appeal");
        URI uriDelete = null;
        try {
            LOG.info("uriDelete: " + returnAr.getDelete());
            uriDelete = new URI(returnAr.getDelete());
        } 
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.DeleteAppeal(uriDelete);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else
            LOG.info("null");
        LOG.info("Get to test if delete works:");
//        URI uriGet = null;
//        try {
//            LOG.info("uriGet: " + returnAr.getSelf());
//            uriGet = new URI(returnAr.getSelf());
//        } 
//        catch (URISyntaxException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }
        returnAr = Action.GetAppeal(uriGet);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            LOG.info("Abandoned Case End********************************************************************************************");
            LOG.info("\n\n");
            return;
        }
        LOG.info("Abandoned Case End********************************************************************************************");
        LOG.info("\n\n");
    }
    
    public static void ForgottenCase(){
        LOG.info("Forgotten Case Start********************************************************************************************");
        
        LOG.info("Step 1: Student build an appeal");
        AppealItem item = new AppealItem(new Justification("Most of my functions work, but I only got such a low grade.") , new Image("capture.jpg"));
        ArrayList<AppealItem> itemList = new ArrayList();
        AppealItem item2 = new AppealItem(new Justification("It is not reasonable to reduce points here.") , new Image("capture2.jpg"));
        itemList.add(item);
        itemList.add(item2);
        Appeal appeal = new Appeal(new Name("ShihuanShao"), new Subject("Assignment1"), GradingItem.Assignment1, itemList);
        AppealRepresentation ar = new AppealRepresentation(appeal, AppealStatus.Pending);
        String xmlString = ar.AppealRepresentationToXml();
        LOG.info("The appeal to send:");
        LOG.info(xmlString);
        LOG.info("============================================================================================================");
        
        LOG.info("Step 2: Student send the appeal to the server");
        AppealRepresentation returnAr = Action.CreateAppeal(xmlString); 
//        LOG.info("The returned appeal representation:");
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
//        LOG.info("Step 3: Student update the appeal to tone down the language");
//        Justification newJustification = new Justification("It is a little bit unreasonable to reduce point here.");
//        returnAr.getAppeal().getAppealItems().get(1).setJustification(newJustification);
//        xmlString = returnAr.AppealRepresentationToXml();
//        URI uriUpdate = null;
//        try {
//            LOG.info("uriUpdate: " + returnAr.getUpdate());
//            uriUpdate = new URI(returnAr.getUpdate());
//        } 
//        catch (URISyntaxException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }
//        returnAr = Action.UpdateAppeal(xmlString, uriUpdate);
//        if(returnAr != null){
////            LOG.info("Response entity in representation:");
////            LOG.info(returnAr.toString());
//        }
//        else{
//            LOG.info("null");
//            return;
//        }
//        LOG.info("============================================================================================================");
//        
//        LOG.info("Step 4: Student get the appeal to confirm no errors");
//        URI uriGet = null;
//        try {
//            LOG.info("uriGet: " + returnAr.getSelf());
//            uriGet = new URI(returnAr.getDelete());
//        } 
//        catch (URISyntaxException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }
//        returnAr = Action.GetAppeal(uriGet);
//        if(returnAr != null){
////            LOG.info("Response entity in representation:");
////            LOG.info(returnAr.toString());
//        }
//        else{
//            LOG.info("null");
//            return;
//        }
//        LOG.info("============================================================================================================");
    
        LOG.info("Step 3: Student submit the appeal and it can't be modified or deleted any longer");
        URI uriSubmit = null;
        try{
            LOG.info("uriSubmit: " + returnAr.getSubmit());
            uriSubmit = new URI(returnAr.getSubmit());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.SubmitAppeal(uriSubmit);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
        try {
            TimeUnit.SECONDS.sleep(3);
            LOG.info("One week passed, the student did not receive any response");
            TimeUnit.SECONDS.sleep(3);
            LOG.info("Two week passed, the student did not receive any response");
            TimeUnit.SECONDS.sleep(3);
            LOG.info("Three week passed, the student did not receive any response");
            LOG.info("The student wanted to check if his appeal get processed");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        LOG.info("Step 4: Student get the submitted appeal");
        try{
            LOG.info("uriSubmit: " + returnAr.getSelf());
            uriSubmit = new URI(returnAr.getSelf());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.GetAppeal(uriSubmit);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
        LOG.info("Step 5: Professor get reminders to process");
        ReminderRepresentation rr = Action.GetReminders();
        if(rr != null){
            ArrayList<Reminder> reminders = new ArrayList();
            ArrayList<DecisionRepresentation> drs = Action.ProcessReminders(reminders);
            if(drs != null){
//                LOG.info("Response entity in representation:");
//                for(int i = 0; i < drs.size(); i++){
//                    LOG.info(drs.get(i).toString());
//                }
            }
            else{
                LOG.info("null");
                return;
            }
        }
        else{
            LOG.info("No reminders");
        }
        LOG.info("============================================================================================================");
    
        LOG.info("Step 6: Professor get an appeal to process");
        try{
            uriSubmit = new URI(Uri.SUBMITTEDURI);
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        LOG.info("Professor is trying to retrieve an appeal");
        AppealRepresentation returnArToProf = Action.GetAppealtoProcess(uriSubmit);
        while(returnArToProf != null)
        {
            String submittedId = returnArToProf.getId().toString();
            String decisionString = Action.ProcessAppeal(returnArToProf);
            try{
                uriSubmit = new URI(Uri.SUBMITTEDURI + "/" + submittedId);
            }
            catch (URISyntaxException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            DecisionRepresentation returnDrToProf = Action.SubmitDecision(decisionString, uriSubmit);
            if(returnDrToProf != null){
//                LOG.info("Response entity in representation:");
//                LOG.info(returnDrToProf.toString());
            }
            else{
                LOG.info("null");
                return;
            }
            
            try{
                uriSubmit = new URI(Uri.SUBMITTEDURI);
            }
            catch (URISyntaxException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            LOG.info("Professor is trying to retrieve an appeal");
            returnArToProf = Action.GetAppealtoProcess(uriSubmit);
        }
        LOG.info("============================================================================================================");
        
        LOG.info("Step 7: Student get the submitted appeal again");
        try{
            LOG.info("uriSubmit: " + returnAr.getSelf());
            uriSubmit = new URI(returnAr.getSelf());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.GetAppeal(uriSubmit);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
        
        LOG.info("Step 8: Student get the decision");
        URI uriDecision = null;
        try{
            LOG.info("uriDecision: " + returnAr.getDecision());
            uriDecision = new URI(returnAr.getDecision());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        DecisionRepresentation returnDr = Action.GetDecision(uriDecision);
        if(returnDr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnDr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
    
        LOG.info("Step 9: Student get the grade");
        if(returnDr.getDecision().getApproval().toString().equalsIgnoreCase("no")){
            LOG.info("Grade remains the same");
            return;
        }
        URI uriGrade = null;
        try {
            LOG.info("uriGrade: " + returnDr.getGrade());;
            uriGrade = new URI(returnDr.getGrade());
        } 
        catch (URISyntaxException ex) {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        Id id = returnDr.getId();
        NameItemGrade returnNig = null;
        try {
            returnNig = Action.GetGrade(uriGrade, id);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if(returnNig != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnNig.toString());
        }
        else{
            LOG.info("null");
            LOG.info("Forgotten Case End********************************************************************************************");
            LOG.info("\n\n");
            return;
        }
        
        LOG.info("Forgotten Case End********************************************************************************************");
        LOG.info("\n\n");
    }
    
    public static void BadStart(){
        LOG.info("Bad Start Start********************************************************************************************");
        LOG.info("Bad Start");
        LOG.info("Step 1: Student build an appeal");
        AppealItem item = new AppealItem(new Justification("Most of my functions work, but I only got such a low grade.") , new Image("capture.jpg"));
        ArrayList<AppealItem> itemList = new ArrayList();
        AppealItem item2 = new AppealItem(new Justification("It is not reasonable to reduce points here.") , new Image("capture2.jpg"));
        itemList.add(item);
        itemList.add(item2);
        Appeal appeal = new Appeal(new Name("ShihuanShao"), new Subject("Assignment1"), GradingItem.Assignment1, itemList);
        AppealRepresentation ar = new AppealRepresentation(appeal, AppealStatus.Pending);
        String xmlString = ar.AppealRepresentationToXml();
        LOG.info("The appeal to send:");
        LOG.info(xmlString);
        LOG.info("============================================================================================================");
        
        LOG.info("Step 2: Student send the appeal to the server");
        LOG.info("Target URI: " + Uri.BADSTARTURI);
        AppealRepresentation returnAr = Action.CreateBadStart(xmlString); 
//        LOG.info("The returned appeal representation:");
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else
            LOG.info("null");
        LOG.info("Bad Start End********************************************************************************************");
        LOG.info("\n\n");
    }
    
    public static void BadId(){
        LOG.info("Bad Id Start********************************************************************************************");
        
        LOG.info("Step 1: Student build an appeal");
        AppealItem item = new AppealItem(new Justification("Most of my functions work, but I only got such a low grade.") , new Image("capture.jpg"));
        ArrayList<AppealItem> itemList = new ArrayList();
        AppealItem item2 = new AppealItem(new Justification("It is not reasonable to reduce points here.") , new Image("capture2.jpg"));
        itemList.add(item);
        itemList.add(item2);
        Appeal appeal = new Appeal(new Name("ShihuanShao"), new Subject("Assignment1"), GradingItem.Assignment1, itemList);
        AppealRepresentation ar = new AppealRepresentation(appeal, AppealStatus.Pending);
        String xmlString = ar.AppealRepresentationToXml();
        LOG.info("The appeal to send:");
        LOG.info(xmlString);
        LOG.info("============================================================================================================");
        
        LOG.info("Step 2: Student send the appeal to the server");
        AppealRepresentation returnAr = Action.CreateAppeal(xmlString); 
//        LOG.info("The returned appeal representation:");
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
//        LOG.info("Step 3: Student update the appeal to tone down the language");
//        Justification newJustification = new Justification("It is a little bit unreasonable to reduce point here.");
//        returnAr.getAppeal().getAppealItems().get(1).setJustification(newJustification);
//        xmlString = returnAr.AppealRepresentationToXml();
//        URI uriUpdate = null;
//        try {
//            LOG.info("uriUpdate: " + returnAr.getUpdate());
//            uriUpdate = new URI(returnAr.getUpdate());
//        } 
//        catch (URISyntaxException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }
//        returnAr = Action.UpdateAppeal(xmlString, uriUpdate);
//        if(returnAr != null){
////            LOG.info("Response entity in representation:");
////            LOG.info(returnAr.toString());
//        }
//        else{
//            LOG.info("null");
//            return;
//        }
//        LOG.info("============================================================================================================");
//        
//        LOG.info("Step 4: Student get the appeal to confirm no errors");
//        URI uriGet = null;
//        try {
//            LOG.info("uriGet: " + returnAr.getSelf());
//            uriGet = new URI(returnAr.getDelete());
//        } 
//        catch (URISyntaxException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }
//        returnAr = Action.GetAppeal(uriGet);
//        if(returnAr != null){
////            LOG.info("Response entity in representation:");
////            LOG.info(returnAr.toString());
//        }
//        else{
//            LOG.info("null");
//            return;
//        }
//        LOG.info("============================================================================================================");
    
        LOG.info("Step 3: Student submit the appeal and it can't be modified or deleted any longer");
        URI uriSubmit = null;
        try{
            LOG.info("uriSubmit: " + returnAr.getSubmit());
            uriSubmit = new URI(returnAr.getSubmit());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.SubmitAppeal(uriSubmit);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
        try {
            TimeUnit.SECONDS.sleep(3);
            LOG.info("One week passed, the student did not receive any response");
            TimeUnit.SECONDS.sleep(3);
            LOG.info("Two week passed, the student did not receive any response");
            TimeUnit.SECONDS.sleep(3);
            LOG.info("Three week passed, the student did not receive any response");
            LOG.info("The student wanted to check if his appeal get processed");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        LOG.info("Step 4: Student tries to get the submitted appeal, but he can't remember the appeal id");
        Id badId = new Id(); 
        try{
            uriSubmit = new URI(Uri.SUBMITTEDURI + "/" + badId);
            LOG.info("uriSubmit: " + uriSubmit);
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.GetAppeal(uriSubmit);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null"); 
        }
        LOG.info("============================================================================================================");
        
        LOG.info("Step 5: Professor get reminders to process");
        ReminderRepresentation rr = Action.GetReminders();
        if(rr != null){
            ArrayList<Reminder> reminders = new ArrayList();
            ArrayList<DecisionRepresentation> drs = Action.ProcessReminders(reminders);
            if(drs != null){
//                LOG.info("Response entity in representation:");
//                for(int i = 0; i < drs.size(); i++){
//                    LOG.info(drs.get(i).toString());
//                }
            }
            else{
                LOG.info("null");
                return;
            }
        }
        else{
            LOG.info("No reminders");
        }
        LOG.info("============================================================================================================");
    
        LOG.info("Step 6: Professor get an appeal to process");
        try{
            uriSubmit = new URI(Uri.SUBMITTEDURI);
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        LOG.info("Professor is trying to retrieve an appeal");
        AppealRepresentation returnArToProf = Action.GetAppealtoProcess(uriSubmit);
        while(returnArToProf != null)
        {
            String submittedId = returnArToProf.getId().toString();
            String decisionString = Action.ProcessAppeal(returnArToProf);
            try{
                uriSubmit = new URI(Uri.SUBMITTEDURI + "/" + submittedId);
            }
            catch (URISyntaxException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            DecisionRepresentation returnDrToProf = Action.SubmitDecision(decisionString, uriSubmit);
            if(returnDrToProf != null){
//                LOG.info("Response entity in representation:");
//                LOG.info(returnDrToProf.toString());
            }
            else{
                LOG.info("null");
                return;
            }
            
            try{
                uriSubmit = new URI(Uri.SUBMITTEDURI);
            }
            catch (URISyntaxException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            LOG.info("Professor is trying to retrieve an appeal");
            returnArToProf = Action.GetAppealtoProcess(uriSubmit);
        }
        LOG.info("============================================================================================================");
        
        
        LOG.info("Step 7: Student get the submitted appeal again, he is still using the bad Id");
        try{
            uriSubmit = new URI(Uri.SUBMITTEDURI + "/" + badId);
            LOG.info("uriSubmit: " + uriSubmit); 
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        returnAr = Action.GetAppeal(uriSubmit);
        if(returnAr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnAr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
        
        
        LOG.info("Step 8: Student get the decision");
        URI uriDecision = null;
        try{
            LOG.info("uriDecision: " + returnAr.getDecision());
            uriDecision = new URI(returnAr.getDecision());
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        DecisionRepresentation returnDr = Action.GetDecision(uriDecision);
        if(returnDr != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnDr.toString());
        }
        else{
            LOG.info("null");
            return;
        }
        LOG.info("============================================================================================================");
    
        LOG.info("Step 9: Student get the grade");
        if(returnDr.getDecision().getApproval().toString().equalsIgnoreCase("no")){
            LOG.info("Grade remains the same");
            return;
        }
        URI uriGrade = null;
        try {
            LOG.info("uriGrade: " + returnDr.getGrade());;
            uriGrade = new URI(returnDr.getGrade());
        } 
        catch (URISyntaxException ex) {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        Id id = returnDr.getId();
        NameItemGrade returnNig = null;
        try {
            returnNig = Action.GetGrade(uriGrade, id);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Case.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if(returnNig != null){
//            LOG.info("Response entity in representation:");
//            LOG.info(returnNig.toString());
        }
        else{
            LOG.info("null");
            LOG.info("Bad Id End********************************************************************************************");
            LOG.info("\n\n");
            return;
        }
        LOG.info("Bad Id End********************************************************************************************");
        LOG.info("\n\n");
    }
    
//    {
//        LOG.info("Step 1: Build an appeal");
//        AppealItem item = new AppealItem(new Justification("Most of my functions work, but I only got such a low grade.") , new Image("capture.jpg"));
//        ArrayList<AppealItem> itemList = new ArrayList();
//        AppealItem item2 = new AppealItem(new Justification("It is not reasonable to reduce points here.") , new Image("capture2.jpg"));
//        itemList.add(item);
//        itemList.add(item2);
//        Appeal appeal = new Appeal(new Name("ShihuanShao"), new Subject("Assignment1"), GradingItem.Assignment1, itemList);
//        AppealRepresentation ar = new AppealRepresentation(appeal, AppealStatus.Pending);
//        String xmlString = ar.AppealRepresentationToXml();
//        LOG.info("The appeal to send:");
//        LOG.info(xmlString);
//        LOG.info("============================================================================================================");
//        
//        LOG.info("Step 2: Create an appeal");
//        Action Action = new Action();
//        AppealRepresentation returnAr = Action.CreateAppeal(xmlString); 
//        LOG.info("The returned appeal representation:");
//        if(returnAr != null)
//            LOG.info(returnAr.toString());
//        else
//            LOG.info("null");
//        LOG.info("============================================================================================================");
//        
//        LOG.info("Step 3: Update the appeal");
//        Justification newJustification = new Justification("It is a little bit unreasonable to reduce point here.");
//        returnAr.getAppeal().getAppealItems().get(1).setJustification(newJustification);
//        xmlString = returnAr.AppealRepresentationToXml();
//        URI uriUpdate = null;
//        try {
//            LOG.info("updateUri: " + returnAr.getUpdate());
//            uriUpdate = new URI(returnAr.getUpdate());
//        } 
//        catch (URISyntaxException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        returnAr = Action.UpdateAppeal(xmlString, uriUpdate);
//        if(returnAr != null)
//            LOG.info(returnAr.toString());
//        else
//            LOG.info("null");
//        LOG.info("============================================================================================================");
//        
//        LOG.info("Step 4: Get the appeal");
//        URI uriGet = null;
//        try {
//            LOG.info("uriGet: " + returnAr.getSelf());
//            uriGet = new URI(returnAr.getDelete());
//        } 
//        catch (URISyntaxException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        returnAr = Action.GetAppeal(uriGet);
//        if(returnAr != null)
//            LOG.info(returnAr.toString());
//        else
//            LOG.info("null");
//        LOG.info("============================================================================================================");
//        
//        
//        LOG.info("Step 5: Delete the appeal");
//        URI uriDelete = null;
//        try {
//            LOG.info("uriDelete: " + returnAr.getDelete());
//            uriDelete = new URI(returnAr.getDelete());
//        } 
//        catch (URISyntaxException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        returnAr = Action.DeleteAppeal(uriDelete);
//        if(returnAr != null)
//            LOG.info(returnAr.toString());
//        else
//            LOG.info("null");
//        LOG.info("Get to test delete:");
//        returnAr = Action.GetAppeal(uriGet);
//        if(returnAr != null)
//            LOG.info(returnAr.toString());
//        else
//            LOG.info("null");
//        LOG.info("============================================================================================================");
//    }
}
