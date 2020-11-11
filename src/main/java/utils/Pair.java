package utils;

/**
 * Describes a Pair consisting of
 * two elements of type L and R.
 * @param <L> left
 * @param <R> right
 * @author Leon Geis
 */
public class Pair<L,R> {
    /**
     * Left Object.
     */
    private L left;
    /**
     * Right Object.
     */
    private R right;

    /**
     * Constructor setting left and right.
     * @param l left
     * @param r right
     */
    public Pair(L l, R r){
        this.left = l;
        this.right = r;
    }

    /**
     * @return Left Object of the Pair.
     */
    public L getLeft(){
        return this.left;
    }

    /**
     * @return Right Object of the Pair.
     */
    public R getRight(){
        return this.right;
    }

}
