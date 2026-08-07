import java.util.function.Function;

public record Success<T>(T value) implements Result<T> {
	@Override
    public <R> Result<R> map(Function<? super T, ? extends R> mapper) {
        return new Success<>(mapper.apply(value));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Result<R> flatMap(Function<? super T, ? extends Result<? extends R>> mapper) {
        return (Result<R>) mapper.apply(value);
    }
}