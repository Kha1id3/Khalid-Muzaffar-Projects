//
// Created by Khalid on 31/10/2022.
//
#include "CTable.h"




void v_mod_tab(CTable* pcTable, int iNewSize) {
    pcTable->bSetNewSize(iNewSize);
}

void v_mod_tab(CTable cTab, int iNewSize){
    cTab.bSetNewSize(iNewSize);
}
