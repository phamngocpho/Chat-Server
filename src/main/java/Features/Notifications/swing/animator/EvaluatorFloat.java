package Features.Notifications.swing.animator;

public class EvaluatorFloat extends Evaluator<Float> {

    @Override
    public Float evaluate(Float from, Float target, float fraction) {
        return from + ((target - from) * fraction);
    }
}
