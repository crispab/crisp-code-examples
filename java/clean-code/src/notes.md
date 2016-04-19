
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
