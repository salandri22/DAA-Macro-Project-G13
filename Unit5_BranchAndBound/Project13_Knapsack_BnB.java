import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

/** Project 13: 0/1 Knapsack using Branch and Bound (DFS) + state-space tree image. */
public class Project13_Knapsack_BnB {

    static class Node {
        int id, level, profit, weight, parent;
        double ub = -1;               // -1 means "no bound computed"
        String status = "explored";   // explored | infeasible | pruned
        String edge = "";             // "include" / "exclude"
        List<Integer> kids = new ArrayList<>();
    }

    static int W = 10, n;
    static int[] p, w;
    static String[] itemNames;
    static List<Node> nodes = new ArrayList<>();
    static int bestProfit = 0, bestId = -1;

    // Upper bound: greedy fill, last item taken fractionally
    static double bound(int level, int profit, int weight) {
        if (weight > W) return 0;
        double b = profit;
        int tot = weight, j = level + 1;
        while (j < n && tot + w[j] <= W) { tot += w[j]; b += p[j]; j++; }
        if (j < n) b += (W - tot) * (double) p[j] / w[j];
        return b;
    }

    static void dfs(int level, int profit, int weight, int parent, String edge) {
        Node nd = new Node();
        nd.id = nodes.size(); nd.level = level; nd.profit = profit;
        nd.weight = weight; nd.parent = parent; nd.edge = edge;
        nodes.add(nd);
        if (parent >= 0) nodes.get(parent).kids.add(nd.id);

        if (weight > W) { nd.status = "infeasible"; return; }       // infeasible
        if (profit > bestProfit) { bestProfit = profit; bestId = nd.id; }
        if (level == n - 1) { nd.ub = profit; return; }             // leaf
        nd.ub = bound(level, profit, weight);
        if (nd.ub <= bestProfit) { nd.status = "pruned"; return; }  // prune by bound

        dfs(level + 1, profit + p[level + 1], weight + w[level + 1], nd.id, "include");
        dfs(level + 1, profit, weight, nd.id, "exclude");
    }

    // ---------- layout & drawing ----------
    static double[] xpos;
    static int leafCount = 0;

    static void layout(int i) {
        Node nd = nodes.get(i);
        if (nd.kids.isEmpty()) { xpos[i] = leafCount++; return; }
        double s = 0;
        for (int k : nd.kids) { layout(k); s += xpos[k]; }
        xpos[i] = s / nd.kids.size();
    }

