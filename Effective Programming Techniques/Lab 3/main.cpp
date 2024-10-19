#include <iostream>
#include "CTable.h"

using namespace std;
int main() {
    cout << "---Static Allocation---" << endl;
    CTable c_tab_DEF;
    CTable c_tab_Pra("khalid", 20);
    CTable c_tab_Copy = c_tab_Pra;


    cout << "\n---clone test---" << endl;
    CTable c_tab;
    CTable *pc_new_tab;
    pc_new_tab = c_tab.pcClone();

    cout << "\nModifying cloned Instances:" << endl;
    pc_new_tab->vSetName("dynamic_copy");
    pc_new_tab->vSetSize(25);
    cout << pc_new_tab->GetName() << endl;
    cout << pc_new_tab->GetSize() << endl;


    cout << "\n Test change size:" << endl;
    c_tab_DEF.vSetName("dynamic_test");
    c_tab_DEF.bSetNewSize(25);
    cout << c_tab_DEF.GetName() << endl;
    cout << c_tab_DEF.GetSize() << endl;

    cout << "\n---Dynamic allocation :----" << endl;
    CTable *c_tab_DEF_dynamic = new CTable();
    CTable *c_tab_Pra_dynamic = new CTable("test_dyn", 20);

    cout << "\n---Dynamic array allocation:---" << endl;
    CTable *pc_tab_dynamic = new CTable[4];

    cout << "\nv_mod_tab :" << endl;
    v_mod_tab(c_tab_DEF, 10);
    cout << c_tab_DEF.GetSize() << endl;

    cout << "\nv_mod_tab:" << endl;
    v_mod_tab(c_tab_DEF_dynamic, 20);
    cout << c_tab_DEF_dynamic->GetSize() << endl;

    cout << "\nTesting objects in Array" << endl;
    CTable *a_tabs = new CTable[2];
    a_tabs[0] = CTable("Khalid_1", 3);
    a_tabs[1] = CTable("Muzaffar_1", 4);

    for (int i = 0; i < a_tabs[0].GetSize(); i++) {
        cout << a_tabs[0].GetTable()[i] << " ";
    }


    cout << "\nRemoving Instances:" << endl;
    delete[] pc_tab_dynamic;





}
































