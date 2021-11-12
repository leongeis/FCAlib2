<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![Github top language][language-shield]][language-url]
[![GitHub all releases](https://img.shields.io/github/downloads/leongeis/FCAlib2/total)]

<!-- PROJECT LOGO -->
<h1 align="center">FCAlib2</h1>
<p align="center">
  <img width="300" height="280" src="https://github.com/leongeis/FCAlib2/blob/main/images/Logo.png">
  <h4 align="center">A library for FCA-Tool Developer.</h4>
</p>


> Is the Project finished ?
- [ ] Yes
- [X] No

> Is the README finished?
- [ ] Yes
- [X] Almost
- [ ] No

##### TODO
- [ ] Add possible removal of rows/columns
- [X] Use Data from DBPedia
- [X] Use Data from YAGO
- [X] Finish Performance Interface
- [x] Include Support of Implications

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Project Structure](#project-structure)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Tutorial](#tutorial)
  * [Creating an Object](#1creating-an-object-fcaobject)
  * [Creating an Attribute](#2creating-an-attribute-fcaattribute)
  * [Creating a Formal Context](#3creating-a-formal-context-fcaformalcontext)
  * [Interaction between Object and Attribute](#4interaction-between-fcaobject-and-fcaattribute)
  * [Interaction between Context and Attribute/Object](#5interaction-between-fcaformalcontext-and-fcaattributefcaobject)
  * [Using the Computation Interface](#6using-the-computation-interface)
  * [Extending the Project](#7extending)
* [Knowledge Graphs](#knowledge-graphs)
  * [Access a Knowledge Graph](#access-a-knowledge-graph)
  * [SPARQL](#sparql)
  * [Results](#results)
  * [Combining with FCA](#combining-with-fca)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)

<!-- ABOUT THE PROJECT -->
## About The Project

This project is an enhanced version of FCAlib originally developed by Baris Sertkaya. The aim of this project is to
develop an open-source library, which is easy to use and easy to extend.<br/>Furthermore, the project contains implementations
to access and query well known Knowledge Graphs like `Wikidata` or `YAGO`. Check the [Knowledge Graph](#knowledge-graphs) section for more 
information.

### Project Structure

The Project is divided into a [`api`](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/api) and a [`lib`](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/lib) part. The former contains all interfaces, and the latter
all classes implementing the interfaces. In the following we will explain each package
inside `api` and `lib` and their purpose.<br/>The implementation and how to use it is explained in the [Tutorial](#tutorial) section.

#### Packages from .api.*
* [api.fca](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/api/fca)
  > This package contains interfaces like `Attribute` or `Context` and the important `Computation` interface. These
  > interfaces represent the core functionalities of FCA. One can use them to, e.g., create a Context and compute the
  > Stem Base or compute the Prime of `Objects` or `Attributes` w.r.t. a Context.
  > <br/>Due to the existing java.lang.Object class, the interface for the Objects is called `ObjectAPI`. Thus, when
  > speaking of using the `Object` interface we always refer to the `ObjectAPI` interface.
* [api.utils](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/api/utils)
  > This package contains utility interfaces like `OutputPrinter` or `Performance`. The former can be used, e.g., to 
  > print the crosstable of a context to the console or write it to a file. Also interfaces, which can be used to query
  > Wikidata (`WikidataAccess`) or YAGO (`YAGOAccess`) can be found here. <br/>Moreover, the interface `ContextHelper` can be used to create a context by querying Wikidata, YAGO or DBPedia.
#### Packages from .lib.*

* [lib.fca](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/lib/fca)
  > This package contains all classes implementing the interfaces from the `api.fca` package. One can simply use them as
  > they are or even extend them, to match the desired properties. 
  > <br/>All classes in this package have the prefix `FCA` to distinguish them from the interfaces from the earlier
  > mentioned package. It is also good to mention that the class `FCAFormalContext` is abstract. This is done to save
  > the types of the `Objects` and `Attributes` of the context. 
* [lib.utils](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/lib/utils)
  > This package contains small utility classes like `IndexedList`, which indexes a given List. Important to mention here
  > is the fact, that all files created by the `OutputPrinter` interface are saved in `lib.utils.output`. Also, different 
  > exceptions like `NoPropertiesDefinedException` can be found in the subpackage `lib.utils.exceptions`.

### Built With

* []()
* []()
* []()



<!-- GETTING STARTED -->
## Getting Started

### Prerequisites
* Maven (3.6.3)
* Internet connection when querying a Knowledge Graph
* Enough space on the Hard Disk, when writing to file
### Installation
*
*
*
## Tutorial
The flavour of FCA comes into play, when using the different algorithms of FCA. However, before
we can start discussing the implemented algorithms, we first need a `Context` with `Attributes`
and `Objects` to perform these algorithms on. Hence, below we will first explain the creation of
`Objects`,`Attributes` and `Contexts`. Afterwards, we are going to show the connection between these
entities and how to use the implemented algorithms by using the `Computation` interface.
<br/>The steps below make use of the implemented classes in the [`lib`](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/lib) package. One
can also implement new classes, by simply implementing the corresponding interfaces or extending the existing
classes in the `lib.fca` package. Before we start it is save to mention here, that we will always  
 speak of creating an Object and hereby refer to our `ObjectAPI` interface and __not__ creating an object of type `java.lang.Object`. Steps on [how to extend](#7extending) the project are given at the end of the Tutorial.
#### 1.Creating an Object (`FCAObject`)
First we need to create an Object and specify the type of the Object itself (first type parameter)
, and the type of the Attributes the Object holds (second type parameter). Both the Object itself and
its Attributes here are of type `String`.
We provide the Constructor of the FCAObject Class with an ID for our new FCAObject `ob1`, which is here `"Q1"`.
<br/><br/>Note: The ID we provide has to be of the same type as the Object itself, which is here `String`.
```java
ObjectAPI<String,String> ob1 = new FCAObject<>("Q1");
```

#### 2.Creating an Attribute (`FCAAttribute`)
When creating a new Attribute, the type parameter are (similar to the ``ObjectAPI`` type parameter) first the type
of the Objects the Attribute holds and secondly the type of the Attribute itself. Again, both the Attribute itself
and its Objects are of type `String`.
We provide the Constructor of the FCAAttribute Class with an ID for our new FCAAttribute ``atr1``,
which is here ``"a"``.
<br/><br/>Note: Again, the ID we provide has to be of the same type as the Attribute itself (second parameter),which is here ``String``.
```java
Attribute<String,String> atr1 = new FCAAttribute<>("a");
```

<p align="center">
<img width="420" height="206" src="https://github.com/leongeis/FCAlib2/blob/main/images/parametertypepattern.png">
<h6 align="center">The pattern of the types is the same throughout the whole library.</h6>
</p>


#### 3.Creating a Formal Context (`FCAFormalContext`)
Similar to the Object and Attribute type parameter, the first type parameter specifies the type of the Objects (G) of
the context, and the second type parameter specifies the type of the Attributes (M) of the context. Here, both
the Objects and the Attributes of the context are of type `String`. Observe the two curved brackets at the end, which
always have to be placed there when creating a ``FCAFormalContext`` Object.
```java
Context<String,String> testContext = new FCAFormalContext<>(){};
```
<p align="center">
<img width="470" height="154" src="https://github.com/leongeis/FCAlib2/blob/main/images/contextattrobj.png">
<h6 align="center">The types of the Context and the Objects/Attributes have to match.</h6>
</p>


#### 4.Interaction between `FCAObject` and `FCAAttribute`
An Object without any Attribute describing the Object is almost worthless. Thus, below is the easiest and quickest way
to add an Attribute to an Object.
```java
ob1.addAttribute("a");
ob1.addAttribute("b");
```
Here the earlier created FCAObject `ob1` gets two new Attributes ("a" and "b") with the method `addAttribute()`. Because
we defined the type of the Attributes of `ob1` to be of type `String`, we can only add Strings as Attributes.


<p align="center">
<img width="236" height="99" src="https://github.com/leongeis/FCAlib2/blob/main/images/objectatt.png">
<h6 align="center">The Object stores the Attribute IDs in a List.</h6>
</p>


On the other hand, an Attribute without any corresponding Object is nothing but wasted memory. Hence, below the easiest and
the quickest way to add an Object to an Attribute.
```java
atr1.addObject("Q1");
atr1.addObject("Q2");
```

<p align="center">
<img width="236" height="99" src="https://github.com/leongeis/FCAlib2/blob/main/images/attrobject.png">
<h6 align="center">The Attribute stores the Object IDs in a List.</h6>
</p>

Here the earlier created FCAAttribute `atr1` gets two Objects assigned ("Q1" and "Q2").
<br/>Important to mention here is that we do not add the Object (``ob1``) to the Attribute, but rather the ID
we have given this Object, which is ``"Q1"``. This is also the case when adding an Attribute to an Object. Thus, we could
add arbitrary IDs to an Attribute or an Object. Nevertheless, in the following, we will see why we need Objects and
Attributes with the corresponding IDs, when adding them to a ``Context`` and using the `Computation` interface.

One might have already recognized the close connection between `Attribute` and `Object`. This becomes
even more obvious when considering the other methods implemented in the classes
[FCAAttribute](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/lib/fca/FCAAttribute.java) and
[FCAObject](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/lib/fca/FCAObject.java).
#### 5.Interaction between `FCAFormalContext` and `FCAAttribute/FCAObject`
One can add Attributes and Objects to a context. Since every `FCAFormalContext` object manages a List of `Attributes`
and ``Objects``, it is possible to add objects of subclasses of `FCAObject` and `FCAAttribute` or simply all
Objects of classes, implementing the according interfaces. Adding an Object will also add all
of its Attributes to the context, dually for Attributes. These newly created ``Attributes`` are always of type `FCAAttribute`.
```java
testContext.addObject(ob1);
testContext.addObject(ob2);
testContext.addObject(ob3);
```
Here we add the FCAObjects `ob1`,`ob2` and `ob3` to the earlier created FCAFormalContext Object. Again, the Attributes
of each individual Object are also added to the context. Thus, one could also use the method `addAttribute()`. However,
this method creates ``FCAObject`` objects and adds them to the Context, if an object is not present in the Context object.
<p align="center">
<img width="600" height="342" src="https://github.com/leongeis/FCAlib2/blob/main/images/testcontext.png">
<h6 align="center">The created testContext Object after the first addObject(ob1) invocation.</h6>
</p>

Here we can see, that each ID (either Attribute or Object) needs to have a corresponding object using that
ID. This is due to the fact, that each `Context` object stores a List of Attributes and a List of Objects. When
invoking methods from the `Computation` interface these Lists will be used for the input of a computation, as well as
the `Context` object itself to, e.g., compute the Prime of Objects.
To add other Objects/Attributes without the creation of ``FCAObject``/`FCAAttribute` objects, see the [Extending](#7extending) section.

#### 6.Using the Computation Interface
The usefulness of FCA comes from the existing algorithms. There is an implemented approach for using these. We can simply
invoke each method of the [`Computation`](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/api/fca/Computation.java) interface, because all of them are static. The most basic operation in FCA
is the computation of the Prime of either a set of `Objects` or a set of `Attributes`. Because the underlying data struture
is a List, we can simply provide these methods with the corresponding List instead of a Set.  
<br/>Lets say we want to get
the Prime of all Objects of a Context. We simply invoke the method `computePrimeOfObjects` of the Computation interface and  
pass all Objects of the Context as well as the corresponding Context object.
```java
//Note that the return type here is ignored
Computation.computePrimeOfObjects(testContext.getContextObjects(),testContext);
```
This can dually be done with the Attributes by using the `computePrimeOfAttributes` method. One can also
compute all concepts or compute the Stem Base of a Context by using the corresponding methods.

```java
//Computing all Concepts of a Context
Computation.computeAllConcepts(testContext);
//Computing the Stem Base of a Context
Computation.computeStemBase(testContext);
```
All other methods from the `Computation` interface have a similar signature and are
fairly easy to use. The question that arrises now is, what to do with the
computed results. One can use the [`OutputPrinter`](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/api/utils/OutputPrinter.java) interface from the `api.utils` package,
to print these results to the console.
Therefore, below is the usage of the `OutputPrinter` interface
with our created Context object `testContext`.

```java
//Print Crosstable and all Concepts of testContext
OutputPrinter.printCrosstableToConsole(testContext);
OutputPrinter.printConceptsToConsole(testContext);
```
Lets say we have provided our `testContext` object with a few more Objects, in the same
way as described above. Then a possible output of these methods would be:
```
The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute
   b d e c a 
Q1 X X - - - 
Q2 X - X - - 
Q3 - - - X - 
Q4 X - - X X 
Q5 - X - - - 
Q6 X - - X - 
Q7 - - X - - 
```
Note that the format, when using the methods from the `OutputPrinter` interface
is always the same.
```
Concepts (A,B) with A⊆G and B⊆M. G is the set of all Objects and M the set of all Attributes.
CONCEPT:[Q1, Q2, Q3, Q4, Q5, Q6, Q7];[]
CONCEPT:[Q3, Q4, Q6];[c]
CONCEPT:[Q2, Q7];[e]
CONCEPT:[Q1, Q5];[d]
CONCEPT:[Q1, Q2, Q4, Q6];[b]
CONCEPT:[Q4, Q6];[b, c]
CONCEPT:[Q4];[a, b, c]
CONCEPT:[Q2];[b, e]
CONCEPT:[Q1];[b, d]
CONCEPT:[];[b, d, e, c, a]
```
When using the `OutPutPrinter` interface for computing all concepts, the algorithm `NextClosure` is used. One can also use an naive implementation
of computing all concepts by invoking the method `computeAllConcepts` from the `Computation` interface and simply passing it the Context object.

Now we also want to print all Implications of the Stem Base to the Console,
but we do not want to use the method from the `OutPutPrinter` interface. We
can simply invoke the method, as done above, and iterate through each Implication.
```java
//Note that the type of the Implications have to match those of the Attributes of the Context
for(Implication<String,String> impl : Computation.computeStemBase(testContext)){
            System.out.println(impl.toString()+": "+Computation.computeImplicationSupport(impl,testContext));
        }
```
The code above even prints the support for each implication to the console, by invoking the `computeImplicationSupport` method and
passing it the current Implication, as well as the Context Object. The result of this code is the following:
```
[a]->[b, c]: 0.14285715
[c, e]->[b, d, e, c, a]: 0.0
[c, d]->[b, d, e, c, a]: 0.0
[e, d]->[b, d, e, c, a]: 0.0
```
Note that the implications, which have as their conclusion (right part) all Attributes of the Context, are actually
implying no Attribute rather then the whole set of Attributes. Then the support is always 0, but every intent is a model of
them, so they are vacously fulfilled.
<br/>The support for the Implications is always in a range between 0 and 1. 0 meaning there is no intent, which fulfills
this Implication and 1 meaning every intent fulfills this Implication.

#### 7.Extending
The usefulness of this library comes also from the fairly easy option to extend existing classes or implement interfaces.
Lets say we want to create a new Attribute class called `MyAttribute`.
```
public class MyAttribute<O,A> implements Attribute<O,A> {
    
    A ID;
    
    List<O> objects;
    
    //Note that these methods are not implemented properly
    @Override
    public A getAttributeID() {
        return null;
    }

    @Override
    public void setAttributeID(A id) {

    }

    @Override
    public void addObject(O object) {

    }

    @Override
    public List<?> getDualEntities() {
        return null;
    }
}
```
Important is of course a meaningful implementation of the methods, which is not given above, and the fields for the
`ID` and the corresponding `List of Objects`. This is dually the case when creating a new `ObjectAPI` class. But besides
these points one can create arbitrary classes and use the implemented Algorithms in `Computation` or `OutPutPrinter`. To get an
idea on how to implement these, one can check out the implementations in [FCAAttribute](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/lib/fca/FCAAttribute.java) and
[FCAObject](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/lib/fca/FCAObject.java).
<br/>Now one can create different Objects, Attributes or Contexts, as long as the interfaces are implemented.
<br/>Lets assume we have implemented the methods properly and also created a new class implementing the `ObjectAPI` interface.
```
public class MyObject<O,A> implements ObjectAPI<O,A>{ //Following a proper implementation
```
We can now even add them to our existing `Context` Object `testContext`.
```
//Create a new Attribute
Attribute<String,String> attribute = new MyAttribute<>("g");

//Add an Object to the Attribute object
attribute.addObject("Q8");

//Add it to the earlier created testContext
testContext.addAttribute(attribute);
```
Note again the matching type of the ``MyAttribute`` object `attribute` and our `testContext`.
Printing the crosstable now, using the `OutPutPrinter` interface gives us the following crosstable:
```
The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute
   b d e c a g 
Q1 X X - - - - 
Q2 X - X - - - 
Q3 - - - X - - 
Q4 X - - X X - 
Q5 - X - - - - 
Q6 X - - X - - 
Q7 - - X - - - 
Q8 - - - - - X 
```
We can see that the new Attribute with ID `g` and Object `Q8` has been added successfully to our Context.
</br>However,
adding an Attribute this way will always create a `FCAObject` and add it to the List of Objects in the Context,
since we use the method `addObject`. An alternative approach to add Attributes to a Context is the following:
```
//After the creation of the new Attribute
testContext.getContextAttributes().add(attribute);
```
The disadvantage of this approach is the following:
```
The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute
   b d e c a g 
Q1 X X - - - - 
Q2 X - X - - - 
Q3 - - - X - - 
Q4 X - - X X - 
Q5 - X - - - - 
Q6 X - - X - - 
Q7 - - X - - - 
```
The Object `Q8` of our newly created Attribute with the ID `g` is not added to the Context. This means
we have to create and add the Object manually to the Context.
```
//Create new Object of our new MyObject class
ObjectAPI<String,String> object = new MyObject<>("Q8");

//Add the corresponding Attribute to the Object
object.addAttribute("g");

//And finally also add this new Object to the Context
testContext.getContextObjects().add(object);
```
When choosing a proper implementation of the `MyObject` class and and invoking the earlier used method
from the `OutPutPrinter` interface, the crosstable of our Context object `testContext` looks like this:
```
The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute
   b d e c a g 
Q1 X X - - - - 
Q2 X - X - - - 
Q3 - - - X - - 
Q4 X - - X X - 
Q5 - X - - - - 
Q6 X - - X - - 
Q7 - - X - - - 
Q8 - - - - - X 
```
Finally the Object and the Attribute are both stored in our Context Object. One can even compute the Prime
of this newly created Object by, e.g., the following approach:
```
for(Attribute<String,String> atr : Computation.computePrimeOfObjects(Collections.singletonList(object),testContext)){
            System.out.println("Attribute of "+object.getObjectID()+" is "+atr.getAttributeID());
        }
```
Note the ``Collections.singletonList`` invokation due to the fact that ``computePrimeOfObjects`` expects a ``List``
of Objects and not a single Object.
The Output of the code above is the following:
```
Attribute of Q8 is g
```
The mentioned steps above are only examples and one can easily extend them and use the computed results in, e.g., an
application. For further information check out the interfaces in [`api.fca`](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/api/fca)  
and [`api.utils`](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/api/utils) and the example implementations  
in [`lib.fca`](https://github.com/leongeis/FCAlib2/tree/main/src/main/java/lib/fca).
## Knowledge Graphs
The scope of this project not only covers FCA itself, but also Knowledge Graphs.
Simply speaking, Knowledge Graphs are a collection of knowledge in form of a graph.
Famous examples for already existing Knowledge Graphs are, e.g., Wikidata and DBPedia.
To access this knowledge, one can either download a dump from the website of the specific
Knowledge Graph or query its SPARQL-Endpoint. The results from both of the mentioned approaches can also
be of different types like, e.g., XML or JSON. However, the question, which arises now is how to combine
these Knowledge Graphs and FCA. This is where FCAlib2 comes into play.<br/>In the following, we will show how
to access and query a Knowledge Graph and how to combine the delivered results with methods from FCA. Note, that
we make extensive use of the RDF4J framework. For more information on the framework see [https://rdf4j.org/](https://rdf4j.org/).
<!-- Wikidata -->
### Access a Knowledge Graph
Before we can query a Knowledge Graph we first need to access it. By accessing a Knowledge Graph we always
refer to accessing the corresponding SPARQL-Endpoint. This is done by using the `SPARQLEndpointAccess` interface, which  
is implemented by the class `KnowledgeGraphAccess`. Hence, we need, at least in our case, one could also extend it or use an
inherent class, to create an `KnowledgeGraphAccess` object and provide as a parameter the specific URL of the SPARQL-Endpoint. A possible approach can be seen below.
```java
SPARQLEndpointAccess wikidataAccess = new KnowledgeGraphAccess("https://query.wikidata.org/sparql");
```
Here we use the URL of the Wikidata Endpoint. One can also use other Endpoint URLs like, e.g., https://dbpedia.org/sparql for
the DBPedia Endpoint or https://yago-knowledge.org/sparql for the YAGO Endpoint.
<br/> Nevertheless, before performing queries on that endpoint, we need to establish a connection to the corresponding
repository.
```java
wikidataAccess.establishConnection();
```
Now we can start to perform queries on that endpoint and further utilize the given results. It is also important to mention here, that  
there can only be one active connection to the same repository at a time. One can use the `closeConnection()` method for this purpose. For more information, check out
the class used above [`KnowledgeGraphAccess`](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/lib/utils/KnowledgeGraphAccess.java)
and the [RDF4J](https://rdf4j.org/) framework.
### SPARQL
There is something all queries on those endpoints have in common, that is the language they are written in, which is SPARQL. Hence, this
section is on how to create SPARQL Queries with the implemented methods. Note that one can also write own SPARQL queries and passing them
as a String to the corresponding `KnowledgeGraphAccess` object.
For more information on how to write SPARQL queries, check out [https://www.w3.org/TR/rdf-sparql-query/](https://www.w3.org/TR/rdf-sparql-query/).
<br/>Nevertheless, we implemented a few methods in the `SPARQLQueryGenerator` interface, which can be used directly or one can also use the `SPARQLQueryBuilder` class,
which enables the creation of simple and generic SPARQL queries.
```java
String query = new SPARQLQueryBuilder().select().
                variable("*").where().subject("?s").predicate("owl:sameAs").
                object("?o").end().limit(100).build();
```
Here, we used the `SPARQLQueryBuilder` class and a few of the available building blocks. Note that, when using this
approach the last method call is always `.build()`. Printing the String `query` yields the following output:
```
SELECT * WHERE { ?s owl:sameAs ?o. }LIMIT 100
```
The approach, using the `SPARQLQueryGenerator` interface can be seen below:
```java
String query = SPARQLQueryGenerator.generateSelectSameAsQuery("http://www.wikidata.org/entity/Q42");
```
Now, we print the String `query` and the result can be seen below:
```
SELECT * WHERE { ?s owl:sameAs <http://www.wikidata.org/entity/Q42>.}
```
Note that only a few restricted cases are implemented in the `SPARQLQueryGenerator` interface. For more
information check out the [`SPARQLQueryGenerator`](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/api/utils/SPARQLQueryGenerator.java)  
interface and the [`SPARQLQueryBuilder`](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/lib/utils/SPARQLQueryBuilder.java) class.

### Results
We have seen how to use the implemented approaches on creating a SPARQL query. However, we also need
to evaluate the results we get from the SPARQL-Endpoint. But before getting results from any
arbitrary endpoint, we need to query it.  
<br/>Hence, below is the implemented approach to query an endpoint with
a `SELECT` query.
```java
//Create List of properties
List<String> properties = new ArrayList<>();

//Add property P21 and P25 from wikidata to the list
properties.add("http://www.wikidata.org/prop/direct/P21");
properties.add("http://www.wikidata.org/prop/direct/P25");

//Generate Query and set limit to 10
String query = SPARQLQueryGenerator.generateSelectUnionQuery(properties,10);

//Perform SELECT query and save result
TupleQueryResult result = wikidataAccess.selectQuery(query);
```

Here we used our earlier created `wikidataAccess` object and performed a `SELECT` query. The result of
this query is now stored in the `TupleQueryResult` object `result`.  
Furthermore, the object `result` contains the result in form of tuples, which we need to iterate.
<p align="center">
<img width="600" height="222" src="https://github.com/leongeis/FCAlib2/blob/main/images/wikidataresult.PNG">
<h6 align="center">The result we get when using the Wikidata Query Service.</h6>
</p>

The figure above illustrates not only how the `Wikidata Query Service` delivers the result of the query, but also
how the result of the query is stored in the `TupleQueryResult` object. In fact, we need to iterate over each tuple,
which consists here of the variables `s` and `o` and their corresponding values.

```java
//Store the binding Names of the result (here only s and o)
List<String> bindingNames = result.getBindingNames();

//Iterate over each tuple and print the values from s and o
for(BindingSet bindingSet : result){
    System.out.println("s: "+bindingSet.getValue(bindingNames.get(0))+" o: "+bindingSet.getValue(bindingNames.get(1)));
}
```

The result has an internal List of `BindingSets`, which we iterate and print the values, making use
of the stored List `bindingNames`. The output of the for-loop above is the following:

```
s: http://www.wikidata.org/entity/Q82356 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q138153 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q155695 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q170379 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q171433 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q182790 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q193115 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q218422 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q223025 o: http://www.wikidata.org/entity/Q43445
s: http://www.wikidata.org/entity/Q269349 o: http://www.wikidata.org/entity/Q43445
```
One can see that these results are equivalent with the results delivered from the
`Wikidata Query Service`.


<p align="center">
<img width="700" height="423" src="https://github.com/leongeis/FCAlib2/blob/main/images/resultdiagram.png">
<h6 align="center">The used terms explained on the result from the Wikdata Query Service.</h6>
</p>


Furthermore, the approach above is only an example on how to
evaluate the result and compute it further. Again, the interested reader is refered to
the [`RDF4J`](https://rdf4j.org/) framework. The remaining question is now, how we can
combine these results with methods from FCA, which is explained in the following section.

### Combining with FCA
We have seen above how to access a SPARQL-Endpoint, generate a query and get the results from the
corresponding endpoint. Now, there is only one question left to answer and that is how do we
combine these results with FCA. This will be explained in this section, starting with the usage
of the `ContextHelper` interface. Afterwards, we will show how to manually create a `Context` object with data from a result.

When using the `ContextHelper` interface, one can easily create a context from, e.g., ``Wikidata`` using the
`createContextFromWikidata` method.

```java
//Create a new Context object
Context<String,String> wikidataContext = new FCAFormalContext<String, String>(){};

//Get the URI of a property class (here property for items about people)
String propertyClass = "http://www.wikidata.org/entity/Q18608871";

//"fill" the context with results from the query
wikidataContext = ContextHelper.createContextFromWikidata(wikidataContext,propertyClass,100);
```

The steps above show how to use the mentioned method `createContextFromWikidata`. First, we start with the creation
of a new `Context` object. Note that the types for the `Objects` and `Attributes` here have to be of type `String` to use the
method. Aferwards, we save the URI of a property class, which is here the property class for all items about people. Then
we use this `Context` object, the String of the property class and a limit for queried items as parameters for `createContextFromWikidata`.
Internally, this method uses all properties from the given class, which is here https://www.wikidata.org/entity/Q18608871, and
simply queries all items, which have at least one of those properties as a predicate. The limit of queried items here is set to 100.
Additionally, the `ContextHelper` interface also contains another method, which enables the creation of a Context from any SPARQL-Endpoint. This
approach is not discussed further, but one can check out the [interface](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/api/utils/ContextHelper.java) for more information.
<br/>Lets simply print the `Crosstable` of the `Context` object `wikidataContext` and see, if this approach worked.

```
                                         http://www.wikidata.org/prop/direct/P19 http://www.wikidata.org/prop/direct/P20 http://www.wikidata.org/prop/direct/P21 http://www.wikidata.org/prop/direct/P27 http://www.wikidata.org/prop/direct/P39
http://www.wikidata.org/entity/Q76999    X                                       X                                       X                                       X                                       -                                                     
http://www.wikidata.org/entity/Q91207    X                                       X                                       X                                       X                                       X                                                     
http://www.wikidata.org/entity/Q96410    X                                       X                                       X                                       X                                       -                                                        
http://www.wikidata.org/entity/Q198276   X                                       X                                       X                                       X                                       -                                                      
http://www.wikidata.org/entity/Q320618   X                                       X                                       X                                       X                                       -                                                                          
http://www.wikidata.org/entity/Q351904   X                                       X                                       X                                       X                                       X                                                                 
http://www.wikidata.org/entity/Q388194   X                                       X                                       X                                       -                                       -                                                        
http://www.wikidata.org/entity/Q458783   X                                       X                                       X                                       X                                       -                                                            
http://www.wikidata.org/entity/Q550616   -                                       X                                       X                                       X                                       -                                                          
http://www.wikidata.org/entity/Q619317   -                                       X                                       X                                       X                                       X                                                           
http://www.wikidata.org/entity/Q630425   X                                       X                                       X                                       X                                       -                                           

```

This output is just an excerpt of the crosstable, because the crosstable itself would be too large to display here. Lets print then
the amount of `Objects` and `Attributes` of the context to verify the result.

```java
System.out.println("Amount of Objects: "+wikidataContext.getContextObjects().size());
System.out.println("Amount of Attributes: "+wikidataContext.getContextAttributes().size());
```
This code yields the following output:

```
Amount of Objects: 100
Amount of Attributes: 187
```

Hence, we can see that the context has in fact, 100 `Objects` and 187 `Attributes`. Now one can use any method and approach
explained earlier in the [Tutorial](#tutorial) section.
<br/>Furthermore, this is not the only approach available to create a `Context` object from results of a query. One can simply
iterate over each `BindingSet` of a result and, e.g., add the corresponding values to the `List of Objects` or to the
`List of Attributes` of a Context.  
<br/>Lets take the approach from earlier:

```java
//Create a List of properties
List<String> properties = new ArrayList<>();
properties.add("http://www.wikidata.org/prop/direct/P21");
properties.add("http://www.wikidata.org/prop/direct/P25");

//Generate SPARQL query and set the limit to 10
String query = SPARQLQueryGenerator.generateSelectUnionQuery(properties,10);

//Create KnowledgeGraphAccess Object
SPARQLEndpointAccess wikidataAccess = new KnowledgeGraphAccess("https://query.wikidata.org/sparql");

//Establish the connection
wikidataAccess.establishConnection();

//Perform the query
TupleQueryResult result = wikidataAccess.selectQuery(query);

//Save the Bindings
List<String> bindings = result.getBindingNames();

//Create Context Object and store the Attributes
Context<String,String> sparqlContext = new FCAFormalContext<String, String>() {};
sparqlContext.addAttribute(new FCAAttribute<>("http://www.wikidata.org/prop/direct/P21"));
sparqlContext.addAttribute(new FCAAttribute<>("http://www.wikidata.org/prop/direct/P25"));

//Iterate over the results and save each subject as an object for the context
for(BindingSet bindingSet : result){
    sparqlContext.addObject(new FCAObject<>(bindingSet.getValue(bindings.get(0)).stringValue()));
}
```

Now to see if this approach worked, let us just print the crosstable of the our newly created `Context` object `sparqlContext`, by
using the `OutputPrinter` interface, as explained in the [Tutorial](#tutorial) section.
This will yield the following crosstable:

```
                                       http://www.wikidata.org/prop/direct/P21 http://www.wikidata.org/prop/direct/P25 
http://www.wikidata.org/entity/Q82356  -                                       -                                       
http://www.wikidata.org/entity/Q138153 -                                       -                                       
http://www.wikidata.org/entity/Q155695 -                                       -                                       
http://www.wikidata.org/entity/Q170379 -                                       -                                       
http://www.wikidata.org/entity/Q171433 -                                       -                                       
http://www.wikidata.org/entity/Q182790 -                                       -                                       
http://www.wikidata.org/entity/Q193115 -                                       -                                       
http://www.wikidata.org/entity/Q218422 -                                       -                                       
http://www.wikidata.org/entity/Q223025 -                                       -                                       
http://www.wikidata.org/entity/Q269349 -                                       - 
```

One can clearly see, that this crosstable is not accurate and lacks the important
`Incidence` between `Objects` and `Attributes`. Hence, we could now query the SPARQL-Endpoint,
about the incidence of each Object and the listed Attributes. We will now generate
a query for each `Object` of the `Context` object and check if this object
has an incidence with the given `Attributes`.

```java
for(ObjectAPI<String,String> object : sparqlContext.getContextObjects()) {
    
    //Generate query for this object and the attributes of the Context
    //Making use of the Stream API to get all Attribute IDs as a List
    String objectQuery = SPARQLQueryGenerator.
                 generateSelectPropertyCheckQuery(object.getObjectID(),sparqlContext.getContextAttributes().stream().
                 map(Attribute::getAttributeID).collect(Collectors.toList()));

    //Get the result, using the earlier created TupleQueryResult object
    result = wikidataAccess.selectQuery(objectQuery);

    //Save the Bindings
    List<String> bindingNames = r.getBindingNames();

    //Iterate over the results and add the corresponding incidence
    for(BindingSet bindingSet : r){
        object.addAttribute(bindingSet.getValue(bindingNames.get(0)).stringValue());
    }
}
```

Printing the crosstable now yields the following output:

```
                                       http://www.wikidata.org/prop/direct/P21 http://www.wikidata.org/prop/direct/P25 
http://www.wikidata.org/entity/Q82356  X                                       -                                       
http://www.wikidata.org/entity/Q138153 X                                       -                                       
http://www.wikidata.org/entity/Q155695 X                                       -                                       
http://www.wikidata.org/entity/Q170379 X                                       X                                       
http://www.wikidata.org/entity/Q171433 X                                       -                                       
http://www.wikidata.org/entity/Q182790 X                                       -                                       
http://www.wikidata.org/entity/Q193115 X                                       X                                       
http://www.wikidata.org/entity/Q218422 X                                       -                                       
http://www.wikidata.org/entity/Q223025 X                                       X                                       
http://www.wikidata.org/entity/Q269349 X                                       -   
```

Finally, we have a context with `Objects`, `Attributes` and their corresponding `Incidence`. Note that this
is only an example and there are many possibilities to create a `Context` from an `SPARQL-Endpoint`. The key here
are always the formulated queries and how one operates with the results of such a query. Note that this is actually not the
only approach one could use. One can also use an own implementation to access a Knowledge Graph. This implementation is heavily
based on the [RDF4J](https://rdf4j.org/) framework, but there are also other framework one could easily use to achieve the same results, like e.g.,
[Apache Jena](https://jena.apache.org/) or the [OWL API](https://github.com/owlcs/owlapi).

<!-- CONTRIBUTING -->
## Contributing


<!-- LICENSE -->
## License


<!-- CONTACT -->
## Contact

Leon Geis - [lgeis@stud.fra-uas.de](lgeis@stud.fra-uas.de)

Project Link: [https://github.com/leongeis/FCAlib2](https://github.com/leongeis/FCAlib2)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* []()
* []()
* []()


<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/leongeis/FCAlib2
[contributors-url]: https://github.com/leongeis/FCAlib2/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/leongeis/FCAlib2
[forks-url]: https://github.com/leongeis/FCAlib2/network/members
[stars-shield]: https://img.shields.io/github/stars/leongeis/FCAlib2
[stars-url]: https://github.com/leongeis/FCAlib2/stargazers
[issues-shield]: https://img.shields.io/github/issues/leongeis/FCAlib2
[issues-url]: https://github.com/leongeis/FCAlib2/issues
[license-shield]: https://img.shields.io/github/license/leongeis/FCAlib2
[license-url]: https://github.com/leongeis/FCAlib2/blob/main/LICENSE
[language-shield]: https://img.shields.io/github/languages/top/leongeis/FCAlib2
[language-url]: https://github.com/leongeis/FCAlib2/search?l=java
