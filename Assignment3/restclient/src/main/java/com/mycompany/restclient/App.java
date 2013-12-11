package com.mycompany.restclient;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {        
        RestClient restClient = new RestClient();
        
        //Test adding food items
        String postNotexist = restClient.postFoodItem("GB", "Fish", "Good", "dinner", "10.09");
        System.out.println("postNotexist response: " + postNotexist);
        System.out.println("=========================================================================");
        String postExist1 = restClient.postFoodItem("GB", "Fish and Chips", "A smaller portion of our Dinner version", "Lunch", "10.95");
        System.out.println("postExist1 response: " + postExist1);
        System.out.println("=========================================================================");
        String postExist2 = restClient.postFoodItem("GB", "Fish", "Good", "dinner", "10.09");
        System.out.println("postExist2 response: " + postExist2);
        System.out.println("=========================================================================");
        String postInvalid = restClient.postFoodItem("CN", "Fish", "Good", "dinner", "10.09");
        System.out.println("postInvalid response: " + postInvalid);
        System.out.println("=========================================================================");
        
        //Test getting food items
        String[] array1 = {"100", "200", "300"};
        String getAllexist = restClient.getFoodItem(array1);
        System.out.println("getAllexist response: " + getAllexist);
        System.out.println("=========================================================================");
        String[] array2 = {"100", "255", "400"};
        String getNotallexist = restClient.getFoodItem(array2);
        System.out.println("getNotallexist response: " + getNotallexist);
        System.out.println("=========================================================================");
        String[] array3 = {"99", "133", "399"};
        String getNoneexist = restClient.getFoodItem(array3);
        System.out.println("getNoneexist response: " + getNoneexist);
        System.out.println("=========================================================================");
    }
}


