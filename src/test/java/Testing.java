import api.fca.*;
import api.utils.ContextHelper;
import api.utils.OutputPrinter;
import lib.fca.*;
import lib.utils.IndexedList;
import lib.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        /*List<Pair<String,String>> s = TTLParser.getNamespaces("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\httpwww.siemens.comsi-dsgraphswitchgear-level-interlockings.ttl");
        for(Pair<String,String> p : s){
            System.out.println(p.getLeft() + " "+p.getRight());
        }
        List<String> trip = TTLParser.getAllTriples("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\httpwww.siemens.comsi-dsgraphswitchgear-level-interlockings.ttl");
        for(String string : trip){
            System.out.println("TRIPLE:" +string);
        }*/


        MultiValuedObject<String,String,String> ob = new FCAMultiValuedObject<>("1");
        MultiValuedAttribute<String,String,String> atr = new FCAMultiValuedAttribute<>("a");
        List<String> vals = new ArrayList<>();
        vals.add("super");
        vals.add("geil");
        vals.add("yesman");
        atr.addObject(ob, Collections.singletonList("stark"));
        ob.addAttribute(atr, vals);
        ob.addAttribute(atr, Collections.singletonList("stark"));
        ob.addAttribute(atr, Collections.singletonList("staascascark"));
        System.out.println(ob.getObjectID());
        for(Pair<MultiValuedAttribute<String,String,String>, List<String>> p : ob.getDualEntities()){
            System.out.println(p.getLeft().getAttributeID()+" "+p.getRight());
        }
        System.out.println(atr.getDualEntities().stream().map(Pair::getLeft).collect(Collectors.toList()));

        MultiValuedContext<String,String,String> context1 = new FCAMultiValuedFormalContext<>(){};
        context1.addMultiValuedObject(ob);
        System.out.println(context1.getContextObjects().size()+" "+ context1.getContextAttributes().size()+" "+context1.getValues().size());
        context1.addMultiValuedAttribute(atr);
        MultiValuedAttribute<String,String,String> atr2 = new FCAMultiValuedAttribute<>("b");
        atr2.addObject(ob, Collections.singletonList("nice"));
        context1.addMultiValuedAttribute(atr2);
        MultiValuedObject<String,String,String> ob1 = new FCAMultiValuedObject<>("5");
        ob1.addAttribute(atr2, Collections.singletonList("super"));
        context1.addMultiValuedObject(ob1);
        System.out.println(context1.getContextObjects().size()+" "+ context1.getContextAttributes().size()+" "+context1.getValues().size());
        System.out.println(context1.getContextObjects().stream().map(MultiValuedObject::getObjectID).collect(Collectors.toList()));
        System.out.println(context1.getContextAttributes().stream().map(MultiValuedAttribute::getAttributeID).collect(Collectors.toList()));
        System.out.println(context1.getValues());
        MultiValuedObject<String,String,String> ob2 = new FCAMultiValuedObject<>("2");
        ob2.addAttribute(atr2, Collections.singletonList("super"));
        context1.addMultiValuedObject(ob2);
        System.out.println();
        OutputPrinter.printMultiValuedTableToConsole(context1);
        for(ObjectAPI<String,String> o : Computation.nominalScaleAttribute(context1.getContextAttributes().get(0))){
            System.out.println(o.getDualEntities());
        }
        Context<String,String> newcont = Computation.nominalScaleContext(context1);
        OutputPrinter.printCrosstableToConsole(newcont);
    }
}
