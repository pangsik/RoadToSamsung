import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 9. 15.
 *
 * 분류: 구현, 그래프 이론, 그래프 탐색, 너비 우선 탐색, 깊이 우선 탐색, 시뮬레이션
 * 난이도: 골드4
 * 소요 시간: time over
 * 혼자 품: X
 * 풀이: 파이어스톰을 시전할 때마다 배열을 복사해서 회전 시킨 후 모든 시전이 끝나면 dfs로 얼음 덩어리를 구함
 * 느낀 점: 예전에 풀었던 문제인데도 배열 인덱스 연산을 잘못해서 시간 안에 풀지 못함, 효율 이전에 구현부터 하자
 */
public class Main_210915_g4_20058_마법사상어와파이어스톰_ksh {
	static int[][] map, tmp;
	static boolean[][] visit;
	static int[] stage;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, Q, sum, max;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = 1 << Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		tmp = new int[N][N];
		visit = new boolean[N][N];
		stage = new int[Q];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < Q; i++) {
			stage[i] = Integer.parseInt(st.nextToken());
		}

		solution();
		br.close();
	}

	private static void solution() {
		for (int i = 0; i < Q; i++) {
			if (stage[i] > 0) {// 0단계가 아니면 파이어스톰 시전
				fireStorm(stage[i]);
			}
			iceCheck();// 얼음이 있는 칸 3개 또는 그 이상과 인접해있지 않은 칸은 얼음의 양이 1 줄어든다.
		}
		resultSet();
	}

	// 파이어스톰은 먼저 격자를 2L × 2L 크기의 부분 격자로 나눈다.
	// 그 후, 모든 부분 격자를 시계 방향으로 90도 회전시킨다.
	private static void fireStorm(int lv) {
		int size = 1 << lv;
		for (int i = 0; i < N; i += size) {
			for (int j = 0; j < N; j += size) {
				rotate(i, j, size);
			}
		}
	}

	// 배열을 복사해서 회전시킴
	private static void rotate(int row, int col, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				tmp[j][size - 1 - i] = map[row + i][col + j];
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[row + i][col + j] = tmp[i][j];
			}
		}
	}

	// 얼음 양 줄이기
	private static void iceCheck() {
		boolean[][] isRemove = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] > 0) {
					int cnt = 0;
					for (int dir = 0; dir < 4; dir++) {
						int nx = i + dx[dir];
						int ny = j + dy[dir];
						if (check(nx, ny) && map[nx][ny] > 0) {
							cnt++;
						}
					}

					if (cnt < 3) {
						isRemove[i][j] = true;
					}
				}
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (isRemove[i][j] && map[i][j] > 0) {
					map[i][j]--;
				}
			}
		}
	}

	// 남아있는 얼음 A[r][c]의 합
	// 남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수
	private static void resultSet() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += map[i][j];
				if (!visit[i][j] && map[i][j] > 0) {
					max = Math.max(max, dfs(i, j));
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(sum).append("\n").append(max);
		System.out.print(sb);
	}

	// dfs로 얼음 덩어리 크기 계산
	private static int dfs(int x, int y) {
		int cnt = 1;
		visit[x][y] = true;

		for (int dir = 0; dir < 4; dir++) {
			int nx = x + dx[dir];
			int ny = y + dy[dir];
			if (check(nx, ny) && !visit[nx][ny] && map[nx][ny] > 0) {
				cnt += dfs(nx, ny);
			}
		}

		return cnt;
	}

	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}