    static void centered(Graphics2D g, String s, int cx, int cy) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(s, cx - fm.stringWidth(s) / 2, cy + fm.getAscent() / 2 - 2);
    }

    static void draw(String file, int[][] items) throws Exception {
        xpos = new double[nodes.size()];
        layout(0);
        int cw = 160, rh = 120, left = 230, top = 150;
        int width = left + leafCount * cw + 60, height = top + (n + 1) * rh + 120;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE); g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK); g.setFont(new Font("SansSerif", Font.BOLD, 22));
        centered(g, "0/1 Knapsack - Branch and Bound   (W = " + W + ", optimal profit = " + bestProfit + ")", width / 2, 40);
        g.setFont(new Font("SansSerif", Font.PLAIN, 15));
        StringBuilder sb = new StringBuilder("Items (profit, weight) sorted by ratio: ");
        for (int[] it : items) sb.append("(").append(it[0]).append(",").append(it[1]).append(") ");
        centered(g, sb.toString(), width / 2, 75);

        // edges first
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        for (Node nd : nodes) {
            if (nd.parent < 0) continue;
            Node pa = nodes.get(nd.parent);
            int x0 = left + (int) (xpos[pa.id] * cw), y0 = top + (pa.level + 1) * rh;
            int x1 = left + (int) (xpos[nd.id] * cw), y1 = top + (nd.level + 1) * rh;
            g.setColor(Color.GRAY); g.setStroke(new BasicStroke(2f));
            g.drawLine(x0, y0, x1, y1);
            g.setColor(new Color(60, 60, 60));
            centered(g, nd.edge, (x0 + x1) / 2 + (nd.edge.equals("include") ? -28 : 28), (y0 + y1) / 2);
        }
        // level labels
        g.setColor(new Color(0, 0, 128)); g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Root (no decision)", 15, top + rh / 2 * 0 + 5);
        for (int l = 0; l < n; l++)
            g.drawString("Item " + (l + 1) + " (" + items[l][0] + "," + items[l][1] + ")", 15, top + (l + 2) * rh + 5 - rh);
        // nodes
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        for (Node nd : nodes) {
            int cx = left + (int) (xpos[nd.id] * cw), cy = top + (nd.level + 1) * rh;
            Color c = new Color(0xcf, 0xe8, 0xff);
            if (nd.status.equals("infeasible")) c = new Color(0xff, 0x9a, 0x9a);
            if (nd.status.equals("pruned")) c = new Color(0xff, 0xd0, 0x8a);
            if (nd.id == bestId) c = new Color(0x8e, 0xe5, 0x9a);
            List<String> lines = new ArrayList<>();
            lines.add("p=" + nd.profit + ", w=" + nd.weight);
            if (nd.ub >= 0) lines.add("ub=" + String.format("%.1f", nd.ub));
            if (nd.status.equals("infeasible")) lines.add("w > W");
            if (nd.status.equals("pruned")) lines.add("PRUNED");
            int bw = 120, bh = 22 + lines.size() * 18;
            g.setColor(c); g.fillRoundRect(cx - bw / 2, cy - bh / 2, bw, bh, 14, 14);
            g.setColor(Color.BLACK); g.setStroke(new BasicStroke(1.5f));
            g.drawRoundRect(cx - bw / 2, cy - bh / 2, bw, bh, 14, 14);
            for (int i = 0; i < lines.size(); i++)
                centered(g, lines.get(i), cx, cy - bh / 2 + 22 + i * 18 - 4);
        }
        // legend
        String[] names = {"Explored", "Pruned (bound <= best)", "Infeasible (w > W)", "Optimal solution"};
        Color[] cols = {new Color(0xcf, 0xe8, 0xff), new Color(0xff, 0xd0, 0x8a),
                        new Color(0xff, 0x9a, 0x9a), new Color(0x8e, 0xe5, 0x9a)};
        int lx = 20, ly = height - 50;
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        for (int i = 0; i < 4; i++) {
            g.setColor(cols[i]); g.fillRect(lx, ly - 12, 22, 16);
            g.setColor(Color.BLACK); g.drawRect(lx, ly - 12, 22, 16);
            g.drawString(names[i], lx + 30, ly + 1);
            lx += 40 + g.getFontMetrics().stringWidth(names[i]) + 20;
        }
        g.dispose();
        ImageIO.write(img, "png", new File(file));
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "true");
        int[] profits = {40, 42, 25, 12};
        int[] weights = {4, 7, 5, 3};
        n = profits.length;

        // sort by profit/weight ratio, descending
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Double.compare((double) profits[b] / weights[b], (double) profits[a] / weights[a]));
        p = new int[n]; w = new int[n];
        int[][] items = new int[n][2];
        for (int i = 0; i < n; i++) {
            p[i] = profits[idx[i]]; w[i] = weights[idx[i]];
            items[i][0] = p[i]; items[i][1] = w[i];
        }

        dfs(-1, 0, 0, -1, "");

        System.out.println("Max profit: " + bestProfit);
        System.out.println("Nodes generated: " + nodes.size());
        for (Node nd : nodes)
            System.out.printf("Node %d: level=%d p=%d w=%d ub=%s status=%s%n",
                    nd.id, nd.level, nd.profit, nd.weight,
                    nd.ub >= 0 ? String.format("%.1f", nd.ub) : "-", nd.status);
        draw("Visualization.png", items);
        System.out.println("Saved Visualization.png");
    }
}
