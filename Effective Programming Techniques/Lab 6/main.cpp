#include <iostream>
#include "CTreeDynamic.h"
#include <string>

using namespace std;

int main() {
    cout<<"------Using Int type class----" <<endl;
    CTreeDynamic<int> * croot = new CTreeDynamic<int>;
    croot->pcGetRoot()->vSetValue(5);
    croot->pcGetRoot()->vAddNewChild();
    croot->pcGetRoot()->vAddNewChild();

    croot->pcGetRoot()->pcGetChild(0)->vSetValue(2);
    croot->pcGetRoot()->pcGetChild(1)->vSetValue(3);

    croot->pcGetRoot()->pcGetChild(0)->vAddNewChild();
    croot->pcGetRoot()->pcGetChild(0)->vAddNewChild();
    croot->pcGetRoot()->pcGetChild(0)->pcGetChild(0)->vSetValue(9);
    croot->pcGetRoot()->pcGetChild(0)->pcGetChild(1)->vSetValue(4);

    croot->pcGetRoot()->vPrint(0);
    cout <<endl;

    int i_min = 5;
    croot->vGetMin(&i_min);
    cout <<"Minimum value is: " << i_min <<  endl;

    cout<<"------Using float type class----" <<endl;

    CTreeDynamic<float> * croot2 = new CTreeDynamic<float>;
    croot2->pcGetRoot()->vSetValue(10.5);
    croot2->pcGetRoot()->vAddNewChild();
    croot2->pcGetRoot()->vAddNewChild();

    croot2->pcGetRoot()->pcGetChild(0)->vSetValue(7.5);
    croot2->pcGetRoot()->pcGetChild(1)->vSetValue(8.5);

    croot2->pcGetRoot()->pcGetChild(0)->vAddNewChild();
    croot2->pcGetRoot()->pcGetChild(0)->vAddNewChild();
    croot2->pcGetRoot()->pcGetChild(0)->pcGetChild(0)->vSetValue(10.2);
    croot2->pcGetRoot()->pcGetChild(0)->pcGetChild(1)->vSetValue(5.3);

    croot2->pcGetRoot()->vPrint(0);
    cout <<endl;

    int i_min2 = 10;
    croot->vGetMin(&i_min2);
    cout <<"Minimum value is: " << i_min2  <<  endl;
    delete croot2;
    cin.get();
}