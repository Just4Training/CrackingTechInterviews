# Object-Orient Design
This section list OOD problems related with *Real World* problems and LeetCode problems

## Content
| Problem                        | Description                                                         |
| LRU Cache                      | [LeetCode 146](https://leetcode.com/problems/lru-cache/description/)|

## Basic Concept

### Access modifier
|Modifier     |Class	 |Package |Subclass	|World|
|-------------|------|--------|---------|-----|
|**public**   |  ✔   |  ✔	 |✔        |✔   |
|**protecte** |  ✔   |  ✔	 |✔	       |✖   |
|**(default)**|  ✔	 |  ✔	 |✖	       |✖   |
|**private**  |  ✔	 |  ✖	 |✖	       |✖   |

### Inner Class vs. Nested Class

### Abstract Class
|          | Abstract Class                                          | Interface                                    |
|Definition| A restricted class that cannot be used to create objects| A set of methods a class must implement      |
|Method    | Implemented or Abstract method                          | Methods are abstract by default              |
|Ihheritance|||

#### When to use
> Consider using abstract classes if any of these statements apply to your situation:  
> - In the Java application, there are some related classes that need to share some lines of code then you can put these lines of code within the abstract class and this abstract class should be extended by all these related classes.
> - You can define the non-static or non-final field(s) in the abstract class so that via a method you can access and modify the state of the object to which they belong.
> - You can expect that the classes that extend an abstract class have many common methods or fields, or require access modifiers other than public (such as protected and private).

> Consider using interfaces if any of these statements apply to your situation:  
> - It is a total abstraction, all methods declared within an interface must be implemented by the class(es) that implements this interface.
> - A class can implement more than one interface. It is called multiple inheritances.
> - You want to specify the behavior of a data type without worrying about its implementation.


reference: [Difference between Abstract Class and Interface in Java](https://www.geeksforgeeks.org/difference-between-abstract-class-and-interface-in-java/)