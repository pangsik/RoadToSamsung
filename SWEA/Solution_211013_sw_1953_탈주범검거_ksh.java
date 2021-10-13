package samsung;

import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2021. 10. 13.
 *
 * 소요 시간: 0h 55m
 * 혼자 품: O
 * 풀이: 3차원 배열에 터널 종류에 따른 방향을 저장하고 이동 가능 여부는 -1를 곱해서 반대방향인지 체크하는 방식으로 풀었다.
 * 느낀 점: 좀 어렵게 접근한것 같은데 예전에 푼 풀이가 조금 느리더라도 더 간결해서 예전 풀이를 다시 상기해야겠다.
 */
public class Solution_211013_sw_1953_탈주범검거_ksh {
	static int[][] map;
	static boolean[][] visit;
	static int[][][] dir = { { { 0, 0, 0, 0 }, { 0, 0, 0, 0 } }, { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } },
			{ { -1, 0, 1, 0 }, { 0, 0, 0, 0 } }, { { 0, 0, 0, 0 }, { 0, 1, 0, -1 } },
			{ { -1, 0, 0, 0 }, { 0, 1, 0, 0 } }, { { 0, 0, 1, 0 }, { 0, 1, 0, 0 } },
			{ { 0, 0, 1, 0 }, { 0, 0, 0, -1 } }, { { -1, 0, 0, 0 }, { 0, 0, 0, -1 } } };
	static int N, M, R, C, L;

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("res/sw_1953.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());

			map = new int[N][M];
			visit = new boolean[N][M];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			sb.append("#").append(tc).append(" ").append(bfs()).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	// bfs로 갈 수 있는 곳을 L초가 될 때까지 이동
	private static int bfs() {
		int res = 1, time = 1;
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[R][C] = true;
		q.offer(new int[] { R, C });

		while (!q.isEmpty()) {
			if (time == L) {
				break;
			}
			time++;
			int size = q.size();

			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();
				if (map[cur[0]][cur[1]] == 0) {
					continue;
				}
				int idx = map[cur[0]][cur[1]];

				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + dir[idx][0][d];
					int ny = cur[1] + dir[idx][1][d];

					if (check(nx, ny) && !visit[nx][ny] && map[nx][ny] != 0) {
						if (cur[0] == nx && cur[1] == ny) {
							continue;
						}

						int ni = map[nx][ny];
						int nd = (d + 2) % 4;
						
						// 터널 간 이동 가능(반대 방향)
						if (dir[ni][0][nd] == (-1 * dir[idx][0][d]) && dir[ni][1][nd] == (-1 * dir[idx][1][d])) {
							res++;
							visit[nx][ny] = true;
							q.offer(new int[] { nx, ny });
						}

					}
				}
			}
		}

		return res;
	}

	// 배열 범위 체크
	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}
