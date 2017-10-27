cmake_minimum_required(VERSION 3.8)
project(lightControler)

set(CMAKE_CXX_STANDARD 14)

set(SOURCE_FILES
        src/ControlerSocket.cpp
        src/main.cpp)
add_executable(lightCtrlR ${SOURCE_FILES})