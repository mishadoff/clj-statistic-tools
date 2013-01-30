#include <vector>
#include <map>
#include <set>
#include <queue>
#include <algorithm>
#include <numeric>
#include <sstream>
#include <iostream>
#include <iomanip>
#define _USE_MATH_DEFINES
#include <cmath>
#include <climits>
#include <cstdlib>
#include <cstring>
#include <cfloat>
#include <cmath>
#include <ctime>
#include <cassert>
#include <cctype>
using namespace std;

#define EPS 1e-12
#define ll long long
#define VI vector<ll>
#define PII pair<ll, ll> 
#define VVI vector<vector<ll> >
#define REP(i,n) for(int i=0,_n=(n);(i)<(int)_n;++i)
#define RANGE(i,a,b) for(int i=(int)a,_b=(int)(b);(i)<_b;++i)
#define FOR(i,c) for(__typeof((c).begin())i=(c).begin();i!=(c).end();++i)
#define ALL(c) (c).begin(), (c).end()
#define ALLR(c) (c).rbegin(), (c).rend()
#define PB push_back
#define MP(a, b) make_pair(a, b)
#define POPCOUNT __builtin_popcount
#define POPCOUNTLL __builtin_popcountll
#define CLEAR(table, v) memset(table, v, sizeof(table));
#define PRINT2(table, W, H) REP(y, H) { REP(x, W) cout<<table[y][x]<<" "; cout<<"\n"; }
#define PRINT3(table, W, H, D) REP(d, D) { REP(y, H) { REP(x, W) cout<<table[d][y][x]<<" "; cout<<"\n"; } cout<<"\n"; }
template <typename T0, typename T1> std::ostream& operator<<(std::ostream& os, const map<T0, T1>& v) { for( typename map<T0, T1>::const_iterator p = v.begin(); p!=v.end(); p++ ){os << p->first << ": " << p->second << " ";} return os; }
template <typename T0, typename T1> std::ostream& operator<<(std::ostream& os, const pair<T0, T1>& v) { os << v.first << ": " << v.second << " "; return os; }
template <typename T> std::ostream& operator<<(std::ostream& os, const vector<vector<T> >& v) { for( int i = 0; i < (int)v.size(); i++ ) { os << v[i] << endl; } return os; }
template <typename T> std::ostream& operator<<(std::ostream& os, const vector<T>& v) { for( int i = 0; i < (int)v.size(); i++ ) { os << v[i] << " "; } return os; }
template <typename T> std::ostream& operator<<(std::ostream& os, const set<T>& v) { vector<T> tmp(v.begin(), v.end()); os << tmp; return os; }
template <typename T> std::ostream& operator<<(std::ostream& os, const deque<T>& v) { vector<T> tmp(v.begin(), v.end()); os << tmp; return os; }



int main() {
	int T;
	cin>>T;
	REP(ttt, T) {
		ll N,K,A,B,C,R;
		cin>>N>>K>>A>>B>>C>>R;
		
		VI w(K);
		w[0] = A;
		RANGE(i, 1, K) {
			w[i] = (B * w[i - 1] + C) % R;
		}
		VI ww(K+2); // [0, K+2)
		REP(j, K) if(w[j]<ww.size()) ww[w[j]]++;
		
		ll ans = -1;
		RANGE(i, K, N) {
			ll nxt = -1;
			REP(j, ww.size()) {
				if(ww[j]==0) {
					nxt = j;
					break;
				}
			}
			assert(nxt!=-1);
			
			int rm = w[i%K];
			if(rm<ww.size()) ww[rm]--;
			
			int add = nxt;
			if(add<ww.size()) ww[add]++;
			
			w[i%K] = nxt;
			
//			if((N-1-i)%(K+1)==0) {
//				cout<<i<<" "<<nxt<<endl;
//			}
			ans = nxt;
			if(5*K<N && (N-1-i)%(K+1)==0) break;
		}
		
		cout<<"Case #"<<ttt+1<<": "<<ans<<endl;
//		break;
	}
	return 0;
}


