import api.fca.Attribute;
import api.fca.Computation;
import api.fca.Context;
import api.fca.ObjectAPI;
import api.utils.ContextHelper;
import api.utils.OutputPrinter;
import api.utils.TTLParser;
import lib.fca.FCAAttribute;
import lib.fca.FCAFormalContext;
import lib.fca.FCAObject;
import lib.utils.IndexedList;
import lib.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//ONLY USED FOR TESTING PURPOSES;
//WILL BE DELETED IN LATER RELEASES
//@author Leon Geis (22.10.20)

public class Testing {

    public static void main(String[] args) throws IOException {

        Context<String,String> context = ContextHelper.createContextFromFile("C:\\Users\\lgeis\\Documents\\GitHub\\FCAlib2\\src\\main\\java\\lib\\utils\\output\\testcon.txt");
        OutputPrinter.printCrosstableToConsole(context);
        OutputPrinter.printConceptsToConsole(context);
        OutputPrinter.printStemBaseToConsole(context);

        List<Attribute<String,String>> at = new ArrayList<>();
        at.add(context.getAttribute("c"));
        at.add(context.getAttribute("f"));
        at.add(context.getAttribute("g"));
        at.add(context.getAttribute("e"));
        List<Attribute<String,String>> obj = Computation.computePrimeOfObjects(Computation.computePrimeOfAttributes(at,context),context);
        for(Attribute<String,String> a : obj){
            System.out.println(a.getAttributeID());
        }


        List<Attribute<String,String>> attt = new ArrayList<>();
        attt.add(context.getAttribute("c"));
        attt.add(context.getAttribute("f"));
        attt.add(context.getAttribute("g"));

        IndexedList<Attribute<String,String>> attrs = new IndexedList<>(attt);
        IndexedList<Attribute<String,String>> res = Computation.computeNextClosure(attrs,context);
        for(Pair<Attribute<String, String>, Integer> ele : res.getIndexedList()){
            System.out.println(ele.getLeft().getAttributeID());
        }

        ObjectAPI<Integer,String> gegenstand = new FCAObject<>();

        /*Context<String,String> jcontext = ContextHelper.createContextFromJSONFile("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\ThesisAlgorithmus\\JSON\\query.json");
        OutputPrinter.printCrosstableToConsole(jcontext);

        Computation.reduceContext(jcontext);
        OutputPrinter.printCrosstableToConsole(jcontext);
        OutputPrinter.writeCrosstableToFile(jcontext,"jcontext");
        for(ObjectAPI objectAPI : jcontext.getContextObjects()){
            System.out.println(objectAPI.getDualEntities());
        }*/


        //---------------------------------------------------------------------------------------------------

        ObjectAPI<Integer,String> gst1 = new FCAObject<>();
        gst1.setObjectID(1);
        gst1.addAttribute("wächst in Europa");
        gst1.addAttribute("Blütezeit Juni");
        gst1.addAttribute("Wuchshöhe größer als 15cm");


        Attribute<Integer,String> atr1 = new FCAAttribute<>();
        atr1.setAttributeID("wächst im Wald");
        atr1.addObject(2);
        atr1.addObject(6);


        Context<Integer, String> cxt = new FCAFormalContext<>(){};
        cxt.addObject(gst1);
        cxt.addAttribute(atr1);
        cxt.getObject(2).addAttribute("wächst in Europa");
        cxt.getObject(2).addAttribute("Blütezeit Juni");
        cxt.getObject(2).addAttribute("Wuchshöhe größer als 15cm");
        cxt.getObject(6).addAttribute("wächst in Europa");
        cxt.getObject(6).addAttribute("Blütezeit Juni");
        cxt.getObject(6).addAttribute("Wuchshöhe größer als 15cm");

        OutputPrinter.printCrosstableToConsole(cxt);
        OutputPrinter.printStemBaseToConsole(cxt);

        List<Pair<String,String>> s = TTLParser.getNamespaces("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\httpwww.siemens.comsi-dsgraphswitchgear-level-interlockings.ttl");
        for(Pair<String,String> p : s){
            System.out.println(p.getLeft() + " "+p.getRight());
        }
        List<String> trip = TTLParser.getAllTriples("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\httpwww.siemens.comsi-dsgraphswitchgear-level-interlockings.ttl");
        for(String string : trip){
            System.out.println("TRIPLE:" +string);
        }

    }
}
