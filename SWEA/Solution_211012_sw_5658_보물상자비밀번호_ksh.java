import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 10. 12.
 *
 * 소요 시간: 0h 53m
 * 혼자 품: O
 * 풀이: 
 * 입력받은 문자열을 char 배열에 저장해서 N/4개씩 끊어서 비트연산자를 통해 10진수로 변환한 후 set에 저장하여 중복을 제거하고
 * set을 list로 변환하여 정렬한 후 K번째로 큰 수를 출력
 * 느낀 점: 
 * 처음에 2차원 배열로 풀려했다가 인덱스 접근이 쉽지 않아서 1차원 배열과 비트연산자를 사용하여 풀었다.
 * TreeSet과 iterator를 이용하면 정렬까지 한번에 처리할 수 있을 것 같다.
 */
public class Solution_211012_sw_5658_보물상자비밀번호_ksh {
	static Set<Integer> set;
	static char[] num;
	static int N, K, S;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			num = br.readLine().toCharArray();

			sb.append("#").append(tc).append(" ").append(solution()).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	private static int solution() {
		set = new LinkedHashSet<>();
		S = N / 4;// 숫자의 길이
		getNum();// 회전하기 전 처음 숫자를 뽑음

		// S-1번까지 회전하여 숫자를 뽑음
		for (int i = 0; i < S - 1; i++) {
			rotate();
			getNum();
		}

		ArrayList<Integer> al = new ArrayList<>();
		for (int i : set) {
			al.add(i);
		}

		Collections.sort(al);
		return al.get(al.size() - K);// K번째로 큰 수 return
	}

	// 16진수를 10진수로 변환하여 set에 저장
	private static void getNum() {
		for (int i = 0; i < 4; i++) {
			int sum = 0;

			// 16진수를 10진수로 변환하기 위해 비트연산자를 사용
			// 16=2^4이므로 4를 곱해줘야 함
			for (int j = 0; j < S; j++) {
				if (num[i * S + j] >= 'A') {
					sum += (num[i * S + j] - 'A' + 10) << 4 * (S - 1 - j);
				} else {
					sum += (num[i * S + j] - '0') << 4 * (S - 1 - j);
				}
			}
			set.add(sum);
		}
	}

	// 한 칸씩 숫자를 밀어줌
	private static void rotate() {
		char tmp = num[N - 1];
		for (int i = N - 1; i > 0; i--) {
			num[i] = num[i - 1];
		}
		num[0] = tmp;
	}
}
