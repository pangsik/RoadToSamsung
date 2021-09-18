import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 9. 19.
 *
 * 분류: 구현, 시뮬레이션
 * 난이도: 골드3
 * 소요 시간: 1h 04m
 * 혼자 품: O
 * 풀이: 토네이도 방향이 2번 바뀔 때마다 해당 방향 진행 횟수를 하나씩 늘려가면서 방향을 변경해가며 모래를 분배해주면 됨
 * 느낀 점: 조건문을 조금 줄이는 방법을 생각해낸다면 시간이 더 줄어들거 같다.
 */
public class Main_210909_g3_20057_마법사상어와토네이도_ksh {
	static int[][] map;
	static int[] dx = { 0, 1, 0, -1 };// 좌하우상
	static int[] dy = { -1, 0, 1, 0 };
	static int[][] tdx = { { -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 }, { -1, -1, 0, 0, 2, 0, 0, 1, 1, 1 },
			{ -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 }, { 1, 1, 0, 0, -2, 0, 0, -1, -1, -1 } };// 좌하우상 / 1,2,5,7,10%,a순
	static int[][] tdy = { { 1, 1, 0, 0, -2, 0, 0, -1, -1, -1 }, { -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 },
			{ -1, -1, 0, 0, 2, 0, 0, 1, 1, 1 }, { -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 } };
	static int N, x, y, res;
	static boolean isEnd;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		x = N / 2;
		y = N / 2;
		int cnt = 0, dir = -1;
		for (int i = 2;; i++) {
			cnt = i / 2;// 방향이 두번 바뀔 때마다 횟수가 하나씩 늘어남
			dir = (dir + 1) % 4;
			tornado(cnt, dir);
			if (isEnd) {
				break;
			}
		}
		return res;
	}

	// 토네이도가 이동할 때마다 모래 분배를 해줌
	private static void tornado(int cnt, int d) {
		for (int i = 0; i < cnt; i++) {
			x += dx[d];
			y += dy[d];
			if (check(x, y)) {
				isEnd = true;
				break;
			}

			int val = 0, sand = 0;
			for (int dir = 0; dir < 9; dir++) {
				int nx = x + tdx[d][dir];
				int ny = y + tdy[d][dir];
				switch (dir) {
				case 0:
				case 1:
					val = map[x][y] / 100;
					break;
				case 2:
				case 3:
					val = map[x][y] * 2 / 100;
					break;
				case 4:
					val = map[x][y] * 5 / 100;
					break;
				case 5:
				case 6:
					val = map[x][y] * 7 / 100;
					break;
				case 7:
				case 8:
					val = map[x][y] * 10 / 100;
					break;
				}
				
				if (check(nx, ny)) {
					res += val;
				} else {
					map[nx][ny] += val;
				}
				sand += val;
			}
			
			// a 모래 처리
			int nx = x + tdx[d][9];
			int ny = y + tdy[d][9];
			val = map[x][y] - sand;
			if (check(nx, ny)) {
				res += val;
			} else {
				map[nx][ny] += val;
			}

			map[x][y] = 0;
		}
	}

	// 배열 경계 넘어가는지 체크
	private static boolean check(int nx, int ny) {
		return nx < 0 || nx >= N || ny < 0 || ny >= N;
	}
}