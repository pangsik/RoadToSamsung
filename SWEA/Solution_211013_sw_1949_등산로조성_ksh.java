import java.io.*;
import java.util.*;

/**
 * @author   : KimSeonhong
 * @date   : 2021. 10. 13.
 *
 * 소요 시간 : 0h 44m
 * 혼자 품: O
 * 풀이: 가장 높은 봉우리 좌표를 리스트에 저장한 후 dfs를 통해 최댓값을 구함, 최대 K만큼 깎을 수 있으므로 조건에 맞는다면 1을 깎는 것이 가장 멀리갈 수 있음
 * 느낀 점: 처음에 무조건 1~K까지 하나하나 깎아서 확인해야 되는 줄 알았는데 위와 같은 풀이를 생각하게 되어 어렵진 않았다.
 */
public class Solution_211013_sw_1949_등산로조성_ksh {
   static ArrayList<int[]> stPos;
   static int[][] map;
   static boolean[][] visit;
   static int[] dx = { -1, 1, 0, 0 };
   static int[] dy = { 0, 0, -1, 1 };
   static int N, K, res;

   public static void main(String[] args) throws Exception {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      StringBuilder sb = new StringBuilder();
      int T = Integer.parseInt(br.readLine());

      for (int tc = 1; tc <= T; tc++) {
         StringTokenizer st = new StringTokenizer(br.readLine(), " ");
         N = Integer.parseInt(st.nextToken());
         K = Integer.parseInt(st.nextToken());

         stPos = new ArrayList<>();
         map = new int[N][N];
         int max = 0;

         for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
               map[i][j] = Integer.parseInt(st.nextToken());
               max = Math.max(max, map[i][j]);
            }
         }

         // 가장 높은 봉우리의 좌표를 리스트에 저장
         for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
               if (map[i][j] == max) {
                  stPos.add(new int[] { i, j });
               }
            }
         }

         solution();
         sb.append("#").append(tc).append(" ").append(res).append("\n");
      }

      System.out.print(sb);
      br.close();
   }

   private static void solution() {
      res = 0;
      // 가장 높은 봉우리마다 dfs 수행 후 방문배열 초기화
      for (int[] cur : stPos) {
         visit = new boolean[N][N];
         dfs(cur[0], cur[1], 1, false);
      }
   }

   // dfs를 통해 등산로 최대 길이 갱신
   private static void dfs(int x, int y, int len, boolean isWork) {
      visit[x][y] = true;

      for (int d = 0; d < 4; d++) {
         int nx = x + dx[d];
         int ny = y + dy[d];

         // 1. 다음 방문할 지형이 낮은 지형이라면 dfs 수행 후 해당 좌표 방문처리 취소
         // 2. 다음 방문할 지형이 높은 지형이면서 아직 공사한적 없고  최대 K까지 깎았을 때 등산로 조성이 가능하다면
         //    다음 방문할 지형을 1만 깎음(적게 깎아야 등산로 최대 길이 조성 가능), dfs 수행 후 원본 배열 복원 및 방문처리 취소
         if (check(nx, ny) && !visit[nx][ny]) {
            if (map[x][y] > map[nx][ny]) {
               dfs(nx, ny, len + 1, isWork);
               visit[nx][ny] = false;
            } else if (!isWork && map[x][y] > map[nx][ny] - K) {
               int tmp = map[nx][ny];
               map[nx][ny] = map[x][y] - 1;
               dfs(nx, ny, len + 1, true);
               map[nx][ny] = tmp;
               visit[nx][ny] = false;
            }
         }
      }

      res = Math.max(res, len);
   }

   // 배열 범위 체크
   private static boolean check(int x, int y) {
      return 0 <= x && x < N && 0 <= y && y < N;
   }
}
