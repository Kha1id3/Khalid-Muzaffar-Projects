#include <iostream>
#include "CFileLastError.h"
#include "CFileThrowEx.h"
#include "CFileErrCode.h"

using namespace std;

string sFilename = "Result.txt";

void vOpenFileTenTimes(string sFilename) {
    CFileThrowEx c_file_throw_ex;
    try {
        for (int i = 0; i < 10; i++)
        {
            c_file_throw_ex.vOpenFile(sFilename);
        }
    }
    catch (int e) {
        cout << " Throwing Exception: Error opening the file 10 times " << e << endl;
    }

    CFileLastError c_file_last_error;
    for (int i = 0; i < 10; i++)
    {
        if (c_file_last_error.bGetLastError()) {
            cout << "Last Error: Error opening the file 10 times" << endl;
            i = 10;
        }

        c_file_last_error.vOpenFile(sFilename);
    }

    CFileErrCode c_file_err_code;
    for (int i = 0; i < 10; i++)
    {
        if (!c_file_err_code.bOpenFile(sFilename)) {
            cout << "Error Code : Error opening the file 10 times" << endl;
            i = 10;
        }
    }
}

int main() {

    CFileThrowEx c_file;
    try {
        c_file = "Custom.txt";
        c_file.vPrintLine("Khalid");
        c_file.vCloseFile();

        cout << "Writing to Custom.txt complete" << endl;
    }
    catch (int e) {
        cout << "error writing in Custom.txt" << endl;
    }



    CFileLastError c_file_last_error;
    c_file_last_error.vOpenFile(sFilename);

    if (!c_file_last_error.bGetLastError()) {
        c_file_last_error.vPrintLine("hello -> c_file_last_error");
        cout << "ended writing c_file_last_error" << endl;
    }
    else {
        cout << "Error opening c_file_last_error" << endl;
    }

    c_file_last_error.vCloseFile();


    CFileThrowEx c_file_throw_ex;
    try {
        c_file_throw_ex.vOpenFile(sFilename);
        c_file_throw_ex.vPrintLine("hello -> c_file_throw_ex");
        cout << "Ended writing c_file_throw_ex" << endl;
    }
    catch (int e) {
        cout << "Error opening c_file_throw_ex: " << e << endl;
    }

    c_file_throw_ex.vCloseFile();


    CFileErrCode c_file_err_code;

    if (c_file_err_code.bOpenFile(sFilename)) {
        c_file_err_code.bPrintLine("hello -> c_file_err_code");
        cout << "Ended writting c_file_err_code" << endl;
    }
    else {
        cout << "Error opening c_file_err_code" << endl;
    }

    c_file_err_code.bCloseFile();

    vOpenFileTenTimes(sFilename);

    cin.get();
    return 0;
}