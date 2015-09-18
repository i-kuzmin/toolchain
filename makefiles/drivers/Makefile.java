ifeq ($(CLASSES),)
	CLASSES := $(BUILD)/classes
endif

java.src := $(filter %.java, $(SOURCES))

source_list := $(BUILD)/java.last.compiled

tags.src += $(java.src)

CLASS_PATH := $(call mk_join,:,$(CLASSES) $(CLASS_PATH))

.PHONY: java.clean

all: $(CLASSES)
clean: java.clean

$(source_list): $(SOURCES)
	$(file >$@,$?)

$(CLASSES): $(source_list) 
	@$(MKDIR) -p $@
	@javac -cp "$(CLASS_PATH)" -d $@ @$<
	@$(ECHO) "#   java compiled" $(shell tr " " "\n" < $< | wc -l) "files"
	@$(TOUCH) $@

java.clean: $(source_list)
	$(RM) $^

