import java.util.ArrayList;

public enum PlayerStats {
    GAINLINEPOSITIVE(0.5), GAINLINEZERO(-0.1), UNSUCCESSFULCARRY(-3)
    ,TOTALMETERSMADE(0.2), DEFENDERSBEATEN(3), LINEBREAKMADE(2.5),
    LINEBREAKCONCEDED(-5), TRY(10), PENALTYTRY(10), SUCCESSFULPASS(1)
    ,UNSUCESSFULPASS(-2), SUCCESSFULOFFLOAD(1), UNSUCCESSFULOFFLOAD(-2)
    ,POSITIVERUCK(0.5), NEGATIVERUCK(-1), ZERORUCK(-0.1)
    ,POSITIVESUPPORT(0.5), NEGATIVESUPPORT(-0.5), DOMINANTTACKLE(2)
    ,EFFECTIVETACKLE(1), ASSISTTACKLE(0.5), MISSEDTACKLE(-3)
    ,UNSUCCESSFULTACKLE(-2), POSITIVEBARGE(0.5), NEGATIVEBARGE(-1)
    ,POSITIVETURNOVER(1), NEGATIVETURNOVER(-2), POSITIVEPENALTY(5)
    ,NEGATIVEPENALTY(-7), YELLOWCARD(-5);
    private final double value;
    PlayerStats(double v) {
        value = v;
    }
    public double getValue() {
        return value;
    }

    public String getName(){
        return this.name();
    }


}
