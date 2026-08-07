@FunctionalInterface
public interface EventProc<E extends Event> {
	Result<String> execute(E event);

}
