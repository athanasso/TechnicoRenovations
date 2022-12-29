package com.codehub.techniconrenovations.util;

import com.codehub.techniconrenovations.services.impl.AdminServicesImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilFunctions {

    /**
     * Converts a String to date
     *
     * @param date
     * @return Date
     */
    public static Date convertToDate(String date) {       
        if (!date.equals("null")) {
            String[] dateSplit =date.split("/");
            return new Date(Integer.parseInt(dateSplit[2]) - 1900, Integer.parseInt(dateSplit[1]) - 1, Integer.parseInt(dateSplit[0]));
        }
        return new Date(1,0,1);//null date flag
    }
    
    /**
     * Checks a date if it is equal to null date flag.
     * @param date
     * @return bool
     */
    public static boolean CheckDate(Date date){
        return date.compareTo(new Date(1,0,1)) == 0;
    }
    
    /**
     * Prints a list of objects.
     * @param list
     */
    public static void printList(List<? extends Object> list){
        if(list.isEmpty())System.err.println("No results matching your criteria found!");
        list.forEach(o -> System.out.println(o.toString()));
    }
    
    /**
     * Saves a list of objects to a file.
     * @param list
     * @param classname
     */
    public static void reportsToFile(List<? extends Object> list,String classname) {
        File file = new File(classname+".csv");
        System.out.println("Generating file for Repairs");
        try ( PrintWriter pw = new PrintWriter(file)) {
            list.forEach(o ->pw.println(o));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminServicesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}

