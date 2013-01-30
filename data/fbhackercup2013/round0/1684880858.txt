#include <iostream>
#include <vector>
#include <algorithm>
#include <cstring>
#include <map>
#include <set>
#include <queue>

using namespace std;

int M[200020];

int main() {
  int N; cin >> N;
  for(int t = 0; t < N; t++) {
    int n, k; cin >> n >> k; n--;
    int a, b, c, r; cin >> a >> b >> c >> r;

    M[0] = a;
    for(int i = 1; i < k; i++) {
      M[i] = (1ll * b * M[i - 1] + c) % r;
    }

    set<int> st;
    for(int i = 0; i <= k; i++) st.insert(i);
    for(int i = 0; i < k; i++) st.erase(M[i]);

    multiset<int> dupst;
    for(int i = 0; i < k; i++) dupst.insert(M[i]);

    for(int i = k; i <= 2 * k; i++) {
      M[i] = *st.begin();
      st.erase(st.begin());

      if(i < 2 * k) {
        dupst.erase(dupst.find(M[i - k]));
        if(M[i - k] <= k && dupst.find(M[i - k]) == dupst.end()) {
          st.insert(M[i - k]);
        }
      }
    }

    cout << "Case #" << (t + 1) << ": ";
    if(n <= 2 * k) {
      cout << M[n] << endl;
    } else {
      cout << M[k + (n - 2 * k - 1) % (k + 1)] << endl;
    }
  }
  return 0;
}
