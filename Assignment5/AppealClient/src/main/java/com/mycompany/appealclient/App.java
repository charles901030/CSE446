package com.mycompany.appealclient;

import com.mycompany.appealclient.model.Appeal;
import com.mycompany.appealclient.model.AppealItem;
import com.mycompany.appealclient.model.AppealRepresentation;
import com.mycompany.appealclient.model.AppealStatus;
import com.mycompany.appealclient.model.GradingItem;
import com.mycompany.appealclient.model.Image;
import com.mycompany.appealclient.model.Justification;
import com.mycompany.appealclient.model.Link;
import com.mycompany.appealclient.model.Name;
import com.mycompany.appealclient.model.Subject;
import com.mycompany.appealclient.model.Uri;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    public static void main( String[] args )
    {
        Case.AbandonedCase();
        Case.HappyCase(); 
        Case.BadStart();
        Case.ForgottenCase();
        Case.BadId();
//        LOG.info("Step 1: Build an appeal");
//        AppealItem item = new AppealItem(new Justification("Too low") , new Image("capture.jpg"));
//        ArrayList<AppealItem> itemList = new ArrayList();
//        AppealItem item2 = new AppealItem(new Justification("Not reasonable") , new Image("capture2.jpg"));
//        itemList.add(item);
//        itemList.add(item2);
//        Appeal appeal = new Appeal(new Name("Shihuan"), new Subject("Assignment1"), GradingItem.Assignment1, itemList);
//        AppealRepresentation ar = new AppealRepresentation(appeal, AppealStatus.Pending);
//        String xmlString = ar.AppealRepresentationToXml();
//        LOG.info("The appeal to send:");
//        LOG.info(xmlString);
//        LOG.info("============================================================================================================");
//        
//        LOG.info("Step 2: Create an appeal");
//        Action action = new Action();
//        AppealRepresentation returnAr = action.CreateAppeal(xmlString); 
//        LOG.info("The returned appeal representation:");
//        if(returnAr != null)
//            LOG.info(returnAr.toString());
//        else
//            LOG.info("null");
//        LOG.info("============================================================================================================");
//        
//        LOG.info("Step 3: Update the appeal");
//        Justification newJustification = new Justification("A little bit unreasonable");
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
//        returnAr = action.UpdateAppeal(xmlString, uriUpdate);
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
//        returnAr = action.GetAppeal(uriGet);
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
//        returnAr = action.DeleteAppeal(uriDelete);
//        if(returnAr != null)
//            LOG.info(returnAr.toString());
//        else
//            LOG.info("null");
//        LOG.info("Get to test delete:");
//        returnAr = action.GetAppeal(uriGet);
//        if(returnAr != null)
//            LOG.info(returnAr.toString());
//        else
//            LOG.info("null");
//        LOG.info("============================================================================================================");
    }
}
