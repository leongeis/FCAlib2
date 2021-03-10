import api.fca.Attribute;
import api.fca.Context;
import api.fca.ObjectAPI;
import api.utils.ContextHelper;
import api.utils.OutputPrinter;
import lib.fca.FCAAttribute;
import lib.fca.FCAFormalContext;
import lib.fca.FCAObject;

import java.io.IOException;

//ONLY USED FOR TESTING PURPOSES;
//WILL BE DELETED IN LATER RELEASES
//@author Leon Geis (22.10.20)

public class Testing {

    public static void main(String[] args) throws IOException {

        //Create Siemens Context
        Context<String,String> siemensContext = new FCAFormalContext<String, String>() {};

        //#Attributes = 12 #Objects = 49
        //Fill Context
        for (int i = 0; i < 49; i++) {
            ObjectAPI<String,String> object = new FCAObject<>(String.valueOf(i+1));
            siemensContext.addObject(object);
        }
        char id = 'a';
        for (int i = 0; i < 12; i++) {
            Attribute<String,String> atr = new FCAAttribute<>(String.valueOf(id));
            siemensContext.addAttribute(atr);
            id++;

        }

        //OutputPrinter.printCrosstableToConsole(siemensContext);
        //Add Incidence
        Context<String,String> test = ContextHelper.createContextFromFile("C:\\Users\\lgeis\\Documents\\GitHub\\FCAlib2\\src\\main\\java\\lib\\utils\\output\\siemens.txt");
        OutputPrinter.printCrosstableToConsole(test);
        OutputPrinter.printStemBaseToConsole(test);
        OutputPrinter.writeStemBaseToFile(test,"siemensSB.txt");
    }
}
