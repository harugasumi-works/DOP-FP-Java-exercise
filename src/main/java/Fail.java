import java.util.function.Function;

public record Fail<T>(EventError err) implements Result<T> {
	@Override
    public <R> Result<R> map(Function<? super T, ? extends R> mapper) {
        return new Fail<>(this.err());
    }

    @Override
    public <R> Result<R> flatMap(Function<? super T, ? extends Result<? extends R>> mapper) {
        return new Fail<>(this.err());
    }
}