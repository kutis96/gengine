# How To Use Gengine

Here's a quick how-to on Gengine usage.

1. Create a JFrame to draw into.

    Do not forget to set it visible.
    
2. Invoke your MainController (do brew your own one) with the given JFrame.
    
    Instantiate it, create a new 

3. Start it. And enjoy.

---

**Okay, okay.** I know you want to know how to brew your own MainController.
Here's how then.

1. Create a new class, preferrably extending the `MainController`.

    Do use the default constructor for now.

2. Create a View to show now.
    
3. In the `init()`, add this view to the supplied `JFrame`.

    **Do not forget to set the JFrame's Layout to BorderLayout, and above all,
      DO NOT FORGET to call doLayout on the JFrame itself.** Otherwise you'd
      have a nice weird problem to debug, wondering why'd your `View` size always
      zero.

4. In the `die()`, don't forget to kill all `Worker` threads you might've
    accomodated on the way.
    
    Just be nice to the garbage collector for once.

5. Do some magic to find out whether the Controller is supposed to die yet or not.

    I haven't added that in the MainDemoController myself so far, so you'll have
    to figure that out on your own.

6. You should be mostly done at this point.

If you need some more info about this, just look through the MainDemoController
and the related code. I know it's not exactly pretty, and there's so much to
be done a bit nicer, but I've had a deadline pushing me, so nothing's quite
perfect there, but it should be sufficient. There it no View switching there
either yet, so do expect that in some of the updates soon.
