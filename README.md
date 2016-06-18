# actors

It provides an actor system.

The project [frtex](https://github.com/LaurentClaessens/frtex) uses this actor system for reading a LaTeX file and return a new LaTeX file in which the "\input" are (recursively) replaced by the content.

### Installation and test

* download the actor system from [github](https://github.com/LaurentClaessens/actors)
* Use it by example performing the tests :
<pre><code>mvn  test</code></pre>

## General classes

* `Message`
* `Mail`
* `MailBox`

## An abstract implementation : the Decent actor system.

The types are

* `DecentActorSystem` its special features are :
    * `void setUpActor(DecentActorRef,DecentActor)`. Give to the two references the attribute they need and associate them in the actor map.
    * `DecentActorRef createPair()` (abstract). This function has to be overridden in the extensions. The override has to create a pair actor/actor reference and call `setUpActror` with these two as arguments.
* `DecentActorRef`
* `DecentAbsActor` its special features are :
    * __reference to the actor system__ you get the actor system with `public DecentActorSystem getActorSystem()`.
    * __accepted type__ Each actor has its own accepted type of message. By default it is the one of the system, but is can be set to any other class. No check is done, but the accepted message type of one actor is supposed to be a subclass of the accepted message type of the system. 

    We have the following two methods :
        * `void setAcceptedType()` that should be used only one. During the creation process of the actor. See the method `setUpActor`.
        * `Class getAcceptedType()`.
        
    * __name/serie number__ Each actor has a name with the usual method `String getName()`. The default name is based on the creation ordering (zero for the first, and so on). The serie number is private.

```java
ActorSystem getActorSystem()
```
returns the actor system which created him.


## Next implementation : your actor system

In order to make the things clearly you should create your own actor system by derivation from the "decent" actor system. You should create the following classes
* `yourActor`
* `yourActorRef`
* `yourActorSystem`

### Some override that you should do

* In the class `yourActorSystem` 

```java
public void setUpActor(yourActorRef ref,yourActor act)
{
    super.setUpActor(ref,act);
    // give here to ref and act the properties they need
}
```

* In the class `yourActorSystem` 

```java
public yourActorRef createPair()
{
    yourActorRef reference = new yourActorRef();
    yourActor actor = new yourActor();
    setUpActor(reference,actor);
    return reference;
}
```

* In the class `yourActorSystem` 

```java
@Override
public  yourActor getActor(DecentActorRef reference) { return (yourActor) super.getActor(reference); }
```
The cast should work because the actor has a reference to its actor system. Thus only actors build from your actor system should get into that method.

* In the class `yourActor` 

```java
@Override
public  YourActorRef getSelfReference() { return (YourActorRef) super.getSelfReference() ; }
```

* In the class `yourActor` 

```java
@Override
public LatexActorSystem getActorSystem() { return (LatexActorSystem) super.getActorSystem(); }
```

* In the class `yourActorRef` 

```java
@Override                                   
public LatexActor getActor() { return (LatexActor) super.getActor(); }                                           
```

* In the class `yourActor`
```java
    @Override
    public void receive(Message m)
    {
        super.receive(m);
        // do whatever your actor should do.
    }
```


## The Echo actor system

It is only for testing purpose. The system manage the message type "EchoText".

### EchoText (the message type)

The `EchoText` type has two subtypes `EchoTextOne` and `EchoTextTwo` that only exist for testing purpose of the `accepted_type` system.

### Special features
* `EchoActor` has a reference to the __last message__


### TODO

* The actor reference often calls its actor in order to answer questions like the accepted type or the actor system. One should memoize them.

### HISTORY

The skeleton is from [rcardin](https://github.com/rcardin/pcd-actors), but I got quite far away from his architecture. For example

* I removed the use of generics and add a class variable `accepted_type`
* I removed the base 'abstract' first implementation.
* I removed the interfaces

