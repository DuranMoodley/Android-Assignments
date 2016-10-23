package com.moodley.duran.prjbuildingmanagement;

class Tenant {

    private final String tenantName;
    private final String idNumber;
    private final int numOfOccupants;
    private final String contactNumber;
    private final String emergencyContact;
    private final int flatNumber;
    //*************************************************
    public Tenant(String name, String idNum, int numOccupants, String contactNum, String emergencyNum, int flatNum)
    {
        tenantName = name;
        idNumber = idNum;
        numOfOccupants = numOccupants;
        contactNumber = contactNum;
        emergencyContact = emergencyNum;
        flatNumber = flatNum;
    }
    //*************************************************
    public String getTenantName() {
        return tenantName;
    }
    //*************************************************
    public String getIdNumber() {
        return idNumber;
    }
    //*************************************************
    public int getNumOfOccupants() {
        return numOfOccupants;
    }
    //*************************************************
    public String getContactNumber() {
        return contactNumber;
    }
    //*************************************************
    public String getEmergencyContact() {
        return emergencyContact;
    }
    //*************************************************
    public int getFlatNumber() {
        return flatNumber;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "tenantName='" + tenantName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", numOfOccupants=" + numOfOccupants +
                ", contactNumber='" + contactNumber + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", flatNumber=" + flatNumber +
                '}';
    }
}
