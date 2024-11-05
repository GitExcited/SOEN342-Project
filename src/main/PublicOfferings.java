package main;

import java.util.ArrayList;

public class PublicOfferings {
    private ArrayList<PublicOffering> offeringsCollection = new ArrayList<PublicOffering>();

    public String getAllPublicOfferingDescriptions(){
        StringBuilder description = new StringBuilder("");
        for (PublicOffering publicOffering : offeringsCollection) {
            description.append(publicOffering.toString()+ " \n");
        }
        return description.toString();
    }
}
