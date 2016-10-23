/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : Holds event details and sends to text file
Updated: 5/22/2016
Events.java
Assignment : 2
 */
package myassignment.moodley.duran.prjplayhouse;
class Events
{
    //Declare fields for event
    private final String directorName;
    private final String phoneNumber;
    private final String emailAddress;
    private final String eventName;
    private final double amountPaid;
    private final String eventDescription;
    //***********************************************************************************************
    public Events(String nameOfDirector, String phone, String email, String nameOfEvent, double paidAmt , String eventDescr)
    {
        directorName = nameOfDirector;
        phoneNumber = phone;
        emailAddress = email;
        eventName = nameOfEvent;
        amountPaid = paidAmt;
        eventDescription = eventDescr;
    }
    //***********************************************************************************************
    @Override
    public String toString()
    {
        return  directorName + "," +
                phoneNumber + "," +
                emailAddress + "," +
                eventName + "," +
                amountPaid + "," +
                eventDescription + "\n" ;
    }
}
