The {@code Properties} class represents a persistent set of properties. The {@code Properties} can be saved to a stream
or loaded from a stream. Each key and its corresponding value in the property list is a string.

A property list can contain another property list as its "defaults"; this second property list is searched if the property key is not found in the original property list.

Because {@code Properties} inherits from {@code Hashtable}, the {@code put} and {@code putAll} methods can be applied to a {@code Properties} object.
Their use is strongly discouraged as they allow the caller to insert entries whose keys or values are not {@code Strings}.
The {@code setProperty} method should be used instead.  
If the {@code store} or {@code save} method is called on a "compromised" {@code Properties} object that contains a
non-{@code String} key or value, the call will fail. 
Similarly, the call to the {@code propertyNames} or {@code list} method will fail if it is called on a "compromised" {@code Properties}
object that contains a non-{@code String} key.


The {@link #load(java.io.Reader) load(Reader)} {@link #store(java.io.Writer, java.lang.String) store(Writer, String)}
methods load and store properties from and to a character based stream in a simple line-oriented format specified below.

The {@link #load(java.io.InputStream) load(InputStream)} {@link #store(java.io.OutputStream, java.lang.String) store(OutputStream, String)}
methods work the same way as the load(Reader)/store(Writer, String) pair, except the input/output stream is encoded in ISO 8859-1 character encoding.
Characters that cannot be directly represented in this encoding can be written using Unicode escapes as defined in section 3.3 of
<cite>The Java&trade; Language Specification</cite>; only a single 'u' character is allowed in an escape
sequence. The native2ascii tool can be used to convert property files to and from other character encodings.

The {@link #loadFromXML(InputStream)} and {@link
#storeToXML(OutputStream, String, String)} methods load and store properties
in a simple XML format.  By default the UTF-8 character encoding is used,
however a specific encoding may be specified if required. Implementations
are required to support UTF-8 and UTF-16 and may support other encodings.
An XML properties document has the following DOCTYPE declaration:

<pre>
&lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
</pre>
Note that the system URI (http://java.sun.com/dtd/properties.dtd) is
<i>not</i> accessed when exporting or importing properties; it merely
serves as a string to uniquely identify the DTD, which is:
<pre>
   &lt;?xml version="1.0" encoding="UTF-8"?&gt;

   &lt;!-- DTD for properties --&gt;

   &lt;!ELEMENT properties ( comment?, entry* ) &gt;

   &lt;!ATTLIST properties version CDATA #FIXED "1.0"&gt;

   &lt;!ELEMENT comment (#PCDATA) &gt;

   &lt;!ELEMENT entry (#PCDATA) &gt;

   &lt;!ATTLIST entry key CDATA #REQUIRED&gt;
</pre>

<p>This class is thread-safe: multiple threads can share a single
<tt>Properties</tt> object without the need for external synchronization.

@see <a href="../../../technotes/tools/solaris/native2ascii.html">native2ascii tool for Solaris</a>
@see <a href="../../../technotes/tools/windows/native2ascii.html">native2ascii tool for Windows</a>

@author  Arthur van Hoff
@author  Michael McCloskey
@author  Xueming Shen
@since   JDK1.0