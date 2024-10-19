#include <iostream>

using namespace std;

void v_alloc_table_add_5 (int iSize) {
// Pointer initialization to null
    int* array;
// Request memory for the array using new operator
    array = new(nothrow) int [iSize];
//if fails then print error
    if (!array) {
        cout << "allocation of memory failed\n";
        return;
    }
// initialization of array offset+5
    for (int i = 0; i < iSize; i++){

        array[i] = i+5;
    }

//print array

    cout<<"Elements of array are ";

    for (int i = 0; i < iSize; i++){
        cout << array[i] << " ";
    }
    cout<<endl;

    delete[] array;
}


bool b_alloc_table_2_dim(int ***piTable, int iSizeX, int iSizeY) {

    try {


//we assign a row of pointers to create the rows
        *piTable = (int **) malloc(iSizeX * sizeof(int *));
//we loop and then allocate space according to the "iSizeY" passed as parameter
        for (int i = 0; i < iSizeX; i = i + 1)
            (*piTable)[i] = (int *) malloc(iSizeY * sizeof(int *));
//it returns 1 if executed till here successfully , else program will get stuck
        return 1;
    }
    catch (...) {
        return false;
    }

}


bool b_dealloc_table_2_dim (int **piTable, int iSizeX, int iSizeY) {
    try{
        for(int i=0; i<= iSizeY; i++)
            delete[] piTable[i];
        delete[] piTable;
        return true;
    }
    catch(...) {
        return false;
    }
}





int main(){
    v_alloc_table_add_5(5);

    int **pi_table= nullptr;


    cout << b_alloc_table_2_dim(&pi_table, -10, -10);


    int **array, X = 10, Y = 20;
    array = new int *[X];
    for (int i = 0; i < X; i++) {
        **array = sizeof(i);
        array[i] = new int[Y];
    }
    cout << std::boolalpha << b_dealloc_table_2_dim(array, X,Y);
}