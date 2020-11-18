<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![Github top language][language-shield]][language-url]

# FCAlib2

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Tutorial](#tutorial)
  * [Creating an Object](#objectcreation)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)

<!-- ABOUT THE PROJECT -->
## About The Project

This project is an enhanced version of FCAlib originally developed by Baris Sertkaya.

### Built With

* []()
* []()
* []()



<!-- GETTING STARTED -->
## Getting Started



### Prerequisites


### Installation


## Tutorial
The flavour of FCA comes into play, when considering Objects and Attributes of a formal Context.
Thus, below is a quick guide on how to create an Object/Attribute/Context and how to use the
implemented algorithms. 
#### 1.Creating an Object (`FCAObject`)
>First we need to create an Object and specify the type of the Object itself (first type parameter)
>, and the type of the Attributes the Object holds (second type parameter). Both the Object itself and
>its Attributes are of type `String`.
>We provide the Constructor of the FCAObject Class with an ID for our new FCAObject ob1, which is here "Q1".
><br/>Note: The ID and the type of the Object itself have to be the same!
```java
FCAObject<String,String> ob1 = new FCAObject<>("Q1");
```
#### 2.Creating an Attribute (`FCAAttribute`)
>When creating a new Attribute, the type parameter are (similar to the FCAObject type parameter) first the type
>of the Attribute itself and secondly the type of the Objects the Attribute holds. Again, both the Attribute itself
>and its Objects are of type `String`.
>We provide the Constructor of the FCAAttribute Class with an ID for our new FCAAttribute atr1, which is here just "a".
><br/>Note: Again, both the ID and the type of the Attribute itself have to be the same!
```java
FCAAttribute<String,String> atr1 = new FCAAttribute<>("a");
```
#### 3.Creating a Formal Context (`FCAFormalContext`)
>Similar to the Object and Attribute type parameter, the first type parameter specifies the type of the Objects (G) of
>the context and the second type parameter specifies the type of the Attributes (M) of the context. Here, both
>the Objects and the Attributes of the context are of type `String`.
```java
FCAFormalContext<String,String> testContext = new FCAFormalContext<>();
```
#### 4.Interaction between `FCAObject` and `FCAAttribute`
>An Object without any Attribute describing the Object is almost worthless. Thus, below is the easiest and quickest way
>to assign an Attribute to an Object.
```java
ob1.addAttribute("a");
ob1.addAttribute("b");
```
>Here the earlier created FCAObject `ob1` gets two new Attributes ("a" and "b") with the method `addAttribute()`.
>On the other hand, an Attribute without any corresponding Object is nothing but wasted memory. Hence, below the easiest and
>quickest way to assign Object to an Attribute.
```java
atr1.addObject("Q1");
atr1.addObject("Q2");
```
>Here the earlier created FCAAttribute `atr1` gets two Objects assigned ("Q1" and "Q2").
>One might already recognized the close connection between `FCAAttribute` and `FCAObject`. This becomes
>even more obvious when considering the other methods of the classes.
<br/>FCAAttribute: https://github.com/leongeis/FCAlib2/blob/main/src/main/java/fca/FCAAttribute.java
<br/>FCAObject: https://github.com/leongeis/FCAlib2/blob/main/src/main/java/fca/FCAObject.java
#### 5.Interaction between `FCAFormalContext` and `FCAAttribute/FCAObject`
>One can either add Attributes or Objects to a context. This approach is fairly easy, because one can add them
>interchangeably. Adding an Object will also add all of its Attributes to the context, dually for Attributes.
```java
testContext.addFCAObject(ob1);
testContext.addFCAObject(ob2);
testContext.addFCAObject(ob3);
```
>Here we add the FCAObjects `ob1`,`ob2` and `ob3` to the earlier created FCAFormalContext Object. Again the Attributes
>of each individual Object are also added to the context. Thus, one could also use the method `addFCAAttribute()`.
#### 6.Algorithms
>The usefulness of FCA comes from the existing algorithms. There is an implemented approach for using these. First we create
>an `FCAOutputWriter` Object to display each computed result on the Console.
```java
FCAOutputWriter<String,String> o = new FCAOutputWriter<>();
```
>Here, the type parameter have to match those of the, via parameter, provided `FCAFormalContext` Object. Thus, both types being
>`String`. This Object `o` can now be used to display the Crosstable, or all Concepts of a formal Context with the same
>type parameters. 
```java
o.printCrosstableToConsole(testContext);
```
>This method prints, e.g., this Crosstable to the Console:
```
The Crosstable of the current context: X:Object has Attribute; -:Object does not have Attribute
   a b c d 
Q1 X X - - 
Q2 - X X - 
Q3 - X - X 
Q4 X X X - 
```
>To display all Concepts of a Context one can use the following method:
```java
o.printConceptsToConsole(testContext);
```
>This method prints, e.g., these Concepts to the Console:
```
CONCEPT:[Q1, Q2, Q3, Q4];[b]
CONCEPT:[Q3];[d, b]
CONCEPT:[Q2, Q4];[c, b]
CONCEPT:[Q1, Q4];[a, b]
CONCEPT:[Q4];[a, c, b]
CONCEPT:[];[a, b, c, d]
```
>The Algorithm used in the method above is `NextClosure`. One can also use an naive implementation
>of computing all concepts implemented in the `FCAFormalContext` class.
><br/>FCAFormalContext: https://github.com/leongeis/FCAlib2/blob/main/src/main/java/fca/FCAFormalContext.java
<!-- ROADMAP -->
## Roadmap



<!-- CONTRIBUTING -->
## Contributing


<!-- LICENSE -->
## License


<!-- CONTACT -->
## Contact

Leon Geis - [lgeis@stud.fra-uas.de](https://twitter.com/twitter_handle)

Project Link: [https://github.com/leongeis/FCAlib2](https://github.com/github_username/repo_name)



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
