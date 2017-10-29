# Configuration
SRC_DIR=src
BUILD_DIR=build
OUT=controler

# Libraries configuration
CXXFLAGS+= -std=c++14 -g
LDLIBS= -lamqpcpp -lpthread

ifeq ($(OS),Windows_NT)
	LDLIBS+= -lws2_32
endif

# - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
SOURCES=$(wildcard $(SRC_DIR)/*.cpp $(SRC_DIR)/**/*.cpp)
OBJS := $(SOURCES:$(SRC_DIR)/%.cpp=$(BUILD_DIR)/%.o)

# link
$(OUT): $(OBJS)
	$(CXX) $(OBJS) -o $@ $(CXXFLAGS) $(LDLIBS) $(LDFLAGS)

# pull in dependency info for *existing* .o files
#include $(OBJS:.o=.d)
include $(wildcard $(BUILD_DIR)/*.d $(BUILD_DIR)/**/*.d)

# compile and generate dependency info
# The $< should be $^ but MSYS make then take build-order-prerequisite
# TODO: Find a way to remove the mkdir from here (order-only dep?)
$(BUILD_DIR)/%.o: $(SRC_DIR)/%.cpp | $(BUILD_DIR)
	@mkdir -p $(dir $@)
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) -c  $< -o $@
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) -MM $< -MF $(BUILD_DIR)/$*.d -MT $@

$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

# remove compilation products
clean:
	rm -rf $(OUT) $(BUILD_DIR)