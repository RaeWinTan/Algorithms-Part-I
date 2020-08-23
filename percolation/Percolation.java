public class Percolation {

    private final int sz;
    private int openSites;
    private boolean[][] sites;
    private int[] id;
    private int[] idSize;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        openSites = 0;
        sz = n;
        sites = new boolean[n][n];
        id = new int[n * n];
        idSize = new int[n * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
                idSize[k] = 1;
                id[k] = k++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > sz || col > sz || row < 1 || col < 1) throw new IllegalArgumentException();
        if (!isOpen(row, col)) {
            sites[row - 1][col - 1] = true;
            openSites++;
            union(row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row > sz || col > sz || row < 1 || col < 1) throw new IllegalArgumentException();
        return sites[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (row > sz || col > sz || row < 1 || col < 1) throw new IllegalArgumentException();
        if (row == 1) return isOpen(row, col);
        for (int i = 0; i < sz; i++) if (root(i) == root(current(row, col))) return true;
        return false;
    }

    private int root(int i) {

        if (i != id[i]) {
            id[i] = root(id[i]);
            i = id[i];
        }
        return i;
    }

    private int current(int r, int c) {
        int currentId;
        if (r - 1 == 0) {
            currentId = c - 1;
        }
        else if (c - 1 == 0) {
            currentId = (r - 1) * sz;
        }
        else {
            currentId = (r - 1) * sz + c - 1;
        }
        return currentId;
    }

    private void union(int r, int c) {
        int fid;
        int sid;
        int currentId = current(r, c);

        if (r - 1 != 0 && this.isOpen(r - 1, c)) {
            if (r - 2 == 0) {
                fid = root(c - 1);
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }
            }
            else if (c - 1 == 0) {
                fid = root((r - 2) * (sz));
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }
            }
            else {
                fid = root(((r - 2) * sz) + c - 1);
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }
            }
        }

        if (r + 1 != sz + 1 && this.isOpen(r + 1, c)) {
            if (c - 1 == 0) {
                fid = root((r) * (sz));
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }

            }
            else {
                fid = root(((r) * sz) + c - 1);
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }
            }
        }

        if (c - 1 != 0 && this.isOpen(r, c - 1)) {
            if (r - 1 == 0) {
                fid = root(c - 2);
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }
            }
            else if (c - 2 == 0) {
                fid = root((r - 1) * (sz));
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }

            }
            else {
                fid = root(((r - 1) * sz) + c - 2);
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }
            }
        }

        if (c + 1 != sz + 1 && this.isOpen(r, c + 1)) {
            if (r - 1 == 0) {
                fid = root(c);
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }
            }
            else {
                fid = root(((r - 1) * sz) + c);
                sid = root(currentId);
                if (idSize[fid] >= idSize[sid]) {
                    id[sid] = fid;
                    idSize[fid] += idSize[sid];
                }
                else {
                    id[fid] = sid;
                    idSize[sid] += idSize[fid];
                }
            }
        }
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        for (int i = 1; i <= sz; i++) {
            if (!isOpen(sz, i)) continue;
            if (isFull(sz, i)) return true;
        }
        return false;

    }

}
