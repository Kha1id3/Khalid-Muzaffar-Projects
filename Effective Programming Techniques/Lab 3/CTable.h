//
// Created by Khalid on 31/10/2022.
//

#ifndef LAB_3_CTABLE_H
#define LAB_3_CTABLE_H
#define DEFAULT_SIZE 10


#include <iostream>
#include <string>
using namespace std;

class CTable {


private:
    string sName;
    string  s_name;
    int i_table_len;
    int* arr;

public:
    CTable() {

        s_name = "Default";
        vSetSize(DEFAULT_SIZE);

        cout << "without: " << s_name << endl;
        arr = new int[DEFAULT_SIZE];
    }

   CTable(string sName, int iTableLen) {
        s_name = sName;
        arr = new int[iTableLen];
        for (int i = 0; i < iTableLen; i++)
        {
            arr[i] = rand();
        }

        cout << "Parameter: " << s_name<< endl;



    }
   CTable( const CTable &pcOther) {

        s_name = pcOther.s_name + "_copy";

        arr = pcOther.arr;

        cout << "Copy:" << s_name<< endl;

    }

  ~CTable() {
        cout << "Removing:" << s_name<< endl;
        delete[] arr;

    }

    void vSetName(string sName)
    {
        s_name = sName;
    }

    void vSetSize(int iSize) {
        i_table_len = iSize;
    };


    bool  bSetNewSize(int iTableLen) {

        if (iTableLen < 0) {
            return false;
        }

        i_table_len = iTableLen;
        int* pi_temp_table = arr;
        arr = new int[i_table_len];

        for (int i = 0; i < i_table_len; i++)
        {
            arr[i] = pi_temp_table[i];
        }

        delete[] pi_temp_table;
        return true;;
    }

  CTable *pcClone() {

        CTable clone ;

        clone.s_name = s_name;

        clone.arr = arr;

      return new CTable(*this);


    }
    int GetSize() {
        return i_table_len;
    }
    string GetName() {
        return s_name;
    }

    int* GetTable() {
        int *pi_table;
        return pi_table;
    }

    };






void v_mod_tab(CTable* pcTable, int iNewSize);
void v_mod_tab(CTable cTab, int iNewSize);










#endif //LAB_3_CTABLE_H
