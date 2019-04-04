cmake_minimum_required(VERSION 3.13)
project(Parallel)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(Parallel
        Parallel_main.cpp
        Parallel_thread.cpp
        Parallel_thread.h
        Thread.cpp
        Thread.h)
