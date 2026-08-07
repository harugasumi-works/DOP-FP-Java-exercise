import java.util.function.Function;

public sealed interface Result<T> permits Success, Fail{
	
	// Transform the inner value
    <R> Result<R> map(Function<? super T, ? extends R> mapper);

    // Transform and avoid nesting (Result<Result<R>>)
    <R> Result<R> flatMap(Function<? super T, ? extends Result<? extends R>> mapper);


    
}
