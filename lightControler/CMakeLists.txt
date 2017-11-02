cmake_minimum_required(VERSION 3.8)
project(lightControler)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_FLAGS "-lamqpcpp -pthread")

include_directories(third_party)

set(SOURCE_FILES
        src/ControlerSocket.cpp
        src/MessageQueue.cpp
        src/main.cpp)
add_executable(lightCtrlR ${SOURCE_FILES})