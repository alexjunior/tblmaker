package org.alexjunior.mahjong.tblmaker.tblmaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 胡牌检测数据生成器
 *
 * @author alexjunior
 * @date:2017年11月6日
 * @time:上午10:16:46
 * @description:
 */
public class TblMaker {
    Seq[] s = { //
            new Seq(0, new int[] { 1, 1, 1 }), //
            // new Seq(1, new int[] { 1, 1 }), //
            // new Seq(1, new int[] { 1, 0, 1 })//
    };

    Tri[] t = { //
            new Tri(0, new int[] { 3 }), //
            // new Tri(1, new int[] { 2 }), //
            // new Tri(2, new int[] { 1 }) //
    };

    Par[] p = { //
            new Par(0, new int[] { 2 }), //
            // new Par(1, new int[] { 1 }) //
    };

    public void make() {
        makeSuit();
        // makeHonor();
    }

    private void makeHonor() {
        /** 原始表 */
        ArrayList<int[]> tbl = getTable(7, 0, 4);
        statistic(tbl);
    }

    private void makeSuit() {
        /** 原始表 */
        ArrayList<int[]> tbl = getTable(9, 4, 4);
        statistic(tbl);
    }

    private void statistic(ArrayList<int[]> tbl) {

        HashMap<String, Tbm> tbms = new HashMap<>();

        HashMap<String, Tbm> tbms_eyes = new HashMap<>();

        System.out.println("table::" + tbl.size());

        /** 0:0 */
        tbl.add(new int[1]);

        /** 素表 */
        for (int[] ttt : tbl) {
            Tbm tbm = new Tbm(0, getTstring(ttt), ttt);
            if (tbm.tile > 12) {
                continue;
            }
            if (tbms.containsKey(tbm.code)) {
                Tbm otm = tbms.get(tbm.code);
                if (tbm.joker >= otm.joker) {
                    continue;
                }
                continue;
            }
            tbms.put(tbm.code, tbm);
        }

        /** 素表加1:2刻 */
        for (int[] ttt : tbl) {
            for (int t = 0; t < ttt.length; t++) {
                if (ttt[t] > 3) {
                    continue;
                }
                int[] tt = new int[ttt.length];
                System.arraycopy(ttt, 0, tt, 0, tt.length);
                tt[t] += 1;
                Tbm tbm = new Tbm(2, getTstring(tt), tt);
                if (tbm.tile > 12) {
                    continue;
                }
                if (tbms.containsKey(tbm.code)) {
                    Tbm otm = tbms.get(tbm.code);
                    if (tbm.joker >= otm.joker) {
                        continue;
                    }
                    continue;
                }
                tbms.put(tbm.code, tbm);
            }
        }

        // printTbl("tbms_nojoker", tbms.values());

        /** 素将表 */
        for (int[] ttt : tbl) {
            for (int t = 0; t < ttt.length; t++) {
                if (ttt[t] > 2) {
                    continue;
                }
                int[] tt = new int[ttt.length];
                System.arraycopy(ttt, 0, tt, 0, tt.length);
                tt[t] += 2;
                Tbm tbm = new Tbm(0, getTstring(tt), tt);
                if (tbm.tile > 14) {
                    continue;
                }
                if (tbms_eyes.containsKey(tbm.code)) {
                    Tbm otm = tbms_eyes.get(tbm.code);
                    if (tbm.joker >= otm.joker) {
                        continue;
                    }
                    continue;
                }
                tbms_eyes.put(tbm.code, tbm);
            }
        }
        // printTbl("tbms_eyes_nojoker", tbms_eyes.values());
        // System.out.println("===================================");

        /** 素表加1:1将 */
        for (int[] ttt : tbl) {
            for (int t = 0; t < ttt.length; t++) {
                if (ttt[t] > 3) {
                    continue;
                }
                int[] tt = new int[ttt.length];
                System.arraycopy(ttt, 0, tt, 0, tt.length);
                tt[t] += 1;
                Tbm tbm = new Tbm(1, getTstring(tt), tt);
                if (tbm.tile > 14) {
                    continue;
                }
                if (tbms_eyes.containsKey(tbm.code)) {
                    Tbm otm = tbms_eyes.get(tbm.code);
                    if (tbm.joker >= otm.joker) {
                        continue;
                    }
                    continue;
                }
                tbms_eyes.put(tbm.code, tbm);
            }
        }

        // printTbl("tbms_eyes_nojoker", tbms_eyes.values());
        // System.out.println("===================================");

        /** 素表依次减混儿 */
        for (int i = 1; i < 8; i++) {
            HashMap<String, Tbm> ts = new HashMap<>();
            for (Tbm tbm : tbms.values()) {
                for (int t = 0; t < tbm.ttt.length; t++) {
                    if (tbm.ttt[t] == 0 || (tbm.joker + 1) > 7) {
                        continue;
                    }
                    int[] tt = new int[tbm.ttt.length];
                    System.arraycopy(tbm.ttt, 0, tt, 0, tt.length);
                    tt[t] -= 1;
                    Tbm tm = new Tbm(tbm.joker + 1, getTstring(tt), tt);
                    if (ts.containsKey(tm.code)) {
                        Tbm otm = ts.get(tm.code);
                        if (tm.joker >= otm.joker) {
                            continue;
                        }
                    }
                    if (tbms.containsKey(tm.code)) {
                        Tbm otm = tbms.get(tm.code);
                        if (tm.joker >= otm.joker) {
                            continue;
                        }
                    }
                    ts.put(tm.code, tm);
                }
            }
            tbms.putAll(ts);
        }

        /** 素将表依次减混儿 */
        for (int i = 1; i < 8; i++) {
            HashMap<String, Tbm> ts = new HashMap<>();
            for (Tbm tbm : tbms_eyes.values()) {
                for (int t = 0; t < tbm.ttt.length; t++) {
                    if (tbm.ttt[t] == 0 || (tbm.joker + 1) > 7) {
                        continue;
                    }
                    int[] tt = new int[tbm.ttt.length];
                    System.arraycopy(tbm.ttt, 0, tt, 0, tt.length);
                    tt[t] -= 1;
                    Tbm tm = new Tbm(tbm.joker + 1, getTstring(tt), tt);
                    if (ts.containsKey(tm.code)) {
                        Tbm otm = ts.get(tm.code);
                        if (tm.joker >= otm.joker) {
                            continue;
                        }
                    }
                    if (tbms_eyes.containsKey(tm.code)) {
                        Tbm otm = tbms_eyes.get(tm.code);
                        if (tm.joker >= otm.joker) {
                            continue;
                        }
                    }

                    // System.out.println(tbm.joker + ":" + tbm.code + " >> " + tm.joker + ":" +
                    // tm.code);
                    ts.put(tm.code, tm);
                }
            }

            tbms_eyes.putAll(ts);
        }

        /** 无将表统计 */
        // printTbl("tbms", tbms.values());

        /** 带将表统计 */
        printTbl("tbms_eyes", tbms_eyes.values());
    }

