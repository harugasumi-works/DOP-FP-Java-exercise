# Smart Home — DOP/FP Practice

A small smart-home event simulation used as a hands-on exercise in Data-Oriented Programming (DOP) and Functional Programming (FP) in modern Java (26, preview features enabled — sealed types, record patterns, pattern matching for switch).

This is not meant to be a realistic or production-shaped smart-home system. The domain (doors, lights, temperature sensors, rooms) is deliberately simple so the focus stays on paradigm mechanics rather than domain complexity.

## What this explores
<p>

  - Data as data, not objects — every domain value (DeviceMetadata, Door, Light, Temp, Success, Fail) is an immutable record. No behavior is attached beyond what's needed to transform the data itself.

- Illegal states made unrepresentable — device state and connection status started as boolean, moved through String, and ended as dedicated enums (DoorState, LightState, ConnectionStatus). Device identity followed the same path: from a raw String id to a sealed interface DeviceID implemented by per-category enums (DoorIDs, LightIDs, TempIDs), each carrying its own Rooms value directly rather than inferring it from a naming convention.<br>
- Exhaustiveness over defensive coding — Event, DeviceID, and Result are all sealed, so every switch over them is checked for completeness by the compiler. Adding a new device or event category breaks the build at every call site that needs updating, instead of failing silently at runtime.<br>
- Errors as data, not exceptions — failures are represented by EventError (a sealed type) inside a Fail value, not thrown as Throwable. This keeps failure information (e.g. which device disconnected) available as structured data for logging/reporting, and keeps EventProc a total function: it always returns a Result, never throws for expected failure cases.<br>
- No shared mutable state — an earlier version used a Gatherer with a mutable accumulator class to combine results, with short-circuit-on-first-failure semantics. It was retired in favor of a stateless map/forEach pipeline that reports on every device independently — a deliberate fix after realizing the batch use case needed accumulate semantics, not fail-fast ones.<br>
- Side effects pushed to the edge — all computation (EventProc, event generation) is pure; the only I/O (System.out.println) happens in the single terminal step at the end of main.
</p>
