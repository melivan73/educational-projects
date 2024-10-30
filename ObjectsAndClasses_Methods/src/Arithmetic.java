@SuppressWarnings({"ConditionalExpression",
        "ManualMinMaxCalculation", "FieldMayBeFinal", "unused"})

public class Arithmetic {
    private int mNum1;
    private int mNum2;

    public Arithmetic(int num1, int num2){
        this.mNum1 = num1;
        this.mNum2 = num2;
    }

    public int Sum() {
        return mNum1 + mNum2;
    }

    public int Product() {
        return mNum1 * mNum2;
    }

    public int Min() {
        return Math.min(mNum1, mNum2);
    }

    public int Min2() {
        return mNum1 <= mNum2 ? mNum1 : mNum2;
    }

    public int Max() {
        return Math.max(mNum1, mNum2);
    }

    public int Max2() {
        return mNum1 >= mNum2 ? mNum1 : mNum2;
    }
}
