# Workers
A **`Worker`** is a simple, extensible abstract class, implementing the
`Runnable` interface. It is designed to make periodic operations as easy
to accomplish as possible.

It provides three abstract methods for the user to implement:

- **`init()`**
    - Initialization. Called once at the beginning.
- **`work()`**
    - Actual work goes on here. This part is called on every loop of the `Worker`.
- **`die()`**
    - Deconstruction. Called once when the `Worker` is stopped.

It also provides a constructor with a period setting there. It internally
contains a `Thread.wait(x)` call which may wait for this set period, with
all the good and bad things that could come out of it.

## Premade Workers
There are at least three different `Worker` classes implemented,
to be found in the `gengine.logic.workers` package.

**`Simulator`** is a `Worker` periodically calling a `World`'s `tick` method.
This may update the `World`'s animations, `WorldEntity` movements, etcetera.

**`RenderingWorker`** is a `Worker` which is responsible for periodic
repainting of a given `World`. A `WorldView` is typically the one constructing
it.

**`GrimReaper`** is a funny name for a `WorldEntity` garbage collector.
It takes the 'dead' `WorldEntities` out of the `World`. I couldn't think
of a more fitting name for it.