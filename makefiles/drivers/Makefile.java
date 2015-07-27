__classes_dir = $(BUILD)/classes

java.DEP = $(TOOLCHAIN)/bin/extract_classes
java.CC := javac

java.src := $(filter %.java, $(SOURCES))
java.tmp := $(filter %.java, $(SOURCES.tmp))
java.dep := $(java.tmp:%=%/depend)

__dirs += $(__classes_dir)
__clean += $(java.tmp) $(java.classes:%='%')

tags.src += $(java.src)

-include $(java.dep)

ifneq ($(CLASSPATH),)
__class_path = -cp "$(__classes_dir):$(CLASSPATH)"
endif

$(__tmp)/%/depend: % |$(java.tmp)
	@$(java.DEP) $< >$@

.PHONY: java.compile
all: java.compile

java.compile: $(java.targets) |$(__classes_dir)
	@if [ ! -z "$(java.targets)" ]; then \
		$(ECHO) "#   compile $(sort $(java.targets))"; \
		$(java.CC) $(__class_path) -d $(__classes_dir) $(sort $(java.targets)); \
	fi
