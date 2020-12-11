<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![Github top language][language-shield]][language-url]

<!-- PROJECT LOGO -->
<h1 align="center">FCAlib2</h1>
<p align="center">
  <img width="300" height="280" src="https://github.com/leongeis/FCAlib2/blob/main/images/Logo.png">
  <h4 align="center">A library for FCA-Tool Developer.</h4>
</p>


> Is the Project finished ?
- [ ] Yes
- [x] No

> Is the README finished?
- [ ] Yes
- [x] No

##### TODO
- [ ] Use Data from DBPedia
- [ ] Use Data from YAGO
- [ ] Finish Performance Interface
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
  * [Wikidata](#wikidata)
  * [DBPedia](#dbpedia)
  * [YAGO](#yago)
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

The Project is divided into a `api` and a `lib` part. The former contains all interfaces, and the latter
all classes implementing the interfaces. In the following we will explain each package
inside `api` and `lib` and their purpose.<br/>The implementation and how to use it is explained in the Tutorial section.

#### Packages from .api.*
* [api.fca](#api.fca) 
  > This package contains interfaces like `Attribute` or `Context` and the important `Computation` interface. These
  > interfaces represent the core functionalities of FCA. One can use them to, e.g., create a Context and compute the
  > Stem Base or compute the Prime of `Objects` or `Attributes` w.r.t. a Context.
  > <br/>Due to the existing java.lang.Object class, the interface for the Objects is called `ObjectAPI`. Thus, when
  > speaking of using the `Object` interface we also mean the `ObjectAPI` interface.
* [api.utils](#api.utils)
  > This package contains utility interfaces like `OutputPrinter` or `Performance`. The former can be used, e.g., to 
  > print the crosstable of a context to the console or write it to a file. Also interfaces, which can be used to query
  > Wikidata (`WikidataAccess`) or YAGO (`YAGOAccess`) can be found here.
#### Packages from .lib.*
* [lib.dbpedia](#lib.dbpedia)
  >
* [lib.fca](#lib.fca)
  > This package contains all classes implementing the interfaces from the `api.fca` package. One can simply use them as
  > they are or even extend them, to match the desired properties. 
  > <br/>All classes in this package have the prefix `FCA` to distinguish them from the interfaces from the earlier
  > mentioned package. It is also good to mention that the class `FCAFormalContext` is abstract. This is done to save
  > the types of the `Objects` and `Attributes` of the context. 
* [lib.utils](#lib.utils)
  > This package contains small utility classes like `IndexedList`, which indexes a given List. Important to mention here
  > is the fact, that all files created by the `OutputPrinter` interface are saved in `lib.utils.output`. Also, different 
  > exceptions like `NoPropertiesDefinedException` can be found in the subpackage `lib.utils.exceptions`.
  > <br/>Moreover, the class `ContextHelper` can be used to create a context by querying Wikidata, YAGO or DBPedia.
* [lib.wikidata](#lib.wikidata)
  > This package contains the classes implementing the `WikidataAccess` and `WikidataSPARQLQueryBuilder` interface.
  > Additionally, different example `SPARQL` queries can be found in the subpackage `lib.wikidata.queries`.
* [lib.yago](#lib.yago)
  >
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
<br/>The steps below make use of the implemented classes in the `lib` package. One
can also implement new classes, by simply implementing the corresponding interfaces or extending the existing
classes in the `lib.fca` package. Steps on how to extend the project are given at the end of the Tutorial.
#### 1.Creating an Object (`FCAObject`)
First we need to create an Object and specify the type of the Object itself (first type parameter)
, and the type of the Attributes the Object holds (second type parameter). Both the Object itself and
its Attributes here are of type `String`.
We provide the Constructor of the FCAObject Class with an ID for our new FCAObject `ob1`, which is here `"Q1"`.
<br/><br/>Note: The ID we provide, and the type of the Object itself have to be same, here `String`.
```java
ObjectAPI<String,String> ob1 = new FCAObject<>("Q1");
```

#### 2.Creating an Attribute (`FCAAttribute`)
When creating a new Attribute, the type parameter are (similar to the ``ObjectAPI`` type parameter) first the type
of the Attribute itself and secondly the type of the Objects the Attribute holds. Again, both the Attribute itself
and its Objects are of type `String`.
We provide the Constructor of the FCAAttribute Class with an ID for our new FCAAttribute ``atr1``,
which is here ``"a"``.
<br/>Note: Again, both the ID we provide, and the type of the Attribute itself (first parameter) have to be the same, here ``String``.
```java
Attribute<String,String> atr1 = new FCAAttribute<>("a");
```
#### 3.Creating a Formal Context (`FCAFormalContext`)
Similar to the Object and Attribute type parameter, the first type parameter specifies the type of the Objects (G) of
the context, and the second type parameter specifies the type of the Attributes (M) of the context. Here, both
the Objects and the Attributes of the context are of type `String`. Observe the two curved brackets at the end, which
always have to be placed there when creating a ``FCAFormalContext`` Object.
```java
Context<String,String> testContext = new FCAFormalContext<>(){};
```
#### 4.Interaction between `FCAObject` and `FCAAttribute`
An Object without any Attribute describing the Object is almost worthless. Thus, below is the easiest and quickest way
to assign an Attribute to an Object.
```java
ob1.addAttribute("a");
ob1.addAttribute("b");
```
Here the earlier created FCAObject `ob1` gets two new Attributes ("a" and "b") with the method `addAttribute()`, because
we defined the type of the Attributes of `ob1` to be of type `String`, we can only add Strings as Attributes.


<p align="center">
<img width="420" height="176" src="https://github.com/leongeis/FCAlib2/blob/main/images/objectatt.png">
<h6 align="center">The Object stores the Attribute IDs in a List.</h6>
</p>


On the other hand, an Attribute without any corresponding Object is nothing but wasted memory. Hence, below the easiest and
the quickest way to assign an Object to an Attribute.
```java
atr1.addObject("Q1");
atr1.addObject("Q2");
```

<p align="center">
<img width="420" height="176" src="https://github.com/leongeis/FCAlib2/blob/main/images/attrobject.png">
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
Here we add the FCAObjects `ob1`,`ob2` and `ob3` to the earlier created FCAFormalContext Object. Again the Attributes
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
invoke each method of the `Computation` interface, because all of them are static. The most basic operation in FCA
is the computation of the Prime of either a set of `Objects` or a set of `Attributes`. Because the underlying data struture
is a List, we can simply provide these methods with the corresponding List instead of a Set.  
<br/>Lets say we want to get
the Prime of all Objects of a Context. We simply invoke the method `computePrimeOfObjects` of the Computation interface and  
pass a List of Objects as well as the corresponding Context object.
```java
//Note that the return type here is ignored
Computation.computePrimeOfObjects(testContext.getContextObjects(),testContext);
```
This can dually be done with the Attributes by using the `computePrimeOfAttributes` method. One can also
compute all concepts or compute the Stem Base of a context by using the corresponding methods.

```java
//Computing all Concepts of a Context
Computation.computeAllConcepts(testContext);
//Computing the Stem Base of a Context
Computation.computeStemBase(testContext);
```
All other methods from the `Computation` have a similar signature and are
fairly easy to use. The question that arrises now is, what to do with the
computed results. One can use the `OutputPrinter` interface from the `api.utils` package,
to print these results to the console.
Therefore, below is the usage of the `OutputPrinter` interface
with our created Context object `testContext` and the `Computation` interface.

```java
//Print Context and all Concepts of testContext
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
Note that the format, when using the methods from the `OutputWrite` interface
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
of computing all concepts by invoking the method `computeAllConcepts` from the `Computation` interface and pass a List of
closures, as well as a Context Object.

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
passing it the current Implication, as well as the Context Object. The resul of this code is the following:
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
this Implication and 1 meaning every intent fulwills this Implication.

#### 7.Extending
The usefulness of this library comes also from the fairly easy option to extend existing classes or interfaces.
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
`ID` and the corresponding `List of Objects`. This is dually the case when creating a new `Object` class. But besides
these points one can create any new class and use the implemented Algorithms in `Computation` or `OutPutPrinter`. To get an
idea on how to implement these, one can check out the implementations in [FCAAttribute](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/lib/fca/FCAAttribute.java) and
[FCAObject](https://github.com/leongeis/FCAlib2/blob/main/src/main/java/lib/fca/FCAObject.java).
<br/>Now one can create different Objects, Attributes or Contexts, as long as the interfaces are implemented.
<br/>Lets assume we have implemented the methods properly and also created a new class implementing the `ObjectAPI` interface.
```
public class MyObject<O,A> implements ObjectAPI<O,A>{
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
application. For further information check out the interfaces in `api.fca` and `api.utils` and the example implementations  
in `lib.fca`.
## Knowledge Graphs
<!-- Wikidata -->
### Wikidata

### DBPedia

### YAGO

<!-- DBPedia -->


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
