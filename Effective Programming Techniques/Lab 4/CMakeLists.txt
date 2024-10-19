cmake_minimum_required(VERSION 3.23)
project(Lab)

set(CMAKE_CXX_STANDARD 17)

add_executable(Lab main.cpp CFileLastError.cpp CFileLastError.h CFileThrowEx.cpp CFileThrowEx.h CFileErrCode.cpp CFileErrCode.h)
