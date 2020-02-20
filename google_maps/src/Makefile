JFLAGS = -Xlint
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Graph.java \
	Road.java\
	Intersection.java\
	StreetMap.java\
        SMapGUI.java  

default: classes

Graph: Graph.java

Pair: Pair.java

street_mapping: street_mapping.java

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

