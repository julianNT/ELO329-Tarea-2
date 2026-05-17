JAVAFX_PATH = /usr/share/openjfx/lib
MODULES = javafx.controls,javafx.media,javafx.graphics
JFLAGS = --module-path $(JAVAFX_PATH) --add-modules $(MODULES)

run:
	javac $(JFLAGS) *.java
	java $(JFLAGS) Stage4

clean:
	rm -f *.class

doc:
	javadoc -d doc $(JFLAGS) *.java