    private void printTbl(String name, Collection<Tbm> col) {
        List<Tbm> list = new ArrayList<>(col);
        Collections.sort(list);

        int[] count = new int[15];
        for (Tbm tbm : list) {
            count[tbm.tile]++;
            System.out.println(tbm.joker + ":" + tbm.code);
            // System.out.println(tbm.joker + "\t" + tbm.code);
        }
        System.out.println(name + ":" + list.size());
        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) {
                continue;
            }
            System.out.println("count::" + i + ":" + count[i]);
        }
    }

    private ArrayList<int[]> getTable(int X, int mmax, int nmax) {
        ArrayList<int[]> tbl = new ArrayList<int[]>();

        for (int m = 0; m <= mmax; m++) {
            for (int n = 0; n <= nmax - m; n++) {
                // m个顺子, n个刻子
                if (m > 0) {
                    int[] seqs = new int[m];
                    while (seqs[m - 1] < s.length) {
                        for (int sei = 0; sei < seqs.length; sei++) {
                            int[] mm = new int[m];
                            while (mm[m - 1] < X - 1) {
                                int[] a = new int[X];
                                for (int i = 0; i < mm.length; i++) {
                                    Seq seq = s[seqs[i]];
                                    if (mm[i] + seq.tile.length > X) {
                                        continue;
                                    }
                                    boolean ok = true;
                                    for (int si = 0; si < seq.tile.length; si++) {
                                        if (a[mm[i] + si] >= 4) {
                                            ok = false;
                                            break;
                                        }
                                    }
                                    if (!ok) {
                                        continue;
                                    }

                                    for (int si = 0; si < seq.tile.length; si++) {
                                        a[mm[i] + si] += 1;
                                    }
                                }
                                if (n > 0) {
                                    int[] tris = new int[n];
                                    while (tris[n - 1] < t.length) {
                                        int[] nn = new int[n];
                                        while (nn[n - 1] < X) {
                                            int[] aa = new int[X];
                                            System.arraycopy(a, 0, aa, 0, aa.length);
                                            for (int j = 0; j < nn.length; j++) {
                                                Tri tri = t[tris[j]];

                                                if (aa[nn[j]] <= 1) {
                                                    aa[nn[j]] += tri.tile[0];
                                                }
                                            }
                                            tbl.add(aa);
                                            plus(nn, 0, X);
                                        }
                                        plus(tris, 0, t.length);
                                    }
                                } else {
                                    tbl.add(a);
                                }
                                plus(mm, 0, X - 1);
                            }
                        }

                        plus(seqs, 0, s.length);
                    }
                } else {
                    if (n > 0) {
                        int[] tris = new int[n];
                        while (tris[n - 1] < t.length) {
                            int[] nn = new int[n];
                            while (nn[n - 1] < X) {
                                int[] aa = new int[X];
                                for (int j = 0; j < nn.length; j++) {
                                    Tri tri = t[tris[j]];
                                    if (aa[nn[j]] + tri.tile[0] <= 4) {
                                        aa[nn[j]] += tri.tile[0];
                                    }
                                }
                                tbl.add(aa);
                                plus(nn, 0, X);
                            }
                            plus(tris, 0, t.length);
                        }
                    }
                }

            }
        }

        return tbl;
    }

    private void plus(int[] a, int i, int max) {
        if (i >= a.length) {
            return;
        }
        a[i]++;
        if (a[i] >= max) {
            if (i + 1 >= a.length) {
                return;
            }
            plus(a, i + 1, max);
            zero(a, i);
        }
    }

    // 归零
    private void zero(int[] a, int i) {
        if (i < 0) {
            return;
        }
        a[i] = 0;
        zero(a, i - 1);
    }

    private String getTstring(int[] a) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
        }
        String tableStr = sb.toString();
        tableStr = tableStr.replaceAll("^(0){1,9}", "");
        tableStr = tableStr.replaceAll("(0){1,9}$", "");
        if (tableStr.length() == 0) {
            tableStr = "0";
        }

        return tableStr;
    }
}

class Tbm implements Comparable<Tbm> {

    int joker;
    int tile;
    String code;
    int[] ttt;

    public Tbm(int joker, String code, int[] ttt) {
        this.joker = joker;
        this.code = code;
        this.ttt = ttt;
        for (int i = 0; i < ttt.length; i++) {
            this.tile += ttt[i];
        }
        if (tile == 0) {
            this.joker = 0;
        }
        this.tile += this.joker;
    }

    @Override
    public int compareTo(Tbm arg0) {
        if (this.joker == arg0.joker) {
            return Integer.parseInt(code) - Integer.parseInt(arg0.code);
        }
        return this.joker - arg0.joker;
    }
}

/** 顺子 */
class Seq {
    int joker;
    int[] tile;

    public Seq(int joker, int[] tile) {
        this.joker = joker;
        this.tile = tile;
    }

}

/** 刻子 */
class Tri {
    int joker;
    int[] tile;

    public Tri(int joker, int[] tile) {
        this.joker = joker;
        this.tile = tile;
    }
}

class Par {
    int joker;
    int[] tile;

    public Par(int joker, int[] tile) {
        this.joker = joker;
        this.tile = tile;
    }

}
