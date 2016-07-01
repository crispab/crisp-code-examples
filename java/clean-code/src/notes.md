
Created an empty project and added Properties to a package se.crisp.example. 
However, some problems with sun internal XmlPropertiesProvider who needs the true java.util.Properties.

Since this is an refactoring exercise, I will try to use java.util package. It seems to work. Let's add a test.

Ok. What I suspected. "java.util" is a prohibited name. So we need to go back to the example name.
Plus a XmlPropertiesProvider of our own. Oh and a BasicXmlPropertiesProvider which uses PropertiesDefaultHandler.

We'll see how much is needed in the test.

The first test was to see if we can create and use some method on it. Size zero was the first natural choice,
but that uses inherited code. So we opt for set and get property.

Now reading the documentation. Properties inherits HashTable, which the documentation find problematic.
No wonder, Properties is not fully complaint with a HashTable so that breaks Liskow's substitution principle.

However, we can't start with a breaking change in the interface, so what can we do? 

Let's get the thing under test first, shall we?

In sweeping terms, the Properties class loads and stores key-value pairs in text- or XML-files. Keys and values
are strings. Keys may have default values.

When analysing the code, there are 9 warnings. We'll see to them later when we are fully test covered.

Let's start with some easy testing of default values.

Ok and with a test of the "propertyName" and stringProperty functions, we have a line coverage of 9% 30/315.
Discovered lack of branch coverage. The getProperty with default was not tested for the case when there was
no fallback necessary!

Next up for testing is the list methods. There is a secret shortening of strings so we need a long value for testing.
The list methods are a duplications of each other so the tests are quite similar too. Coverage now 15% 50/315.

Ok, some serious business is the load functions with their support for unicode characters. The properties file format 
is found here: https://docs.oracle.com/cd/E23095_01/Platform.93/ATGProgGuide/html/s0204propertiesfileformat01.html

Getting full coverage with leading space seems not possible since the inner class used to read lines is removing
it and the load method itself as well! 

Added some multi-line testing and we have reached 48% 152/315 line coverage. Adding comment testing gives 50% 159/315!

Adding empty keys that has value "" does not extend the line coverage. That was a bit surprising but possible.

Ok, so this has been dormant for a while.

The first reflection is that some of the tests does not do what they say in their names. Should be easy to fix.
Secondly, the inner class LineReader is weird. It can be constructed from a Reader or an InputStream. The logic
is similar for both cases but there are some conditionals whether it has been constructed from either.

Still, lots of coverage missing, especially when it comes to store function. But also around escape strings for
Unicode.

Added a store test and got 64% (207/322) coverage.

Fiddling with the tests, discovered a typical problem for these kind of scenarios. The test passed bu was wrong
as it compared two empty strings.