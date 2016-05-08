# A couple of words about Rendering
Rendering is done at multiple levels in Gengine.

## The Renderable Interface
This interface is a thing that makes any object renderable by any renderer.
All `WorldEntities`, `Tiles`, `Overlays` etcetera, are mandated to extend it.

This interface basically only contains a `render` method, which returns a
`java.awt.Image` of sorts. Any extra buffering or loading of such an image
is to be solved by the classes implementing this interface.

## WorldRenderers
WorldRenderers are objects that take a `World`, a fresh pack of `WorldRendererOptions`,
and they render the heck out of it onto a specified `BufferedImage` surface.

**`WorldRendererOptions`** only contains some pixel/camera offset values
as well as some additional rendering flags, providing an unified container
for such options.

## Views
The **`Views`** are a fairly recent addition to the Gengine. They extend a `JPanel`,
and they draw themselves into themselves. Thus it's often very important to
set them visible before doing anything to them.

The `Views` also contain at least two additional methods: `initialize` and `deconstruct`.
These methods allow `Controllers` to softstart/softkill some `Threads` or `Workers`
within those Views.

## WorldView
A **`WorldView`** is a `View` containing a `RenderingWorker`, rendering a
`World` into itself using a specified `WorldRenderer`.

## Overlays
**`Overlays`** are some handy little things, letting one to attach another `Renderable`
to a given `Object` (typically certain `WorldEntities`) implementing the `HasOverlays`
interface.

I found the `Overlays` are quite handy for displaying various debug information around
the `WorldEntities`, as well as say, displaying the entity's mood, or maybe even an
entity's chat with another entity. The possibilities are virtually unlimited.

I've only implemented a single `Overlay` so far, which is the **`TextOverlay`**.
It only renders a set String into itself using the **`TextRenderer`**.