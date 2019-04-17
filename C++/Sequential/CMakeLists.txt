cmake_minimum_required(VERSION 3.13)
project(Sequential)

set(CMAKE_CXX_STANDARD 14)

add_executable(Sequential
        Sequential_main.cpp
        cmake-build-debug/text.txt)
