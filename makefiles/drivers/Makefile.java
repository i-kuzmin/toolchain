__classes_dir = $(BUILD)/classes

java.DEP = $(TOOLCHAIN)/bin/extract_classes
java.CC := javac

java.src := $(filter %.java, $(SOURCES))
java.tmp := $(filter %.java, $(SOURCES.tmp))
java.dep := $(java.tmp:%=%/depend)

#__all += $(java.classes)
__dirs += $(__classes_dir)
__clean += $(java.tmp) $(java.classes)

tags.src += $(java.src)

-include $(java.dep)

ifneq ($(CLASSPATH),)
__class_path = -cp "$(__classes_dir):$(CLASSPATH)"
endif

all: $(java.classes)

$(java.classes): $(java.src) |$(java.dep) $(__classes_dir)
	@$(ECHO) "#   compile $?"
	@$(java.CC) $(__class_path) -d $(__classes_dir) $?

$(__tmp)/%/depend: % |$(java.tmp)
	@$(ECHO) "#   dependancies for $<"
	@$(java.DEP) $< >$@

