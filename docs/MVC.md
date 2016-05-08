# Model, View, and the Controller.
## A Story of the Holy Trinity.
### Right here.
#### In Markdown.
##### Really.

The MVC model is a model that everyone seems to like these days. It separates
three distinct blocks of a given program, and sort of specifies what each of
them can and can't do.

## Model
#### A.K.A. The bits and bobs
Model is any set of data that's there mostly for the sake of the data.
It can also do some operations on such data, once the Controller tells it so.
A Model is thus a slave of the Controller. Whenever you hear a Model cry about
the tyranny of the Controllers, don't listen to it.

## View
#### A.K.A. The Ugly GUI, Presenter, StuffLikeThat
A view is a thing responsible for displaying the data to the potential user,
and possibly relaying some user-made changes to the Controller.
A Controller can ask it to update itself.
Thus, the View and the Controller are roughly at the same level,
however the Controller is still the boss. A View has no way of getting
the reference without the Controller giving it to it.

## Controller
#### A.K.A. The Boss
The Boss, I mean, the Controller orchestrates the whole flow.
It updates the View, can modify the Model, and do just about anything it wants,
including throwing a NullPointerException in your face, because it can.

# Now, how does this stuff work in Gengine
Short answer: it hardly does.

Long answer:

- **Model**: Anything that's data. Typically the `World` itself, and anything within it.
- **View**: Anything that's called a `View` 
#Now, how's this stuff working in Genginein Gengine, which is
    a thing displaying something onto a JPanel, which happens to be the `View` itself.
- **Controller**: Any `Controller`. Typically a `WorldController`. These often contain
    other objects that may act like some small controllers themselves, such as any
    of the `Workers`. Some `WorldEntities` may act like controllers of their own as well.