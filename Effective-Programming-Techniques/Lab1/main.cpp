#include <iostream>
#include <cstdlib>
#include <ctime>


using namespace std;


int main() {

    int i, j, k;
    int days = 30;
    int Sales[4][5] = {0};
    int PriceProduct[5] = {5, 9, 2, 4, 4};
    double total;
    double grandtotal;
    int SoldProducts = 0;

        srand((unsigned int) time(nullptr));

        for (k = 0; k <= days; k++) {
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 5; j++) {

                    SoldProducts = rand() % 10;
                    Sales[i][j] += SoldProducts *  PriceProduct[0] * days;
                }
            }
        }
        grandtotal = 0;

        cout << "  SalesPerson\titem 1\titem 2\titem 3\titem 4\titem 5\tTotal\n\n";

        for (i = 0; i < 4; i++) {
            cout << "        "<<(i + 1) <<"\t";
            total = 0;
            for (j = 0; j < 5; j++) {
                total += Sales[i][j];
                cout << Sales[i][j] << "\t";
            }

            cout << total << endl;
            grandtotal += total;
        }
        cout<< "\n";
        cout << " Total\t\t";

        for (i = 0; i < 5; i++) {
            total = 0;
            for (j = 0; j < 4; j++)
                total += Sales[j][i];
            cout << total << "\t";
        }
        cout << grandtotal << endl;
        return 0;
    }
