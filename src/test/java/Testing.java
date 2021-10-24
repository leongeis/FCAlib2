import api.fca.Context;
import api.utils.ContextHelper;
import api.utils.OutputPrinter;

import java.io.IOException;

//ONLY USED FOR TESTING PURPOSES;
//WILL BE DELETED IN LATER RELEASES
//@author Leon Geis (22.10.20)

public class Testing {

    public static void main(String[] args) throws IOException {


        /*Context<String,String> jcontext = ContextHelper.createContextFromJSONFile("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\ThesisAlgorithmus\\JSON\\query.json");
        OutputPrinter.printCrosstableToConsole(jcontext);

        Computation.reduceContext(jcontext);
        OutputPrinter.printCrosstableToConsole(jcontext);
        OutputPrinter.writeCrosstableToFile(jcontext,"jcontext");
        for(ObjectAPI objectAPI : jcontext.getContextObjects()){
            System.out.println(objectAPI.getDualEntities());
        }*/


        //---------------------------------------------------------------------------------------------------
        /*
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


        */


        /*List<Pair<String,String>> s = TTLParser.getNamespaces("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\httpwww.siemens.comsi-dsgraphswitchgear-level-interlockings.ttl");
        for(Pair<String,String> p : s){
            System.out.println(p.getLeft() + " "+p.getRight());
        }
        List<String> trip = TTLParser.getAllTriples("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\httpwww.siemens.comsi-dsgraphswitchgear-level-interlockings.ttl");
        for(String string : trip){
            System.out.println("TRIPLE:" +string);
        }*/


        /*MultiValuedObject<String,String,String> ob = new FCAMultiValuedObject<>("1");
        MultiValuedAttribute<String,String,String> atr = new FCAMultiValuedAttribute<>("a");
        List<String> vals = new ArrayList<>();
        vals.add("super");
        vals.add("geil");
        vals.add("yesman");
        atr.addObject(ob, Collections.singletonList("stark"));
        ob.addAttribute(atr, vals);
        ob.addAttribute(atr, Collections.singletonList("stark"));
        ob.addAttribute(atr, Collections.singletonList("staascascark"));

        MultiValuedContext<String,String,String> context1 = new FCAMultiValuedFormalContext<>(){};
        context1.addMultiValuedObject(ob);
        System.out.println(context1.getContextObjects().size()+" "+ context1.getContextAttributes().size()+" "+context1.getValues().size());
        //context1.addMultiValuedAttribute(atr);
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
        }*/
        //Context<String,String> newcont = Computation.nominalScaleContext(context1);
        //OutputPrinter.printCrosstableToConsole(newcont);
        //MultiValuedContext<String,String,String> ttl = ContextHelper.createContextFromTTLFile("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\root-level-interlockings-simplified-interlocking-context.ttl");
        //System.out.println("ja");
        //System.out.println("OBJEKTE: "+ttl.getContextObjects().size());
        //System.out.println("ATTRIBUTE: "+ttl.getContextAttributes().size());
        //System.out.println("VALUES: "+ttl.getValues().size());/*
        //for(String s : ttl.getValues()){
           // System.out.println("val  "+s);
        //}
        /*for(MultiValuedObject<String,String,String> mva : ttl.getContextObjects()){
            System.out.println("ob  "+mva.getObjectID());
            for(Pair<MultiValuedAttribute<String,String,String>, List<String>> par : mva.getDualEntities()){
                System.out.println("ATR: "+par.getLeft().getAttributeID());
                System.out.println("VAL: "+par.getRight());
            }
        }*/

        /*for(MultiValuedObject<String,String,String> object : ttl.getContextObjects()){
            System.out.println("SUBJECT: "+object.getObjectID());
            for(Pair<MultiValuedAttribute<String,String,String>, List<String>> pair : object.getDualEntities()){
                System.out.println(pair.getLeft().getAttributeID()+" "+pair.getRight());
            }
        }*/
        //System.out.println("VALUES:"+ttl.getValues());

        //OutputPrinter.printMultiValuedTableToConsole(ttl);
        //Context<String,String> scaled = Computation.nominalScaleContext(ttl,true);
        //Computation.reduceContext(scaled);
        //OutputPrinter.printCrosstableToConsole(scaled);
        /*System.out.println("Anzahl der Gegenstände: "+scaled.getContextObjects().size());
        System.out.println("Anzahl der Merkmale: "+scaled.getContextAttributes().size());

        int count =0;
        for(ObjectAPI<String,String> o : scaled.getContextObjects()){
            count += o.getDualEntities().size();
        }
        System.out.println("KREUZE: "+ count);
        Set<String> set = new HashSet<>(scaled.getContextObjects().stream().map(ObjectAPI::getObjectID).collect(Collectors.toSet()));
        if(set.size() < scaled.getContextAttributes().size()){
            System.out.println("DOPPELT");
        }


        long a1 = Performance.setTimeStamp();
        OutputPrinter.writeStemBaseToFile(scaled,"easy.txt");
        long a2 = Performance.setTimeStamp();
        System.out.println("Execution Time: "+Performance.getExecutionTime(a1,a2));
        /*for(MultiValuedObject<String,String,String> o : ttl.getContextObjects()){
            System.out.println(o.getObjectID()+" "+o.getDualEntities().stream().map(Pair::getLeft).collect(Collectors.toList()).stream().map(MultiValuedAttribute::getAttributeID).collect(Collectors.toList()));
        }*/
        //OutputPrinter.printCrosstableToConsole(scaled);

        //Set<String> set = new HashSet<>(scaled.getContextAttributes().stream().map(Attribute::getAttributeID).collect(Collectors.toSet()));
        //if(set.size() < scaled.getContextAttributes().size()){
            //System.out.println("DOPPELT");
        //}

        /*
        MultiValuedContext<String,String,Integer> mvContext = new FCAMultiValuedFormalContext<>(){};
        MultiValuedObject<String,String,Integer> mvObject = new FCAMultiValuedObject<>("Stinkende Nieswurz");
        mvObject.addAttribute(new FCAMultiValuedAttribute<>("Anzahl der Blütenblätter"), Collections.singletonList(5));
        mvContext.addMultiValuedObject(mvObject);

        MultiValuedContext<String,String,String> con = new FCAMultiValuedFormalContext<String, String, String>() {};
        MultiValuedObject<String,String,String> o1 = new FCAMultiValuedObject<>("Alpen-Hahnenfuß");
        MultiValuedObject<String,String,String> o2 = new FCAMultiValuedObject<>("Flutender-Hahnenfuß");
        MultiValuedObject<String,String,String> o3 = new FCAMultiValuedObject<>("Gelber Hornmohn");
        MultiValuedObject<String,String,String> o4 = new FCAMultiValuedObject<>("Schöllkraut");
        MultiValuedObject<String,String,String> o5 = new FCAMultiValuedObject<>("Alpenveilchen");
        MultiValuedObject<String,String,String> o6 = new FCAMultiValuedObject<>("Wiesen-Kuhschelle");
        MultiValuedObject<String,String,String> o7 = new FCAMultiValuedObject<>("Feld-Rittersporn");
        MultiValuedObject<String,String,String> o8 = new FCAMultiValuedObject<>("Stinkende Nieswurz");

        MultiValuedAttribute<String,String,String> ar1 = new FCAMultiValuedAttribute<>("Blütezeit");
        MultiValuedAttribute<String,String,String> ar2 = new FCAMultiValuedAttribute<>("Wuchsgröße");
        MultiValuedAttribute<String,String,String> ar3 = new FCAMultiValuedAttribute<>("Blütenfarbe");
        MultiValuedAttribute<String,String,String> ar4 = new FCAMultiValuedAttribute<>("Anzahl der Blütenblätter");

        o1.addAttribute(ar1,Collections.singletonList("Mai-Sept."));
        o1.addAttribute(ar2,Collections.singletonList("5-15cm"));
        //o1.addAttribute(ar3,Collections.singletonList("weiß"));
        //o1.addAttribute(ar4,Collections.singletonList(">=5"));

        o2.addAttribute(ar1,Collections.singletonList("Juni-Aug."));
        o2.addAttribute(ar2,Collections.singletonList("60-600cm"));
        //o2.addAttribute(ar3,Collections.singletonList("weiß"));
        //o2.addAttribute(ar4,Collections.singletonList(">=5"));

        o3.addAttribute(ar1,Collections.singletonList("Juni-Juli"));
        o3.addAttribute(ar2,Collections.singletonList("20-80cm"));
        //o3.addAttribute(ar3,Collections.singletonList("gelb"));
        //o3.addAttribute(ar4,Collections.singletonList("<=4"));

        o4.addAttribute(ar1,Collections.singletonList("Mai-Sept."));
        o4.addAttribute(ar2,Collections.singletonList("30-70cm"));
        //o4.addAttribute(ar3,Collections.singletonList("gelb"));
        //o4.addAttribute(ar4,Collections.singletonList("<=4"));

        o5.addAttribute(ar1,Collections.singletonList("Juli-Sept."));
        o5.addAttribute(ar2,Collections.singletonList("5-15cm"));
        o5.addAttribute(ar3,Collections.singletonList("rot"));
        o5.addAttribute(ar4,Collections.singletonList(">=5"));

        o6.addAttribute(ar1,Collections.singletonList("Apr.-Mai"));
        o6.addAttribute(ar2,Collections.singletonList("10-50cm"));
        o6.addAttribute(ar3,Collections.singletonList("blau"));
        o6.addAttribute(ar4,Collections.singletonList(">=5"));

        o7.addAttribute(ar1,Collections.singletonList("Mai-Aug."));
        o7.addAttribute(ar2,Collections.singletonList("20-40cm"));
        o7.addAttribute(ar3,Collections.singletonList("blau"));

        o8.addAttribute(ar1,Collections.singletonList("Feb.-Apr."));
        o8.addAttribute(ar2,Collections.singletonList("30-60cm"));
        o8.addAttribute(ar3,Collections.singletonList("grün"));
        o8.addAttribute(ar4,Collections.singletonList("5"));

        con.addMultiValuedObject(o1);
        con.addMultiValuedObject(o2);
        con.addMultiValuedObject(o3);
        con.addMultiValuedObject(o4);
        //con.addMultiValuedObject(o5);
        //con.addMultiValuedObject(o6);
        //con.addMultiValuedObject(o7);
        //con.addMultiValuedObject(o8);

        Context<String,String> sc = Computation.nominalScaleContext(con,true);

        OutputPrinter.printCrosstableToConsole(sc);

        /*MultiValuedContext<String,String,String> kon = ContextHelper.createContextFromTTLFile("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\test.ttl");

        for(MultiValuedObject<String,String,String> o : kon.getContextObjects()){
            //System.out.println("Objekt ID: "+o.getObjectID());
            for(Pair<MultiValuedAttribute<String,String,String>, List<String>> p : o.getDualEntities()){
                //System.out.println("(Merkmal ID, Merkmalausprägungen): ("+p.getLeft().getAttributeID()+", "+p.getRight()+")");
            }
        }
        for(MultiValuedAttribute<String,String,String> atr: kon.getContextAttributes()) {
            for (ObjectAPI<String, Pair<String, Boolean>> o : Computation.dichotomScaleAttribute(kon.getContextAttributes().get(kon.getContextAttributes().indexOf(atr)))) {
                //System.out.println(kon.getContextAttributes().get(kon.getContextAttributes().indexOf(atr)).getAttributeID());
                //System.out.println(o.getObjectID());
                //System.out.println(o.getDualEntities());
            }
        }*/
        //OutputPrinter.printCrosstableToConsole(Computation.nominalScaleContext(kon,false));
        //OutputPrinter.printCrosstableToConsole(Computation.dichotomScaleContext(kon));


        /*MultiValuedContext<String,String,String> con = new FCAMultiValuedFormalContext<String, String, String>() {};
        MultiValuedObject<String,String,String> o1 = new FCAMultiValuedObject<>("Alpen-Hahnenfuß");
        MultiValuedObject<String,String,String> o2 = new FCAMultiValuedObject<>("Flutender-Hahnenfuß");
        MultiValuedObject<String,String,String> o3 = new FCAMultiValuedObject<>("Gelber Hornmohn");
        MultiValuedObject<String,String,String> o4 = new FCAMultiValuedObject<>("Schöllkraut");
        MultiValuedObject<String,String,String> o5 = new FCAMultiValuedObject<>("Alpenveilchen");
        MultiValuedObject<String,String,String> o6 = new FCAMultiValuedObject<>("Wiesen-Kuhschelle");
        MultiValuedObject<String,String,String> o7 = new FCAMultiValuedObject<>("Feld-Rittersporn");
        MultiValuedObject<String,String,String> o8 = new FCAMultiValuedObject<>("Stinkende Nieswurz");

        MultiValuedAttribute<String,String,String> ar1 = new FCAMultiValuedAttribute<>("Blütezeit");
        MultiValuedAttribute<String,String,String> ar2 = new FCAMultiValuedAttribute<>("Wuchsgröße");
        MultiValuedAttribute<String,String,String> ar3 = new FCAMultiValuedAttribute<>("Blütenfarbe");
        MultiValuedAttribute<String,String,String> ar4 = new FCAMultiValuedAttribute<>("Anzahl der Blütenblätter");

        o1.addAttribute(ar1,Collections.singletonList("Mai-Sept."));
        o1.addAttribute(ar2,Collections.singletonList("5-15cm"));
        o1.addAttribute(ar3,Collections.singletonList("weiß"));
        o1.addAttribute(ar4,Collections.singletonList(">=5"));

        o2.addAttribute(ar1,Collections.singletonList("Juni-Aug."));
        o2.addAttribute(ar2,Collections.singletonList("60-600cm"));
        o2.addAttribute(ar3,Collections.singletonList("weiß"));
        o2.addAttribute(ar4,Collections.singletonList(">=5"));

        o3.addAttribute(ar1,Collections.singletonList("Juni-Juli"));
        o3.addAttribute(ar2,Collections.singletonList("20-80cm"));
        o3.addAttribute(ar3,Collections.singletonList("gelb"));
        o3.addAttribute(ar4,Collections.singletonList("<=4"));

        o4.addAttribute(ar1,Collections.singletonList("Mai-Sept."));
        o4.addAttribute(ar2,Collections.singletonList("30-70cm"));
        o4.addAttribute(ar3,Collections.singletonList("gelb"));
        o4.addAttribute(ar4,Collections.singletonList("<=4"));

        o5.addAttribute(ar1,Collections.singletonList("Juli-Sept."));
        o5.addAttribute(ar2,Collections.singletonList("5-15cm"));
        o5.addAttribute(ar3,Collections.singletonList("rot"));
        o5.addAttribute(ar4,Collections.singletonList(">=5"));

        o6.addAttribute(ar1,Collections.singletonList("Apr.-Mai"));
        o6.addAttribute(ar2,Collections.singletonList("10-50cm"));
        o6.addAttribute(ar3,Collections.singletonList("blau"));
        o6.addAttribute(ar4,Collections.singletonList(">=5"));

        o7.addAttribute(ar1,Collections.singletonList("Mai-Aug."));
        o7.addAttribute(ar2,Collections.singletonList("20-40cm"));
        o7.addAttribute(ar3,Collections.singletonList("blau"));

        o8.addAttribute(ar1,Collections.singletonList("Feb.-Apr."));
        o8.addAttribute(ar2,Collections.singletonList("30-60cm"));
        o8.addAttribute(ar3,Collections.singletonList("grün"));
        o8.addAttribute(ar4,Collections.singletonList("5"));

        con.addMultiValuedObject(o1);
        con.addMultiValuedObject(o2);
        con.addMultiValuedObject(o3);
        con.addMultiValuedObject(o4);
        con.addMultiValuedObject(o5);
        con.addMultiValuedObject(o6);
        con.addMultiValuedObject(o7);
        con.addMultiValuedObject(o8);

        //OutputPrinter.printCrosstableToConsole(Computation.nominalScaleContext(con,false));
        OutputPrinter.printCrosstableToConsole(Computation.dichotomScaleContext(con));
        OutputPrinter.printStemBaseToConsole(Computation.dichotomScaleContext(con));


        MultiValuedContext<String,String,String> fromttl = ContextHelper.createContextFromTTLFile("C:\\Users\\lgeis\\Desktop\\Uni\\Bachelor-Thesis\\SiemensUseCase\\root.ttl");
        System.out.println(fromttl.getContextAttributes().size());
        System.out.println(fromttl.getContextObjects().size());
        //Context<String,String> nominalContext = Computation.dichotomScaleContext(fromttl);

        System.out.println("GEGENSTÄNDE: "+Computation.dichotomScaleContext(fromttl).getContextObjects().size());
        System.out.println("MERKMALE: "+Computation.dichotomScaleContext(fromttl).getContextAttributes().size());
        long a = Performance.setTimeStamp();
        OutputPrinter.writeStemBaseToFile(Computation.dichotomScaleContext(fromttl),"neu");
        System.out.println("EXEC TIME: "+Performance.convertToFormat(Performance.getExecutionTime(a,Performance.setTimeStamp())));*/

        Context<String,String> con = ContextHelper.createContextFromFile("C:\\Users\\lgeis\\Documents\\GitHub\\FCAlib2\\src\\main\\java\\lib\\utils\\output\\easy.txt");
        OutputPrinter.writeCrosstableToFile(con,"C:\\Users\\lgeis\\Desktop\\", "test");
        OutputPrinter.writeConceptsToFile(con,"C:\\Users\\lgeis\\Desktop\\","tests");
        OutputPrinter.writeStemBaseToFile(con,"C:\\Users\\lgeis\\Desktop\\","teststem");
        OutputPrinter.printStemBaseToConsole(con);



    }
}
